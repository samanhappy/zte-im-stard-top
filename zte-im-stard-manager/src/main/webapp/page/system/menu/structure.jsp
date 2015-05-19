<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="iframe-html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>组织架构</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/easyui1.3/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/easyui1.3/icon.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jstree/themes/default/style.min.css" />
<style type="text/css">
.jstree-default .jstree-icon:empty {
	width: 24px;
	height: 24px;
	line-height: 46px;
	margin-top: 11px;
}

.odd {
	background: #fcfcfc;
}

.c-struct .inner {
	height: 85%;
}

tr,td {
	height: 50px;
}

.g-table td {
	padding: 0px 0;
	border: 1px solid #e4e4e4;
	color: #909090;
}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/jstree/jstree.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/page.js"></script>

<script type="text/javascript">

	var currentHeight;
	var currentWidth;
	$(window).resize(function () {
   		var windowHeight = $(window).height();
   		var windowWidth = $(window).width();
 
   		if (currentHeight == undefined || currentHeight != windowHeight
      		|| currentWidth == undefined || currentWidth != windowWidth) {
     		parent.adjust();
      		currentHeight = windowHeight;
      		currentWidth = windowWidth;
   		}
	});

	if (typeof (JSON) == 'undefined') {
		//如果浏览器不支持JSON，则载入json2.js
		$.getScript('${pageContext.request.contextPath}/js/json2.js');
	}

	function toEditGroup(id, coverdiv) {
		if (curGroupId == curTenantId) {
			alert("公司不能编辑");
			return;
		}
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/toEditGroup.do',
					data : {
						gid : curGroupId
					},
					dataType : "json",
					success : function(data) {
						if (data.res && data.res.reCode
								&& data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						}
						$(window.parent.$("div#editDept #gid").val(data.gid));
						$(window.parent.$("div#editDept #gfullId").val(data.fullId));
						$(window.parent.$("div#editDept #gname")
								.val(data.gname));
						$(window.parent.$("div#editDept #gtname").html(
								data.gname));
						$(window.parent.$("div#editDept #deptName").val(
								data.deptName));
						$(window.parent.$("div#editDept #deptId").val(
								data.deptId));
						$(window.parent.$("div#editDept #sequ").val(data.sequ));
						$(window.parent.$("div#removeDept #sdelGroup").html(
								'确认删除' + data.gname + '？'));
						$("div#editDept #deptName").val(data.deptName);
						$("div#editDept #deptId").val(data.deptId);
					}
				});
		showDiv(id, coverdiv);
	}

	function showDiv(id, coverdiv) {
		//追加一个层，使背景变灰  
		var iWidth = $(window.parent).width();//浏览器当前窗口可视区域宽度  
		var iHeight = $(window.parent).height();//浏览器当前窗口可视区域高度   
		var divStyle = "position:absolute;left:0px;top:0px;width:100%;height:100%;filter:Alpha(Opacity=30);opacity:0.3;background-color:#000000;";
		$(window.parent.document).find("#" + coverdiv).attr("style", divStyle);

		var winNode = $(window.parent.document).find("#" + id);
		winNode.css("top", ((iHeight - winNode.height()) / 2));
		winNode.fadeIn("slow");
	}

	function showCover(coverdiv) {
		//追加一个层，使背景变灰  
		var iWidth = $(window.parent).width();//浏览器当前窗口可视区域宽度  
		var iHeight = $(window.parent).height();//浏览器当前窗口可视区域高度   
		var divStyle = "position:absolute;left:0px;top:0px;width:100%;height:100%;filter:Alpha(Opacity=30);opacity:0.3;background-color:#000000;";
		$(window.parent.document).find("#" + coverdiv).attr("style", divStyle);
	}

	function hideCover(coverdiv) {
		//删除变灰的层  
		$(window.parent.document).find("#" + coverdiv).removeAttr("style");
	}

	var curTenantId = 'c1';
	var curGroupName = null;
	var curGroupId = '<%=session.getAttribute("groupId")%>';
	
	var sort = 'uid asc';

	function changeSort(id) {
		if (id == 'idSortI') {
			sort = 'uid';
			$("#nameSortI").removeClass("ico-sort-asc");
			$("#nameSortI").removeClass("ico-sort-dsc");
		} else if (id == 'nameSortI') {
			sort = 't9_pinyin';
			$("#idSortI").removeClass("ico-sort-asc");
			$("#idSortI").removeClass("ico-sort-dsc");
		}
		var ele = $("#" + id);
		if (ele.hasClass("ico-sort-dsc")) {
			sort += ' asc';
			ele.removeClass("ico-sort-dsc");
			ele.addClass("ico-sort-asc");
		} else {
			sort += ' desc';
			ele.removeClass("ico-sort-asc");
			ele.addClass("ico-sort-dsc");
		}
		loadMemberData(curTenantId, curGroupId, 1);
	}

	//若启用LDAP或三方数据库接口，则企业管理员不能对通讯录数据进行更改
	var loginAuthType = null;
	function filterByLoginAuthType(type) {
		loginAuthType = type;
		if (isCannotOper()) {
			$(".userManage").css('display', 'none');
			$(".userManageDisable").css('display', 'inline-block');

			$("#userImport").css('display', 'none');
			$("#userImportDisable").css('display', 'inline-block');

			$("#userExport").css('display', 'none');
			$("#userExportDisable").css('display', 'inline-block');

			$("#deptManage").css('display', 'none');
			$("#deptManageDisable").css('display', 'block');

		}
	}

	function isCannotOper() {
		return loginAuthType
				&& (loginAuthType == 'LDAP' || loginAuthType == 'THIRD');
	}

	function loadParam() {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/loadParam.do',
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						} else if (data.item && data.item.length
								&& data.item.length > 0) {
							var tbodyHtml = '';
							for ( var i = 0; i < data.item.length; i++) {
								{
									var param = data.item[i];
									if (param.id == 'system') {
										filterByLoginAuthType(param.loginAuthType);
									} else if (param.id == 'contact') {
									} else {
									}
								}
							}
							$("#userDefineTab").html(tbodyHtml);
						}
					}
				});
	}

	function checkLisence() {
		$.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/checkLisence.do',
			dataType : "json",
			success : function(data) {
				if (data.remainDays) {
					$(".startDate").html(data.startDate);
					$(".endDate").html(data.endDate);
					if (data.remainDays < 0) {
						$("#licensePass").css('display', 'block');
					} else if (data.remainDays < 30) {
						$("#licenseOver").css('display', 'block');
					}
				}
			}
		});
	}

	loadParam();
	checkLisence();

	var privileges = null;
	$(function() {
		// 加载角色权限
		$.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/getPrivileges.do',
			dataType : "json",
			success : function(data) {
				privileges = JSON.stringify(data);
			},
			complete : function() {
				if (privileges == null) {
					$("#deptManage").css('display', 'none');
					$("#deptManageDisable").css('display', 'block');

					$(".userManage").css('display', 'none');
					$(".userManageDisable").css('display', 'inline-block');

					$("#userImport").css('display', 'none');
					$("#userImportDisable").css('display', 'inline-block');

					$("#userExport").css('display', 'none');
					$("#userExportDisable").css('display', 'inline-block');
				} else if (privileges != '\"all\"') {
					if (privileges.indexOf('deptManage') == -1) {
						$("#deptManage").css('display', 'none');
						$("#deptManageDisable").css('display', 'block');
					}
					if (privileges.indexOf('userManage') == -1) {
						$(".userManage").css('display', 'none');
						$(".userManageDisable").css('display', 'inline-block');
					}
					if (privileges.indexOf('userImport') == -1) {
						$("#userImport").css('display', 'none');
						$("#userImportDisable").css('display', 'inline-block');
					}
					if (privileges.indexOf('userExport') == -1) {
						$("#userExport").css('display', 'none');
						$("#userExportDisable").css('display', 'inline-block');
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});

		$('#jstree')
				.bind("loaded.jstree", function(e, data) {
					$("#jstree").jstree("open_all");
				})
				.bind(
						"ready.jstree",
						function(e, data) {
							if (curGroupId && curGroupId != null) {
								$('#jstree').jstree(true).select_node(
										"#" + curGroupId);
							} else {
								$('#jstree').jstree(true).select_node(
										"#" + curTenantId);
							}
						})
				.on(
						'changed.jstree',
						function(e, data) {
							$("#keyword").val("");
							var i, j, r = [];
							if (data.selected.length == 1) {
								var id = data.instance
										.get_node(data.selected[0]).id;
								curGroupId = id;
								curGroupName = data.instance
										.get_node(data.selected[0]).text;
								loadMemberData(curTenantId, curGroupId, 1);
							} else if (data.selected.length > 1) {
								// 多选处理
								for (i = 0, j = data.selected.length; i < j; i++) {
									r.push(data.instance
											.get_node(data.selected[i]).text);
								}
								$('#event_result').html(
										'Selected: ' + r.join(', '));
							}
						})
				.jstree(
						{
							'core' : {
								'data' : {
									'url' : function(node) {
										return node.id === '#' ? '${pageContext.request.contextPath}/groupTree.do?isRoot=1&t='
												+ (new Date()).valueOf()
												: '${pageContext.request.contextPath}/groupTree.do?isRoot=0&t='
														+ (new Date())
																.valueOf();
									},
									'data' : function(node) {
										return {
											'id' : node.id
										};
									}
								},
								"themes" : {
									"theme" : "classic",
									"dots" : false,
									"icons" : false
								}
							}
						});
	});

	var curDeptMemberCount = -1;
	var pSize = 10;
	function loadMemberData(tenantId, groupId, cPage) {
		if (tenantId) {
			var reqUrl = "${pageContext.request.contextPath}/groupMember.do";
			var reqData = "tenantId=" + tenantId;
			reqData += "&pSize=" + pSize;
			if (curGroupId) {
				reqData += "&groupId=" + curGroupId;
			}
			if (cPage) {
				reqData += "&cPage=" + cPage;
			}
			if ($("#keyword").val() && $("#keyword").val() != '') {
				reqData += "&keyword=" + $("#keyword").val().toLowerCase();
			}
			reqData += "&sortStr=" + sort;
			reqData += "&t=" + (new Date()).valueOf();
			curDeptMemberCount = -1;
			$
					.ajax({
						type : "post",
						url : reqUrl,
						data : reqData,
						dataType : "json",
						contentType : "application/x-www-form-urlencoded; charset=utf-8",
						beforeSend : function() {
							showCover('coverdiv1');
						},
						success : function(data) {
							if (data.res.reCode && data.res.reCode == '2') {
								parent.location.href = '${pageContext.request.contextPath}/';
							} else if (data.res.reCode
									&& data.res.reCode == '1') {
								$("#dataBody").html('');
								var len = data.members.memberList.length;
								var pageHtml = '';
								if (len && len > 0) {
									var htmlData = '';
									for ( var i = 0; i < len; i++) {
										var member = data.members.memberList[i];
										if (i % 2 == 0) {
											htmlData += "<tr>";
										} else {
											htmlData += "<tr class='odd'>";
										}
										htmlData += "<td><input type='checkbox' class='checkbox' name='checkId' value='";
										htmlData += JSON.stringify(member);
										htmlData += "'/></td>";
										htmlData += "<td>" + (i + 1) + "</td>";
										htmlData += "<td>" + (member.uid ? member.uid : "")
												+ "</td>";
										htmlData += "<td>" + (member.cn ? member.cn : "")
												+ "</td>";
										htmlData += "<td>" + (member.mobile ? member.mobile : "")
												+ "</td>";
										htmlData += "<td>" + (member.mail ? member.mail : "")
												+ "</td>";
										htmlData += "<td>" + (member.deptName ? member.deptName : "")
												+ "</td>";
										htmlData += "<td>" + (member.roleName ? member.roleName : "")
												+ "</td>";
										htmlData += "<td>"
												+ (member.usable == 'true' ? '启用'
														: '停用') + "</td>";
										htmlData += "<td>";

										if ((privileges == '\"all\"' || (privileges != null && privileges
												.indexOf('userPrivManage') != -1))) {
											htmlData += "<a href='javascript:parent.permissionSet(\""
													+ member.id
													+ "\",\""
													+ member.cn
													+ "\",\""
													+ member.roleName
													+ "\");'>保护设置</a>&nbsp;";
										} else {
											htmlData += "保护设置&nbsp;";
										}
										if ((privileges == '\"all\"' || (privileges != null && privileges
												.indexOf('userAbleManage') != -1))) {
											htmlData += "<a href='javascript:updateUserState(\""
													+ member.id
													+ "\","
													+ (member.usable == 'true' ? "\"false\");'>停用"
															: "\"true\");'>启用")
													+ "</a>";
										} else {
											htmlData += (member.usable == 'true' ? "停用"
													: "启用");
										}
										htmlData += "</td></tr>";
									}
									$("#dataBody").html(htmlData);

									pageHtml += "<a href='javascript:loadMemberData(curTenantId,curGroupId,"
											+ data.members.previous
											+ ")' class='prev'></a>";
									var startI = 1;
									if (data.members.totalPage > 10) {
										if (data.members.currentPage > 5) {
											startI = data.members.currentPage - 5;
										}
										if (data.members.totalPage
												- data.members.currentPage < 5) {
											startI -= 4 - (data.members.totalPage - data.members.currentPage);
										}
									}
									for ( var i = startI, j = 1; i <= data.members.totalPage
											&& j <= 10; i++, j++) {
										pageHtml += "<a href='javascript:loadMemberData(curTenantId,curGroupId,"
												+ i + ")'";
										if (i == data.members.currentPage) {
											pageHtml += " class='active'";
										}
										pageHtml += (">" + i + "</a>");
									}
									pageHtml += "<a href='javascript:loadMemberData(curTenantId,curGroupId,"
											+ data.members.next
											+ ")'  class='next'></a>";
								} else if (data.res.reCode
										&& data.res.reCode == '2') {
									parent.location.href = '${pageContext.request.contextPath}/';
								}

								$("#pageDiv").html(pageHtml);
								curDeptMemberCount = data.members.totalRecord;
							} else {
								alert("数据加载出错");
							}
						},
						complete : function() {
							hideCover('coverdiv1');
							parent.setCurDeptMemberCount(curDeptMemberCount);
							// 取消checkAll选中
							$("#checkAll")[0].checked = false;
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							alert("数据加载出错");
						}
					});

		}

	}

	loadMemberData(curTenantId);

	function editUser() {
		var checkedEle = $("input[name='checkId']:checked");
		if (checkedEle.length == 0) {
			alert("必须选择一名员工");
		} else if (checkedEle.length > 1) {
			alert("只能选择一名员工");
		} else {
			parent.setEditUserValue(checkedEle[0].value);
			showDiv('addUser', 'coverdiv1');
		}
	}

	function removeUser() {
		var checkedEle = $("input[name='checkId']:checked");
		if (checkedEle.length == 0) {
			alert("必须选择一名员工");
		} else {
			var removeids = "";
			var removenames = "";
			for ( var i = 0; i < checkedEle.length; i++) {
				var user = JSON.parse(checkedEle[i].value);
				removeids += user.id + ',';
				removenames += user.cn + ',';
			}
			removeids = removeids.substring(0, removeids.length - 1);
			removenames = removenames.substring(0, removenames.length - 1);
			parent.setRemoveValues(removeids, removenames);
			showDiv('removeUser', 'coverdiv2');
		}
	}

	function updateUserState(id, usable) {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/updateUser.do',
					data : {
						id : id,
						usable : usable
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							// 					alert("设置成功");
						} else if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert("设置失败");
						}
						loadMemberData(curTenantId);
					}
				});
	}

	function showAddUserDiv() {
		parent.resetUserValue();
		showDiv('addUser', 'coverdiv1');
	}

	function checkAll() {
		if ($("#checkAll").is(':checked')) {
			$("#dataBody input[type='checkbox']").each(function() {
				this.checked = true;
			});
		} else {
			$("#dataBody input[type='checkbox']").each(function() {
				this.checked = false;
			});
		}
	}

	function uncheckAll() {
		$("#dataBody input[type='checkbox']").each(function() {
			this.checked = false;
		});
	}

	function exportUser() {
		var checkedEle = $("input[name='checkId']:checked");
		parent.showExportTip(curGroupId, curGroupName, checkedEle);
	}
