<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="iframe-html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>组织架构</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />

<style type="text/css">
.c-company .inner,.c-system .inner {
	margin-left: 245px;
	padding: 0;
	overflow-x: hidden;
	overflow-y: auto;
}

a {
	color: black;
}

a:hover {
	color: #2c5492;
}

.item.on a {
	color: #2c5492;
}
</style>



<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
	var currentHeight;
	var currentWidth;
	$(window).resize(
			function() {
				var windowHeight = $(window).height();
				var windowWidth = $(window).width();

				if (currentHeight == undefined || currentHeight != windowHeight
						|| currentWidth == undefined
						|| currentWidth != windowWidth) {
					parent.adjust();
					currentHeight = windowHeight;
					currentWidth = windowWidth;
				}
			});
	
	if (typeof (JSON) == 'undefined') {
		//如果浏览器不支持JSON，则载入json2.js
		$.getScript('${pageContext.request.contextPath}/js/json2.js');
	}

	function val(item) {
		return $(item).val();
	}

	function setRoleUsers(perms, divPerms) {
		$("#roleUsers").val(perms);
		$("#roleUsersDiv").html(divPerms);
	}

	function saveOrUpdateRole() {

		if ($("#roleName").val() == '') {
			alert('角色名称不能为空');
			return;
		}

		var privList = $("#privDiv input:checked");
		if (privList.length == 0) {
			alert('至少设置一个权限');
			return;
		}

		var userList = '';
		var roleUsersList = $("#roleUsersDiv input");
		if (roleUsersList.length > 0) {
			for ( var i = 0; i < roleUsersList.length; i++) {
				userList += '&userList[' + i + '].id=' + roleUsersList[i].value;
				userList += '&userList[' + i + '].name='
						+ roleUsersList[i].name;
			}
		}

		var privileges = '';
		if (privList.length > 0) {
			for ( var i = 0; i < privList.length; i++) {
				privileges += '&privileges[' + i + ']=' + privList[i].value;
			}
		}

		$
				.ajax({
					type : "POST",
					contentType : "application/x-www-form-urlencoded; charset=utf-8",
					url : '${pageContext.request.contextPath}/saveOrUpdateRole.do',
					data : 'roleId=' + $("#roleId").val() + '&roleName='
							+ $("#roleName").val() + '&desc='
							+ $("#desc").val() + userList + privileges,
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							listRole(data.roleId);
						} else if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert('保存角色失败');
						}
					},
					complete : function() {
						clearData();
					},
					clearForm : true,
					resetForm : true
				});

	}

	function clearData() {
		$("#roleId").val('');
		$("#roleName").val('');
		$("#desc").val('');
		$("#privDiv input").removeAttr("checked");
		$("#roleUsers").val('');
		$("#roleUsersDiv").html('');
	}

	function listRole(roleId) {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/listRole.do',
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						} else if (data.item && data.item.length
								&& data.item.length > 0) {
							var html = '';
							var showI = data.item.length - 1;
							if (roleId != '' && !roleId) {
								showI = 0;
							}
							for ( var i = 0; i < data.item.length; i++) {
								var role = data.item[i];
								if (roleId && role.roleId == roleId) {
									showI = i;
								}
								html += "<li id='li_" + role.roleId + "'class='item'><a href='javascript:showRole("
										+ JSON.stringify(role)
										+ ");'>"
										+ role.roleName + '</a></li>';
							}
							$("#roleList").html(html);
							showRole(data.item[showI]);
						} else {
							$("#roleList").html('');
						}
					}
				});

	}

	function disableData() {
		$("#roleNameTd").css("display", "none");
		$("#roleNameLabelTd").css("display", "block");
		$("#roleNameLabelTd").html($("#roleName").val());

		$("#descTd").css("display", "none");
		$("#descLabelTd").css("display", "block");
		$("#descLabelTd").html($("#desc").val());

		$(":checkbox").each(function() {
			if (this.checked) {
				$(this).css("display", "none");
			} else {
				$(this).parent().parent().css("display", "none");
			}
		});

		$("#roleUsers").attr("readonly", "readonly");
		$("#selectUserA").css("display", "none");
		$("#operDiv").css("display", "none");

		if ($("#zuzhijiagou input:checked").length == 0) {
			$("#zuzhijiagou").css("display", "none");
		}
		if ($("#qiyeguanli input:checked").length == 0) {
			$("#qiyeguanli").css("display", "none");
		}
		if ($("#xitongguanli input:checked").length == 0) {
			$("#xitongguanli").css("display", "none");
		}
	}

	function enableData() {
		$("#roleNameTd").css("display", "block");
		$("#roleNameLabelTd").css("display", "none");

		$("#descTd").css("display", "block");
		$("#descLabelTd").css("display", "none");

		$(":checkbox").each(function() {
			$(this).css("display", "inline-block");
			$(this).parent().parent().css("display", "block");
		});

		$("#roleUsers").removeAttr("readonly");
		$("#selectUserA").css("display", "inline-block");
		$("#operDiv").css("display", "block");

		$("#zuzhijiagou").css("display", "block");
		$("#qiyeguanli").css("display", "block");
		$("#xitongguanli").css("display", "block");
	}

	var isAdd = false;
	var isEdit = false;
	var hasEdit = false;
	var toShowRole = null;

	function continueShow() {
		isEdit = false;
		hasEdit = false;
		if (toShowRole != null) {
			isAdd = false;
			showRole(toShowRole);
			$("#edit").css('display', 'inline-block');
			$("#edit_disable").css('display', 'none');
			$("#delete").css('display', 'inline-block');
			$("#delete_disable").css('display', 'none');
		} else if (isAdd) {
			isAdd = false;
			showAdd();
		}
	}

	function continueSaveAndShow() {
		isAdd = false;
		isEdit = false;
		hasEdit = false;
		saveOrUpdateRole();
		$("#edit").css('display', 'inline-block');
		$("#edit_disable").css('display', 'none');
		$("#delete").css('display', 'inline-block');
		$("#delete_disable").css('display', 'none');
	}

	var curRoleId = null;
	var curRole = null;

	$(function() {
		//单选组事件
		$(":checkbox").bind("click", function() {
			if (isEdit) {
				hasEdit = true;
			}
		});
	});

	// 判断内容是否编辑过
	function dataHasEdit() {
		if (isEdit && curRole != null) {

			if (hasEdit) {
				return true;
			}

			if ($("#roleName").val() != curRole.roleName) {
				return true;
			}

			if ($("#desc").val() != curRole.desc) {
				return true;
			}

			var perms = '';
			if (curRole.userList) {
				for ( var i = 0; i < curRole.userList.length; i++) {
					perms += curRole.userList[i].name + '；';
				}
			}
			if (perms.length > 0) {
				perms = perms.substring(0, perms.length - 1);
			}
			if ($("#roleUsers").val() != perms) {
				return true;
			}
		}
		return false;
	}

	function showRole(role) {
		if ((isAdd || dataHasEdit()) && $("#roleName").val() != ''
				&& $("#privDiv input:checked").length > 0) {
			// 其他操作弹出保存提示
			parent.showRoleTip($("#roleName").val());
			toShowRole = role;
			return;
		}

		isAdd = false;
		isEdit = false;

		$('#inner').animate({
			scrollTop : 0
		}, 0);

		//修改超链接样式
		$("#roleList li").removeClass("on");
		$("#li_" + role.roleId).addClass("on");

		$("#roleId").val(role.roleId);
		curRoleId = role.roleId;
		curRole = role;
		$("#roleName").val(role.roleName);
		$("#desc").val(role.desc);

		$("#privDiv input").removeAttr("checked");
		for ( var i = 0; i < role.privileges.length; i++) {
			$("#privDiv input[value='" + role.privileges[i] + "']").each(
					function() {
						this.checked = true;
					});
		}

		var perms = '';
		var divPerms = '';
		if (role.userList) {
			for ( var i = 0; i < role.userList.length; i++) {
				perms += role.userList[i].name + '；';
				divPerms += "<input id='"+role.userList[i].id+"' type='hidden' name='" + role.userList[i].name + "' value='" + role.userList[i].id + "' />";
			}
		}
		if (perms.length > 0) {
			perms = perms.substring(0, perms.length - 1);
		}

		$("#roleUsers").val(perms);
		$("#roleUsersDiv").html(divPerms);

		enableData();
		disableData();

		//修改超链接样式
		$("#menu li").removeClass("on");

		$("#edit").css('display', 'inline-block');
		$("#edit_disable").css('display', 'none');
		$("#delete").css('display', 'inline-block');
		$("#delete_disable").css('display', 'none');
	}

	listRole();

	function showAdd() {

		if ((isAdd || dataHasEdit()) && $("#roleName").val() != ''
				&& $("#privDiv input:checked").length > 0) {
			// 其他操作弹出保存提示
			parent.showRoleTip($("#roleName").val());
			toShowRole = null;
			isAdd = true;
			return;
		}

		//修改超链接样式
		$("#menu li").removeClass("on");
		$("#menu #add").addClass("on");
		clearData();
		enableData();

		$("#edit").css('display', 'none');
		$("#edit_disable").css('display', 'inline-block');
		$("#delete").css('display', 'none');
		$("#delete_disable").css('display', 'inline-block');

		isAdd = true;
	}

	function showEdit() {
		if (isEdit)
			return;
		//修改超链接样式
		$("#menu li").removeClass("on");
		$("#menu #edit").addClass("on");
		enableData();
		isEdit = true;
	}

	function removeRole() {
		//修改超链接样式
		$("#menu li").removeClass("on");
		$("#menu #edit").addClass("on");
		parent.prepareRemoveRole($("#roleId").val(), $("#roleName").val());
	}

	function cancel() {
		listRole($("#roleId").val() != "" ? $("#roleId").val() : curRoleId);
	}