</script>

</head>
<body>
	<div class="nav-structure">
		<div class="inner">
			<div id="deptManage" class="hd clearfix">
				<a href="javascript:parent.showAddDept();" class="l">添加部门</a> <a
					href="javascript:toEditGroup('editDept','coverdiv1');" class="r">编辑部门</a>
			</div>
			<div id="deptManageDisable" class="hd clearfix"
				style="display: none; color: gray;">添加部门&nbsp;&nbsp;&nbsp;
				编辑部门</div>

			<div id="jstree" style="color: #303030; font-size: 16px"></div>

			<div>
				<ul id="tree">
				</ul>
			</div>
		</div>
	</div>
	<!-- 组织架构主体部分 [[ -->
	<div class="c-struct">
		<div id="tableDiv" class="inner">
			<div id="licensePass" class="m-expire clearfix"
				style="display: none;">
				<i></i>
				<div class="l">
					<p>您所使用的服务已过期，请立即联系我们。</p>
					<p>服务热线：400-8888888</p>
				</div>
				<div class="r">
					服务期：<span class="startDate">2014-10-28</span> 至<span
						class="endDate">2014-12-31</span>
				</div>
			</div>
			<div id="licenseOver" class="m-expire clearfix m-expire-tobe"
				style="display: none;">
				<i></i>
				<div class="l">
					<p>
						您所使用的服务将于<span class="endDate">2014-10-28</span>过期，为了不影响系统的正常使用，请立即联系我们。
					</p>
					<p>服务热线：400-8888888</p>
				</div>
				<div class="r">
					服务期：<span class="startDate">2014-10-28</span> 至 <span
						class="endDate">2014-12-31</span>
				</div>
			</div>

			<div class="c-header">
				<ul>
					<li class="add userManage"><a
						href="javascript:showAddUserDiv();">添加员工<i></i></a></li>
					<li class="del userManage"><a href="javascript:removeUser();">删除<i></i></a></li>
					<li class="edit userManage"><a href="javascript:editUser();">编辑<i></i></a></li>
					<li id="userImport" class="import"><a
						href="javascript:parent.showImportUser();">导入<i></i></a></li>
					<li id="userExport" class="export last"><a
						href="javascript:exportUser();">导出<i></i></a></li>

					<li class="add userManageDisable"
						style="display: none; color: gray;">添加员工<i></i></li>
					<li class="del userManageDisable"
						style="display: none; color: gray;">删除<i></i></li>
					<li class="edit userManageDisable"
						style="display: none; color: gray;">编辑<i></i></li>
					<li id="userImportDisable" class="import"
						style="display: none; color: gray;">导入<i></i></li>
					<li id="userExportDisable" class="export last"
						style="display: none; color: gray;">导出<i></i></li>
				</ul>
				<div class="g-search g-input" style="width: 140px;">
					<span class="ico-search"></span> <i class="f"></i> <input
						id="keyword" name="keyword" type="text"
						onkeypress="if(event.keyCode==13) {loadMemberData(curTenantId, curGroupId, 1);return false;}" />
					<i class="e"></i>
				</div>
			</div>
			<table class="tc g-table">
				<tr>
					<th width="40px"><input id="checkAll" name="checkAll"
						type="checkbox" class="checkbox" onclick="checkAll();" /></th>
					<th width="40px">序号</th>
					<th width="120px" class="sort">工号 <i id="idSortI"
						class="ico-sort ico-sort-asc" onclick="changeSort('idSortI');"></i>
					</th>
					<th width="60px" class="sort">姓名<i id="nameSortI"
						class="ico-sort" onclick="changeSort('nameSortI');"></i></th>
					<th width="110px">手机号</th>
					<th width="200px">邮箱</th>
					<th width="60px">部门</th>
					<th width="70px">职级</th>
					<th width="60px">状态 <!--<i class="ico-arrow-down"></i> -->
					</th>
					<th width="120px">操作</th>
				</tr>
				<tbody id="dataBody">
				</tbody>
			</table>

		</div>
		<!-- 分页 -->
		<div id="pageDiv" class="g-paging tc mtb30"
			style="position: fixed; left: 185px; bottom: 100px; width: 100%; height: 54px; padding-left: 0 !important; padding-left: 185px; white-space: nowrap;"></div>

	</div>
	<!-- 组织架构主体部分 ]] -->
</body>
</html>