</script>
</head>
<body>
	<div class="nav-structure nav-sys">
		<div class="inner">
			<ul id="roleList">
			</ul>
		</div>
	</div>
	<!-- 企业管理主体部分 [[ -->
	<div class="c-system c-sys-addrole">
		<div class="inner" id="inner" style="overflow:auto; height: 81%">
			<div class="c-header" style="border: 0">
				<ul id="menu">
					<li id="add" class="jia item"><a href="javascript:showAdd();">添加角色<i></i></a></li>
					<li id="edit" class="modify item"><a
						href="javascript:showEdit();">编辑角色<i></i></a></li>
					<li id="edit_disable" class="modify item"
						style="display: none; color: gray;">编辑角色<i></i></li>
					<li id="delete" class="item del"><a
						href="javascript:removeRole();">删除角色<i></i></a></li>
					<li id="delete_disable" class="item del"
						style="display: none; color: gray;">删除角色<i></i></li>
				</ul>
			</div>
			<table id="privDiv" class="g-table tc t-role-manage"
				style="margin: 0;">
				<colgroup>
					<col width="90" />
					<col width="360" />
					<col />
				</colgroup>
				<tr>
					<td>角色名称：</td>
					<td id="roleNameTd" style="display: none; border-bottom: 0">
						<div class="g-input" id="roleNameDiv">
							<input id="roleId" name="roleId" type="hidden" /> <i class="f"></i><input
								id="roleName" name="roleName" type="text" value=""
								placeholder="请输入角色名称" /><i class="e"></i>
						</div> <i class="g-required">*</i>
					</td>
					<td id="roleNameLabelTd" style="display: none; border-bottom: 0">
						<div>
							<label id="roleNameLabel"></label>
						</div>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>角色描述：</td>
					<td id="descTd" style="border-bottom: 0;">
						<div class="g-input">
							<i class="f"></i><input id="desc" name="desc" type="text"
								value="" placeholder="请输入角色描述" /><i class="e"></i>
						</div>
					</td>
					<td id="descLabelTd" style="border-bottom: 0;">
						<div class="g-input">
							<label id="descLabel"></label>
						</div>
					</td>
					<td></td>
				</tr>
				<tr>
					<td class="vt">角色设置：</td>
					<td colspan="2">
						<div id="zuzhijiagou" class="clearfix">
							<span class="manage-title l">组织架构：</span>
							<ul class="manage-list l">
								<li><label class="name l"><input name="privilege"
										value="deptManage" type="checkbox" class="checkbox" />部门管理</label> <span
									class="tip l">增加、修改、删除部门的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="userManage" type="checkbox" class="checkbox" />员工管理</label> <span
									class="tip l">增加、修改、删除员工的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="userPrivManage" type="checkbox" class="checkbox" />员工权限设置</label>
									<span class="tip l">设置个人信息访问的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="userAbleManage" type="checkbox" class="checkbox" />员工启用、停用</label>
									<span class="tip l">启用或停用员工的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="userImport" type="checkbox" class="checkbox" />导入</label> <span
									class="tip l">批量导入员工的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="userExport" type="checkbox" class="checkbox" />导出</label> <span
									class="tip l">批量导出员工通讯录的权利</span></li>
							</ul>
						</div>
						<div id="qiyeguanli" class="clearfix">
							<span class="manage-title l">企业管理：</span>
							<ul class="manage-list l">
								<li><label class="name l"><input name="privilege"
										value="publishNews" type="checkbox" class="checkbox" />发布新闻</label> <span
									class="tip l">发布企业新闻的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="editNews" type="checkbox" class="checkbox" />编辑新闻</label> <span
									class="tip l">修改已发布企业新闻的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="removeNews" type="checkbox" class="checkbox" />删除新闻</label> <span
									class="tip l">删除已发布企业新闻的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="updateCompInfo" type="checkbox" class="checkbox" />企业信息修改</label>
									<span class="tip l">修改企业名称、logo等信息的权利</span></li>
							</ul>
						</div>
						<div id="xitongguanli" class="clearfix">
							<span class="manage-title l">系统管理：</span>
							<ul class="manage-list l">
								<li><label class="name l"><input name="privilege"
										value="roleManage" type="checkbox" class="checkbox" />角色管理</label> <span
									class="tip l">设置角色权限和用户的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="updateSystemParam" type="checkbox" class="checkbox" />系统参数修改</label>
									<span class="tip l">设置账号登录的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="updateContactParam" type="checkbox" class="checkbox" />通讯录参数修改</label>
									<span class="tip l">设置通讯录参数、成员属性保护、个人可设置属性的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="defineParam" type="checkbox" class="checkbox" />通讯录自定义</label>
									<span class="tip l">设置通讯录自定义字段的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="licenseManage" type="checkbox" class="checkbox" />License管理</label>
									<span class="tip l">管理系统License的权利</span></li>
								<li><label class="name l"><input name="privilege"
										value="clientManage" type="checkbox" class="checkbox" />客户端管理</label>
									<span class="tip l">管理客户端版本的权利</span></li>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<td class="vt" colspan="3">角色成员： <!-- 					</td> --> <!-- 					<td colspan="2"> -->
						<a id="selectUserA" href="javascript:parent.selectUser('role');"
						class="ico-block" style="display: inline-block; margin-top: 5px;"></a>
						<textarea id="roleUsers"
							style="display: block; width: 80%; height: 100px; margin-top: 10px; color: #909090;"></textarea>
						<div id="roleUsersDiv"></div>
						<div id="operDiv">
							<a href="javascript:saveOrUpdateRole();" class="btn-true mt30">保存</a><a
								href="javascript:cancel();" class="btn-false mt30 ml30">取消</a>
						</div></td>

				</tr>
			</table>

		</div>
	</div>
</body>
</html>