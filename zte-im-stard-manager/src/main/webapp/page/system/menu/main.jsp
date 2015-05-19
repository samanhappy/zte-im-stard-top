<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>企信管理平台</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/jstree/themes/default/style.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script src="${pageContext.request.contextPath}/jstree/jstree.js"></script>
<script
	src='${pageContext.request.contextPath}/js/datepicker/WdatePicker.js'
	charset="UTF-8"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />
<style type="text/css">
/*消除chrome浏览器黄色底*/
input:-webkit-autofill {
	-webkit-box-shadow: 0 0 0 1000px white inset;
}

#coverdiv1 {
	z-index: 996;
}

#coverdiv2 {
	z-index: 998;
}

.dialog {
	position: absolute;
	display: none;
	z-index: 997;
}

.dialog1 {
	position: absolute;
	display: none;
	z-index: 999;
}

.jstree-default .jstree-icon:empty {
	width: 24px;
	height: 24px;
	line-height: 46px;
	margin-top: 11px;
}

.g-iframe {
	width: 100%;
	/* 	height: 100%; */
}

.selectLabel {
	display: inline-block;
	width: 96px;
	margin: 0 20px 0 0;
	font-size: 16px;
	line-height: 35px;
}

#posUl li {
	border-bottom: 1px solid #e7e8ea;
}

#sexUl li {
	border-bottom: 1px solid #e7e8ea;
}

.d-staff .tab-bd .item0 .dib {
	margin-left: 5px;
	margin-right: 20px;
}

.g-dialog-tip .license-warm {
	font-size: 18px;
	color: #f29315;
	padding: 0;
	text-align: center;
}

.g-dialog-tip .license-warm .con {
	padding: 60px 0 0;
}

.d-staff .tab-bd .item0 .txt {
	height: 115px;
}

.d-set-protect .bd1 label {
	padding: 10px 8px 0;
}

.d-right .bd .box-zj label {
	width: auto;
}
</style>
<script type="text/javascript">
	function adjust() {
		var iframeHeight = $(window).height() - 130;
		$('iframe').eq(0).css({
			height : iframeHeight
		});
	}

	window.onload = adjust;
	// 	window.onresize = adjust;

	if (typeof (JSON) == 'undefined') {
		//如果浏览器不支持JSON，则载入json2.js
		$.getScript('${pageContext.request.contextPath}/js/json2.js');
	}

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
					$(".updateCompInfo").css('display', 'none');
					$(".licenseManage").css('display', 'none');
					$(".clientManage").css('display', 'none');
					$(".roleManage").css('display', 'none');
				} else if (privileges != '\"all\"') {
					if (privileges.indexOf('updateCompInfo') == -1) {
						$(".updateCompInfo").css('display', 'none');
					}
					if (privileges.indexOf('licenseManage') == -1) {
						$(".licenseManage").css('display', 'none');
					}
					if (privileges.indexOf('clientManage') == -1) {
						$(".clientManage").css('display', 'none');
					}
					if (privileges.indexOf('roleManage') == -1) {
						$(".roleManage").css('display', 'none');
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});

		loadOrgTree()
	});

	$.ajaxSetup({
		cache : false
	});

	function setEditCType(type) {
		$("#editClient #ctype").val(type);
		switchShow('editClientUl');
	}

	function setAddCType(type) {
		$("#addClient #ctype").val(type);
		switchShow('addClientUl');
	}

	$(function() {
		//单选组事件
		$("input[name='paramType']").bind("click", function() {
			if (this.checked) {
				$(this).siblings().each(function() {
					this.checked = false;
				});
			}
		});

		//单选组事件
		$("#addClient input[name='cisActive']").bind("click", function() {
			if (this.checked) {
				$(this).siblings().each(function() {
					this.checked = false;
				});
			}
		});

		//单选组事件
		$("#editClient input[name='cisActive']").bind("click", function() {
			if (this.checked) {
				$(this).siblings().each(function() {
					this.checked = false;
				});
			}
		});

		//单选组事件
		$("#permSet").bind("click", function() {
			if (this.checked) {
				$("#ableDiv").css('display', 'block');
				$("#disableDiv").css('display', 'none');
				$("#permPosInfo").css('display', 'block');
				$(".perm").css('display', 'inline-block');
			} else {
				$("#permPosInfo input").each(function() {
					this.checked = false;
				});
				$("#permPosInfo").css('display', 'none');
				$("#deptPermList").val('');
				$("#userPermList").val('');
				$("#deptPermDiv").html('');
				$("#userPermDiv").html('');
				$(".perm").css('display', 'none');
			}
		});
	})

	function changeDisplay(id) {
		$("#mainLi").removeClass('active');
		$("#orgLi").removeClass('active');
		$("#sysLi").removeClass('active');
		if (id == 'mainMenu') {
			$("#mainLi").addClass('active');
			$("#orgMenu").css("display", "none");
			$("#sysMenu").css("display", "none");
			openUrl('mainLi', '/zte-im-manager/page/system/menu/structure.jsp',
					'main');
		} else if (id == 'orgMenu') {
			$("#sysMenu").css("display", "none");
			var display = $("#" + id).css("display");
			if (display == 'none') {
				$("#" + id).css("display", "block");
			} else {
				$("#" + id).css("display", "none");
			}
			$("#orgLi").addClass('active');
		} else if (id == 'sysMenu') {
			$("#orgMenu").css("display", "none");
			var display = $("#" + id).css("display");
			if (display == 'none') {
				$("#" + id).css("display", "block");
			} else {
				$("#" + id).css("display", "none");
			}
			$("#sysLi").addClass('active');
		}
	}

	function hideDiv(id, coverdiv) {
		var winNode = $("#" + id);
		winNode.fadeOut("slow");
		//删除变灰的层  
		$("#" + coverdiv).removeAttr("style");
	}

	function submitVideo() {
		var vSrc = $('#videoSrc').val();
		$("#main")[0].contentWindow.showVideo(vSrc);
		hideDiv('insertVideo', 'coverdiv1');
	}

	var chooseDeptFrom = null;
	function showDiv(id, coverdiv, from) {
		//追加一个层，使背景变灰  
		var iWidth = $(window).width();//浏览器当前窗口可视区域宽度  
		var iHeight = $(window).height();//浏览器当前窗口可视区域高度   
		var divStyle = "position:absolute;left:0px;top:0px;width:100%;height:100%;filter:Alpha(Opacity=30);opacity:0.3;background-color:#000000;";
		$("#" + coverdiv).attr("style", divStyle);

		var winNode = $("#" + id);
		winNode.css("top", ((iHeight - winNode.height()) / 2));
		winNode.fadeIn("slow");

		if (id == 'chooseDept' || id == 'permissionSet') {
			loadOrgTree();
			chooseDeptFrom = from;
		}
	}

	var userLiArr = new Array("userInfoLi", "userDetailLi", "userDefineLi");
	var userTabArr = new Array("userInfoTab", "userDetailTab", "userDefineTab");
	function showUserTab(index) {
		for ( var i = 0; i < userLiArr.length; i++) {
			if (i == index) {
				$("#" + userLiArr[i]).addClass("active");
				$("#" + userTabArr[i]).css("display", "block");
			} else {
				$("#" + userLiArr[i]).removeClass("active");
				$("#" + userTabArr[i]).css("display", "none");
			}
		}
	}

	function switchShow(id) {
		var ele = $("#" + id);
		if (ele.css("display") == 'none') {
			ele.css("display", "block");
		} else {
			ele.css("display", "none");
		}
	}

	function setSex(val) {
		$("#sex").val(val);
		switchShow("sexUl");
	}

	function chooseDept() {
		var selectedElms = $("#groupTree").jstree("get_selected", true);
		if (selectedElms.length == 0) {
			alert("您没有选择一个部门！");
			return;
		}

		if (chooseDeptFrom != 'permissionSet' && selectedElms.length > 1) {
			// 除了部门权限其他只能选择一个部门
			alert("最多只能选择一个部门！");
			return;
		}

		if (chooseDeptFrom == 'permissionSet') {
			var perms = ' ';
			var divPerms = '';
			for ( var i = 0; i < selectedElms.length; i++) {
				perms += selectedElms[i].text + '；';
				divPerms += "<input id='" + selectedElms[i].id + "' type='hidden' name='" + selectedElms[i].text + "' value='" + selectedElms[i].id + "' />";
			}
			if (perms.length > 0) {
				perms = perms.substring(0, perms.length - 1);
			}
			$("#deptPermList").val(perms);
			$("#deptPermDiv").html(divPerms);
			hideDiv('chooseDept', 'coverdiv2');
		} else {
			$("div#" + chooseDeptFrom + " #deptName").val(selectedElms[0].text);
			$("div#" + chooseDeptFrom + " #deptId").val(selectedElms[0].id);
			$("div#" + chooseDeptFrom + " #fullId").val(
					selectedElms[0].original.fullId);
			hideDiv('chooseDept', 'coverdiv2');
		}
		$("#groupTree").jstree("uncheck_all");
	}

	function selectNode(e, data) {
		if (chooseDeptFrom != 'permissionSet' && data.selected.length > 1) {
			for ( var i = 0; i < data.selected.length; i++) {
				if (data.node.id != data.selected[i]) {
					$('#groupTree').jstree(true).deselect_node(
							"#" + data.selected[i]);
				}
			}
		}
	}

	function loadOrgTree() {
		$('#groupTree')
				.bind("loaded.jstree", function(e, data) {
					$("#groupTree").jstree("open_all");
				})
				.bind("select_node.jstree", selectNode)
				.jstree(
						{

							"core" : {
								"data" : {
									"url" : function(node) {
										return node.id === '#' ? '${pageContext.request.contextPath}/groupTree.do?isRoot=1&t='
												+ (new Date()).valueOf()
												: '${pageContext.request.contextPath}/groupTree.do?isRoot=0&t='
														+ (new Date())
																.valueOf();
									},
									"data" : function(node) {
										return {
											'id' : node.id
										}
									}
								},
								"themes" : {
									"theme" : "classic",
									"dots" : false,
									"icons" : false
								},
								"multiple" : true
							// 允许多选
							},
							"checkbox" : {
								// 禁用级联选中  
								'three_state' : false,
								'cascade' : 'undetermined'
							},
							"search" : {
								"show_only_matches" : true
							},
							"plugins" : [ "checkbox", "search" ]
						});

		var to = false;
		$('#orgSearch').keyup(function() {
			if (to) {
				clearTimeout(to);
			}
			to = setTimeout(function() {
				var v = $('#orgSearch').val();
				$('#groupTree').jstree(true).search(v);
			}, 250);
		});
	}

	function addGroup() {

		if ($("#gname").val() == "") {
			alert('部门名称不能为空');
			return;
		}
		if ($("div#addDept #deptId").val() == "") {
			alert('上级部门不能为空');
			return;
		}
		if ($("div#addDept #sequ").val() == "") {
			alert('排序号不能为空');
			return;
		}

		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/addGroup.do',
					data : {
						gname : $("#gname").val(),
						gpname : $("div#addDept #deptName").val(),
						gpid : $("div#addDept #deptId").val(),
						gtid : curTenantId,
						sequ : $("div#addDept #sequ").val()
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else if (data.res.reCode && data.res.reCode == '-1') {
							alert(data.res.resMessage);
						} else {
							hideDiv('addDept', 'coverdiv1');
							$('#groupTree').jstree('refresh');
							$("#main").attr("src", "structure.jsp");

							$("#gname").val('');
							$("div#addDept #deptId").val('');
							$("div#addDept #deptName").val('');
							$("div#addDept #deptDesc").val('');
							$("div#addDept #sequ").val('');
						}
					}
				});
	}

	function showAddDept() {
		$("#gname").val('');
		$("div#addDept #deptId").val('');
		$("div#addDept #deptName").val('');
		$("div#addDept #deptDesc").val('');
		$("div#addDept #sequ").val('');
		showDiv('addDept', 'coverdiv1');
	}

	function editGroup() {

		if ($("div#editDept #gname").val() == "") {
			alert('部门名称不能为空');
			return;
		}
		if ($("div#editDept #deptId").val() == "") {
			alert('上级部门不能为空');
			return;
		}
		if ($("div#editDept #gid").val() == $("div#editDept #deptId").val()) {
			alert('上级部门不能为自己');
			return;
		}

		if ($("div#editDept #fullId").val().indexOf(
				$("div#editDept #gfullId").val()) == 0) {
			alert('上级部门不能为子部门');
			return;
		}

		if ($("div#editDept #sequ").val() == "") {
			alert('排序号不能为空');
			return;
		}

		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/editGroup.do',
					data : {
						gid : $("div#editDept #gid").val(),
						gname : $("div#editDept #gname").val(),
						gpname : $("div#editDept #deptName").val(),
						gpid : $("div#editDept #deptId").val(),
						sequ : $("div#editDept #sequ").val(),
						gtid : curTenantId
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else if (data.res.reCode && data.res.reCode == '-1') {
							alert(data.res.resMessage);
						} else {
							hideDiv('editDept', 'coverdiv1');
							$('#groupTree').jstree('refresh');
							$("#main").attr("src", "structure.jsp");
						}
					}
				});
	}

	var curDeptMemberCount = -1;
	function setCurDeptMemberCount(count) {
		curDeptMemberCount = count;
	}

	function delGroup() {
		if (curDeptMemberCount == 0) {
			$
					.ajax({
						type : "POST",
						url : '${pageContext.request.contextPath}/delGroup.do',
						data : {
							gid : $("div#editDept #gid").val(),
							gname : $("div#editDept #gname").val()
						},
						dataType : "json",
						success : function(data) {
							if (data.res.reCode && data.res.reCode == '2') {
								window.location.href = '${pageContext.request.contextPath}/';
							}
							$('#groupTree').jstree('refresh');
							hideDiv('removeDept', 'coverdiv2');
							hideDiv('editDept', 'coverdiv1');
							$("#main").attr("src", "structure.jsp");
						}
					});
		} else {
			hideDiv('removeDept', 'coverdiv2');
		}
	}

	function removeDept() {
		if (curDeptMemberCount == 0) {
			$("#sdelGroup").css("display", 'block');
			$("#removeDeptTip").css("display", 'none');
		} else {
			$("#sdelGroup").css("display", 'none');
			$("#removeDeptTip").css("display", 'block');
		}
		showDiv('removeDept', 'coverdiv2');
	}

	var curTenantId = 'c1';

	var posData;

	//获取职位数据
	function getPosData() {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/posList.do',
					data : {
						tenantId : curTenantId
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							posData = data.item;
							var len = data.item.length;
							if (len && len > 0) {
								var htmlData = '';
								for ( var i = 0; i < len; i++) {
									var group = data.item[i];
									htmlData += "<li><a href=\"javascript:setVal('pos','";
									htmlData += group.name;
									htmlData += "','";
									htmlData += group.id;
									htmlData += "');\">";
									htmlData += group.name;
									htmlData += "</a></li>";
								}
								$("#posUl").html(htmlData);
							}
						} else if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						}
					}
				});
	}

	function setVal(id, display, value) {
		$("#" + id).val(value);
		$("#" + id + "Display").val(display);
		switchShow(id + "Ul");
	}

	getPosData();

	var mobileRegr = /^(\+86)?(1)(\d{10})$/;
	var emailRegr = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;

	function checkParams() {
		if ($("#addUser #uid").val() == "") {
			alert("工号不能为空");
			return false;
		}

		if ($("#addUser #cn").val() == "") {
			alert("姓名不能为空");
			return false;
		}

		if ($("#addUser #deptId").val() == "") {
			alert("部门不能为空");
			return false;
		}

		/* if ($("#addUser #sex").val() == "") {
			alert("性别不能为空");
			return false;
		} */

		if ($("#addUser #pos").val() == "") {
			alert("职级不能为空");
			return false;
		}

		if ($("#addUser #birthday").val() == "") {
			$("#addUser #birthday").attr('disabled', true);
		}

		var mobile = $("#addUser #mobile").val();
		if (mobile == "") {
			alert("手机不能为空");
			return false;
		} else if (!mobileRegr.test(mobile)) {
			alert("手机号格式不正确");
			return false;
		}

		var email = $("#addUser #mail").val();
		if (email == "") {
			alert("邮箱不能为空");
			return false;
		} else if (email.indexOf('@') == -1 || email.indexOf('@') == 0
				|| email.indexOf('@') == email.length - 1) {
			alert("邮箱格式不正确：必须包含@且不能在开头和结尾");
			return false;
		} else if (email.indexOf('@') != email.lastIndexOf('@')) {
			alert("邮箱格式不正确：只能包含一个@");
			return false;
		} else if (email.indexOf('.') == -1 || email.indexOf('.') == 0
				|| email.lastIndexOf('.') == email.length - 1) {
			alert("邮箱格式不正确：必须包含.且不能在开头和结尾");
			return false;
		} else if (!emailRegr.test(email)) {
			alert("邮箱格式不正确");
			return false;
		}

		if ($("#addUser #password").val() == "") {
			alert("密码不能为空");
			return false;
		}

		if ($("#addUser #photoUrl").val() == "") {
			var photo = $("#addUser #photo").val();
			if (photo != ""
					&& !isAllowedAttach(photo, ' .jpeg .gif .jpg .png ')) {
				alert("头像格式不正确");
				return false;
			}
			// 没有选择头像时置为disable避免jquery提交一个空串
			if (photo == "") {
				$("#addUser #photo").attr('disabled', true);
			}
		} else {
			$("#addUser #photo").attr('disabled', true);
		}
		return true;
	}

	function addUser() {

		// 判断是新增还是修改
		if ($("#addUser #id").val() == "") {
			var options = {
				target : '#output2', // target element(s) to be updated with server response 
				success : function(data) {
					if (data.res.reCode && data.res.reCode == '1') {
						$("#main")[0].contentWindow.loadMemberData(curTenantId);
						hideDiv('addUser', 'coverdiv1');
						showUserTab(0);
						$('#addUserForm')[0].reset();
					} else if (data.res.reCode && data.res.reCode == '2') {
						window.location.href = '${pageContext.request.contextPath}/';
					} else if (data.res.reCode && data.res.reCode == '-1') {
						alert(data.res.resMessage);
					} else {
						alert("添加失败");
					}
				},
				complete : function() {
					hideDiv('', 'coverdiv2');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("数据操作出错");
				},
				url : '${pageContext.request.contextPath}/addUser.do',
				type : 'POST',
				dataType : "json",
				clearForm : false,
				resetForm : false
			};
			$('#addUserForm').unbind('submit');
			$('#addUserForm').submit(function() {
				$(this).ajaxSubmit(options);
				return false;
			});

			if (checkParams()) {
				showDiv('', 'coverdiv2');
				$("#addUser #tenantId").val(curTenantId);
				$("#addUserForm").submit();
			}
		} else {
			var editUserOptions = {
				target : '#output2',
				success : function(data) {
					if (data.res.reCode && data.res.reCode == '1') {
						$("#main")[0].contentWindow.loadMemberData(curTenantId);
						hideDiv('addUser', 'coverdiv1');
						$("#addUser #photoUrl").val("");
						$("#addUser #photoImg").attr("src", "");
						$("#addUser #imgSpan").html('上传头像');
						$("#addUser #birthday").attr('disabled', false);
						$("#addUser #photo").attr('disabled', false);
						$("#removeUserA").css("display", 'none');
						showUserTab(0);
						$('#addUserForm')[0].reset();
					} else if (data.res.reCode && data.res.reCode == '2') {
						window.location.href = '${pageContext.request.contextPath}/';
					} else if (data.res.reCode && data.res.reCode == '-1') {
						alert(data.res.resMessage);
					} else {
						alert("编辑失败");
					}
				},
				complete : function() {
					hideDiv('', 'coverdiv2');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("数据操作出错");
				},
				url : '${pageContext.request.contextPath}/addUser.do',
				type : 'POST',
				dataType : "json",
				clearForm : false,
				resetForm : false
			};
			$('#addUserForm').unbind('submit');
			$('#addUserForm').submit(function() {
				$(this).ajaxSubmit(editUserOptions);
				return false;
			});

			if (checkParams()) {
				showDiv('', 'coverdiv2');
				$("#addUser #tenantId").val(curTenantId);
				$("#addUserForm").submit();
			}
		}
	}

	function resetUserForm() {
		$("#addUserForm")[0].reset();
		$("#main")[0].contentWindow.uncheckAll();
		$("#posUl").css('display', 'none');
		$("#sexUl").css('display', 'none');
		$("#addUserForm input:hidden").val('');
	}

	function isAllowedAttach(sFile, sUploadImagesExt) {
		var sExt = sFile.match(/\.[^\.]*$/);
		if (sExt) {
			sExt = sExt[0].toLowerCase();
		} else {
			return false;
		}
		if (sUploadImagesExt.indexOf(sExt) != -1) {
			return true;
		}
		return false;
	}

	function resetUserValue() {
		$("#userOperDesc").html('添加员工');
		$("#removeUserA").css("display", 'none');
		$("#addUser #deptRelationId").val('');
		$("#addUser #roleRelationId").val('');
		$("#addUser #tenantId").val('');
		$("#addUser #id").val('');
		$("#addUser #photoUrl").val('');
		$("#addUser #photoImg").attr("src", '');
		$("#addUser #imgSpan").html('上传头像');
		showUserTab(0);
		$("#posUl").css('display', 'none');
		$("#sexUl").css('display', 'none');

		// 密码可修改
		$("#pwdDiv").css('background-color', 'white');
		$("#addUser #password").css('background-color', 'white');
		$("#addUser #password").removeAttr('readonly');
	}

	function setEditUserValue(json) {
		$("#userOperDesc").html('编辑员工');
		var member = JSON.parse(json);
		$("#addUser #deptRelationId").val(member.deptRelationId);
		$("#addUser #roleRelationId").val(member.roleRelationId);
		$("#addUser #tenantId").val(member.tenantId);
		$("#addUser #id").val(member.id);
		$("#addUser #uid").val(member.uid);
		$("#addUser #cn").val(member.cn);
		$("#addUser #deptId").val(member.deptId);
		$("#addUser #deptName").val(member.deptName);
		if (member.sex) {
			$("#addUser #sex").val(member.sex);
			$("#addUser #sexDisplay").val(member.sex == 1 ? "男" : "女");
		}
		$("#addUser #pos").val(member.roleId);
		$("#addUser #posDisplay").val(member.roleName);
		$("#addUser #birthday").val(member.birthday);
		$("#addUser #mobile").val(member.mobile);
		$("#addUser #l").val(member.l);
		$("#addUser #mail").val(member.mail);
		$("#addUser #password").val(member.password);
		// 密码不可修改
		$("#pwdDiv").css('background-color', 'rgb(235, 235, 228)');
		$("#addUser #password").css('background-color', 'rgb(235, 235, 228)');
		$("#addUser #password").attr('readonly', 'readonly');

		$("#addUser #photoUrl").val(member.photo);
		if (member.photo && member.photo != null) {
			$("#addUser #photoImg").attr("src", member.photo);
			$("#addUser #imgSpan").html('');
		} else {
			$("#addUser #photoImg").attr("src", "");
			$("#addUser #imgSpan").html('上传头像');
		}
		$("#addUser #weixin").val(member.weixin);
		$("#addUser #photoSign").val(member.photoSign);
		$("#addUser #weibo").val(member.weibo);
		$("#addUser #usable").val(member.usable);
		$("#addUser #hobby").val(member.hobby);
		$("#addUser #nickname").val(member.nickname);
		$("#addUser #qq").val(member.qq);
		$("#addUser #wwwhomepage").val(member.wwwhomepage);
		$("#addUser #address").val(member.postaladdress);

		$("#addUser #shortcode").val(member.shortcode);
		$("#addUser #deviceId").val(member.deviceId);

		$("#addUser #hometelephone").val(member.hometelephone);
		$("#addUser #fax").val(member.fax);
		$("#addUser #info").val(member.info);

		$("#removeUserA").css("display", 'inline-block');

		showUserTab(0);
		$("#posUl").css('display', 'none');
		$("#sexUl").css('display', 'none');
	}

	var maxsize = 1 * 1024 * 1024;//1M
	var errMsg = "上传的附件文件不能超过1M！！！";
	//var tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过1M，建议使用IE、FireFox、Chrome浏览器。";
	var browserCfg = {};
	var ua = window.navigator.userAgent;
	if (ua.indexOf("MSIE") >= 1) {
		browserCfg.ie = true;
	} else if (ua.indexOf("Firefox") >= 1) {
		browserCfg.firefox = true;
	} else if (ua.indexOf("Chrome") >= 1) {
		browserCfg.chrome = true;
	}
	function checkfile() {
		try {
			var obj_file = document.getElementById("photo");
			if (obj_file.value == "") {
				alert("请先选择上传文件");
				return false;
			}
			var filesize = 0;
			if (browserCfg.firefox || browserCfg.chrome) {
				filesize = obj_file.files[0].size;
			} else if (browserCfg.ie) {
				var obj_img = document.getElementById('photo');
				obj_img.dynsrc = obj_file.value;
				if (obj_img.fileSize) {
					filesize = obj_img.fileSize;
				} else if (obj_img.files && obj_img.files[0]
						&& obj_img.files[0].size) {
					filesize = obj_img.files[0].size;
				}
			} else {
				//alert(tipMsg);
				return true;
			}
			if (!filesize || filesize == -1) {
				//alert(tipMsg);
				return true;
			} else if (filesize > maxsize) {
				alert(errMsg);
				return false;
			} else {
				return true;
			}
		} catch (e) {
			alert(e);
		}
		return true;
	}

	function uploadUserFile() {
		try {
			var fileObj = $("#userFile");
			var file = $("#userFile").val();
			if (file == "") {
				alert("请先选择上传文件");
				return false;
			}
			if (!isAllowedAttach(file, ' .xlsx ')) {
				alert("文件格式不正确，请选择xlsx文件");
				$("#userFile").val('');
				//针对ie的clear实现
				fileObj.replaceWith( fileObj = fileObj.clone( true ) );
				return false;
			}

			var uploadOptions = {
				success : function(data) {
					if (data.res.reCode && data.res.reCode == '1') {
						$("#uploadedFileUrl").val(data.fileUrl);
						$("#uploadTip").css("display", "none");
						$("#templateTd").css("display", "none");
						$("#uploadedFile").css("display", "block");
						$("#fileSpan").html(file.split("\\").pop());
					} else if (data.res.reCode && data.res.reCode == '2') {
						window.location.href = '${pageContext.request.contextPath}/';
					} else {
						alert("上传失败");
					}
				},
				complete : function() {
					$("#userFile").val('');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("数据操作出错");
				},
				url : '${pageContext.request.contextPath}/uploadFile.do',
				type : 'POST',
				dataType : "json",
				clearForm : false,
				resetForm : false
			};
			$('#fileForm').unbind('submit');
			$('#fileForm').submit(function() {
				$(this).ajaxSubmit(uploadOptions);
				return false;
			});
			$("#fileForm").submit();

		} catch (e) {
			alert(e);
		}
		return true;
	}

	function showImportUser() {
		$("#uploadedFileUrl").val("");
		$("#uploadTip").css("display", "block");
		$("#templateTd").css("display", "block");
		$("#uploadedFile").css("display", "none");
		showDiv('importData', 'coverdiv1');
	}

	function importUser() {

		var fileUrl = $("#uploadedFileUrl").val();
		if (fileUrl == "") {
			alert('请先上传文件');
			return;
		}

		showDiv(null, 'coverdiv2', null);
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/importUser.do',
					data : {
						tenantId : curTenantId,
						fileUrl : fileUrl
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							$("#main")[0].contentWindow
									.loadMemberData(curTenantId);
							hideDiv('importData', 'coverdiv1');
						} else if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else {
							if (data.res.resMessage) {
								alert(data.res.resMessage);
							} else {
								alert("导入数据出错");
							}
						}
					},
					complete : function() {
						$("#uploadedFileUrl").val("");
						hideDiv('', 'coverdiv2');
						$("#uploadTip").css("display", "block");
						$("#templateTd").css("display", "block");
						$("#uploadedFile").css("display", "none");
					}
				});
	}

	function selectPhoto() {
		var photo = $("#addUser #photo").val();
		if (photo != "") {
			var fileObj = $("#addUser #photo");
			if (!isAllowedAttach(photo, ' .jpeg .gif .jpg .png ')) {
				$("#addUser #photo").val('');
				//针对ie的clear实现
				fileObj.replaceWith( fileObj = fileObj.clone( true ) );
				alert("头像格式不正确");
				return false;
			}
			if (!checkfile()) {
				$("#addUser #photo").val('');
				//针对ie的clear实现
				fileObj.replaceWith( fileObj = fileObj.clone( true ) );
				return false;
			}
			var uploadPhotoOptions = {
				target : '#output2',
				success : function(data) {
					if (data.res.reCode && data.res.reCode == '1') {
						$("#addUser #photoImg").attr("src", data.fileUrl);
						$("#addUser #imgSpan").html('');
						$("#addUser #photoUrl").val(data.fileUrl);
						$("#addUser #photo").val('');
					} else if (data.res.reCode && data.res.reCode == '2') {
						window.location.href = '${pageContext.request.contextPath}/';
					} else {
						alert("上传失败");
					}
				},
				complete : function() {
					hideDiv('', 'coverdiv2');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("数据操作出错");
				},
				url : '${pageContext.request.contextPath}/uploadFile.do',
				type : 'POST',
				dataType : "json",
				clearForm : false,
				resetForm : false
			};
			$('#addUserForm').unbind('submit');
			$('#addUserForm').submit(function() {
				$(this).ajaxSubmit(uploadPhotoOptions);
				return false;
			});
			showDiv(null, 'coverdiv2', null);
			$("#addUserForm").submit();
		}
	}

	var remove_ids;
	var remove_names;
	function setRemoveValues(removeids, removenames) {
		remove_ids = removeids;
		remove_names = removenames;
		$("#removeDesc").html("确认将" + removenames + "删除？");
	}

	var remove_client_ids;
	var remove_client_names;
	function setClientRemoveValues(removeids, removenames) {
		remove_client_ids = removeids;
		remove_client_names = removenames;
		$("#removeClientDesc").html("确认将" + removenames + "从列表中删除吗？");
	}

	function removeUser() {

		hideDiv('addUser', 'coverdiv1');

		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/removeUser.do',
					data : {
						unames : remove_names,
						uids : remove_ids
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							$("#main")[0].contentWindow
									.loadMemberData(curTenantId);
						} else if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert('刪除失败');
						}
					},
					complete : function() {
						hideDiv('removeUser', 'coverdiv2');
					}
				});
	}

	function removeSingleUser() {
		remove_ids = $("#addUser #id").val();
		$("#removeDesc").html("确认将" + $("#addUser #cn").val() + "删除？");
		showDiv('removeUser', 'coverdiv2');
	}

	var curUid = null;
	var curUname = null;
	function permissionSet(id, name, job) {

		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/listUserPerm.do',
					data : {
						uid : id
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							curUid = id;
							curUname = name;
							$("#permUserInfo").html(name + " " + job);

							var hasPermSeted = false;
							// 加载职级角色权限
							var posHtml = '';
							for ( var i = 0; i < posData.length; i++) {
								posHtml += "<label><input type='checkbox'";
								if (contain(data.item, posData[i].id)) {
									posHtml += "checked='checked'";
									hasPermSeted = true;
								}
								posHtml += " class='checkbox' name='"
										+ posData[i].name + "' value='"
										+ posData[i].id + "' />"
										+ posData[i].name + "</label>";
							}
							$("#permPosInfo").html(posHtml);

							// 加载部门和用户权限
							if (setPermByType(data.item, 2)) {
								hasPermSeted = true;
							}
							if (setPermByType(data.item, 3)) {
								hasPermSeted = true;
							}

							if (hasPermSeted) {
								$("#permSet").each(function() {
									this.checked = true;
								});
								$("#ableDiv").css('display', 'block');
								$("#disableDiv").css('display', 'none');
								$("#permPosInfo").css('display', 'block');
								$(".perm").css('display', 'inline-block');
							} else {
								$("#permSet").each(function() {
									this.checked = false;
								});
								$("#ableDiv").css('display', 'none');
								$("#disableDiv").css('display', 'block');
								$("#permPosInfo").css('display', 'none');
								$(".perm").css('display', 'none');
							}

							showDiv('permissionSet', 'coverdiv1');

						} else if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert('获取用户权限失败');
						}
					},
					complete : function() {

					}
				});
	}

	function contain(item, permId) {
		if (item && permId && item.length && item.length > 0) {
			for ( var i = 0; i < item.length; i++) {
				if (item[i].permId == permId) {
					return true;
				}
			}
		}
		return false;
	}

	function setPermByType(item, type) {
		var hasPermSeted = false;
		var perms = '';
		var divPerms = '';
		if (item && type && item.length && item.length > 0) {
			for ( var i = 0; i < item.length; i++) {
				if (item[i].permType == type) {
					if (type == 3) {
						perms += item[i].permName + '；';
					} else {
						perms += item[i].permName + '；';
					}
					divPerms += "<input id='userPermDiv" + item[i].permId + "' type='hidden' name='" + item[i].permName + "' value='" + item[i].permId + "' />";
				}
			}
		}
		if (perms.length > 0) {
			perms = perms.substring(0, perms.length - 1);
			hasPermSeted = true;
		}
		if (type == 2) {
			$("#deptPermList").val(perms);
			$("#deptPermDiv").html(divPerms);
		} else if (type == 3) {
			$("#userPermList").val(perms);
			$("#userPermDiv").html(divPerms);
		}
		return hasPermSeted;
	}

	function selectDept(type) {
		if (type == 'perm') {
			showDiv('chooseDept', 'coverdiv2', 'permissionSet');
			var permDeptList = $("#deptPermDiv input");
			if (permDeptList.length > 0) {
				for ( var i = 0; i < permDeptList.length; i++) {
					selectTreeNode(permDeptList[i].value);
				}
			}
		} else if (type == 'addDept') {
			showDiv('chooseDept', 'coverdiv2', 'addDept');
			if ($("#addDept #deptId").val() != '') {
				selectTreeNode($("#addDept #deptId").val());
			}
		} else if (type == 'editDept') {
			showDiv('chooseDept', 'coverdiv2', 'editDept');
			if ($("#editDept #deptId").val() != '') {
				selectTreeNode($("#editDept #deptId").val());
			}
		} else if (type == 'addUser') {
			showDiv('chooseDept', 'coverdiv2', 'addUser');
			if ($("#addUser #deptId").val() != '') {
				selectTreeNode($("#addUser #deptId").val());
			}
		}

	}

	function selectTreeNode(id) {
		$('#groupTree').jstree(true).select_node("#" + id);
	}

	var page = "";
	function loadUserList() {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/listUser.do',
					data : {
						tenantId : curTenantId,
						sortStr : sort
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							var userHtml = '';
							if (data.item && data.item.length > 0) {
								var lastDeptName = null;
								for ( var i = 0; i < data.item.length; i++) {
									var member = data.item[i];
									userHtml += "<tr>";
									userHtml += "<td>";
									userHtml += "<input type='checkbox' id='"
											+ member.id + "'";
									if (page == 'role') {
										if ($("#main")[0].contentWindow
												.val("#userPermDiv"
														+ member.id) == member.id) {
											userHtml += " checked='checked'";
										}
									} else {
										var value = $("#userPermDiv" + member.id).val();
										if (value && value == member.id) {
											userHtml += " checked='checked'";
										}
									}
									userHtml += " class='checkbox' name='";
									userHtml += member.cn + "' value='"
											+ member.id + "'/>";
									userHtml += "</td>";

									userHtml += "<td>" + member.cn + "</td>";
									userHtml += "<td>" + member.uid + "</td>";
									userHtml += "<td>" + member.deptName
											+ "</td>";
									userHtml += "</tr>";
								}
								if (lastDeptName != null) {
									userHtml += "</div>";
								}
							}
							$("#userListInfo").html(userHtml);
							showDiv('userList', 'coverdiv2');
							$('#userScrollDiv').scrollTop(0);
						} else if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert('获取用户列表失败');
						}
					},
					complete : function() {

					}
				});
	}

	function selectUser(pageSet) {
		page = pageSet;
		$("#selectUserA").attr("href",
				"javascript:selectUserList('" + page + "');");
		$("#userSearch").val('');
		loadUserList();
	}

	function openUrl(liId, url, target) {
		//验证是否登录
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/loadParam.do',
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else {
							if (liId == 'mainLi'
									|| url == '/zte-im-manager/page/system/manage/role-manage.jsp') {
								//$("#footer").css("margin-left", "395px");
							} else {
								//$("#footer").css("margin-left", "195px");
							}
							$("li").removeClass("on");
							$("#" + liId).addClass("on");
							window.open(url, target);
						}
					}
				});
	}

	function selectUserList(page) {
		var inputList = $("#userListInfo input:checked");
		var perms = '';
		var divPerms = '';
		if (inputList.length > 0) {
			for ( var i = 0; i < inputList.length; i++) {
				perms += inputList[i].name + '；';
				divPerms += "<input type='hidden' id='userPermDiv" + inputList[i].value + "' name='" + inputList[i].name + "' value='" + inputList[i].value + "' />";
			}
		}
		if (perms.length > 0) {
			perms = perms.substring(0, perms.length - 1);
		}
		if (page && page == 'role') {
			$("#main")[0].contentWindow.setRoleUsers(perms, divPerms);
		} else {
			$("#userPermList").val(perms);
			$("#userPermDiv").html(divPerms);
		}
		hideDiv('userList', 'coverdiv2');
	}

	function clearPerm() {
		$("#permPosInfo input").removeAttr('checked');
		$("#deptPermList").val('');
		$("#userPermList").val('');
		$("#deptPermDiv").html('');
		$("#userPermDiv").html('');
	}

	function updatePerm() {
		var permList = '';
		var index = 0;
		var permPosList = $("#permPosInfo input:checked");
		if (permPosList.length > 0) {
			for ( var i = 0; i < permPosList.length; i++) {
				permList += '&permList[' + (index + i) + '].tenantId='
						+ curTenantId;
				permList += '&permList[' + (index + i) + '].memberId=' + curUid;
				permList += '&permList[' + (index + i) + '].permType=1';
				permList += '&permList[' + (index + i) + '].permId='
						+ permPosList[i].value;
				permList += '&permList[' + (index + i) + '].permName='
						+ permPosList[i].name;
			}
			index += permPosList.length;
		}

		var permDeptList = $("#deptPermDiv input");
		if (permDeptList.length > 0) {
			for ( var i = 0; i < permDeptList.length; i++) {
				permList += '&permList[' + (index + i) + '].tenantId='
						+ curTenantId;
				permList += '&permList[' + (index + i) + '].memberId=' + curUid;
				permList += '&permList[' + (index + i) + '].permType=2';
				permList += '&permList[' + (index + i) + '].permId='
						+ permDeptList[i].value;
				permList += '&permList[' + (index + i) + '].permName='
						+ permDeptList[i].name;
			}
			index += permDeptList.length;
		}

		var permUserList = $("#userPermDiv input");
		if (permUserList.length > 0) {
			for ( var i = 0; i < permUserList.length; i++) {
				permList += '&permList[' + (index + i) + '].tenantId='
						+ curTenantId;
				permList += '&permList[' + (index + i) + '].memberId=' + curUid;
				permList += '&permList[' + (index + i) + '].permType=3';
				permList += '&permList[' + (index + i) + '].permId='
						+ permUserList[i].value;
				permList += '&permList[' + (index + i) + '].permName='
						+ permUserList[i].name;
			}
			index += permUserList.length;
		}

		$
				.ajax({
					type : "POST",
					contentType : "application/x-www-form-urlencoded; charset=utf-8",
					url : '${pageContext.request.contextPath}/updateUserPerm.do',
					data : 'uid=' + curUid + '&uname=' + curUname + permList,
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							hideDiv('permissionSet', 'coverdiv1');
						} else if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert('获取用户权限失败');
						}
					},
					complete : function() {

					}
				});

	}

	function userListCheckAll() {
		if ($("#userListCheckAll").is(':checked')) {
			$("#userListInfo input[type='checkbox']").each(function() {
				this.checked = true;
			});
		} else {
			$("#userListInfo input[type='checkbox']").each(function() {
				this.checked = false;
			});
		}
	}

	var groupId = null, users = null;
	function showExportTip(curGroupId, curGroupName, checkedUsers) {
		groupId = curGroupId;
		users = checkedUsers;
		if (checkedUsers.length > 0) {
			$('#exportDesc').html('确认导出选中' + checkedUsers.length + '条用户吗');
		} else if (curGroupId == null || curGroupId == "null") {
			$('#exportDesc').html('确认导出公司全部用户吗');
		} else {
			$('#exportDesc').html('确认导出 ' + curGroupName + ' 全部用户吗');
		}
		showDiv('exportTip', 'coverdiv1');
	}

	function exportUser() {
		var uids = null;
		if (users.length > 0) {
			uids = "";
			for ( var i = 0; i < users.length; i++) {
				var user = JSON.parse(users[i].value);
				uids += user.id + ',';
			}
			uids = uids.substring(0, uids.length - 1);
		}

		showDiv(null, 'coverdiv2');
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/exportUser.do',
					data : {
						tenantId : curTenantId,
						groupId : groupId,
						uids : uids
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							window.location.href = data.fileUrl;
						} else if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert("操作失败");
						}
					},
					complete : function() {
						hideDiv(null, 'coverdiv2');
						hideDiv('exportTip', 'coverdiv1');
					}
				});
	}

	function selectProp(div) {
		var propList = $("#" + div + " input:checked");
		var props = '';
		var names = '';
		if (propList.length > 0) {
			for ( var i = 0; i < propList.length; i++) {
				props += propList[i].value + ',';
				names += propList[i].name + '、';
			}
		}
		if (props.length > 0) {
			props = props.substring(0, props.length - 1);
		}
		if (names.length > 0) {
			names = names.substring(0, names.length - 1);
		}
		$("#main")[0].contentWindow.setProp(div, names, props);
		hideDiv(div, 'coverdiv1');
	}

	function addParam() {

		if ($("#addParam #paramName").val() == '') {
			alert('参数名称不能为空');
			return;
		}
		if ($("#addParam input[name=paramType]:checked").length == 0) {
			alert('参数类型不能为空');
			return;
		}

		var options = {
			success : function(data) {
				if (data.res.reCode && data.res.reCode == '1') {
					loadParam();
					$("#main")[0].contentWindow.loadParam();
				} else if (data.res.reCode && data.res.reCode == '2') {
					window.location.href = '${pageContext.request.contextPath}/';
				} else {
					alert("保存失败");
				}
			},
			complete : function() {
				hideDiv('addParam', 'coverdiv1');
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("数据操作出错");
			},
			url : '${pageContext.request.contextPath}/saveOrUpdateParam.do',
			type : 'POST',
			dataType : "json",
			clearForm : true,
			resetForm : true
		};
		$('#addParamForm').unbind('submit');
		$('#addParamForm').submit(function() {
			$(this).ajaxSubmit(options);
			return false;
		});

		$("#addParamForm").submit();
	}

	function showRoleTip(roleName) {
		$("#roleTipDesc").html("是否保存" + roleName + "?");
		showDiv('roleTip', 'coverdiv1');
	}

	function editParam() {
		var options = {
			success : function(data) {
				if (data.res.reCode && data.res.reCode == '1') {
					loadParam();
					$("#main")[0].contentWindow.loadParam();
				} else if (data.res.reCode && data.res.reCode == '2') {
					window.location.href = '${pageContext.request.contextPath}/';
				} else {
					alert("保存失败");
				}
			},
			complete : function() {
				hideDiv('editParam', 'coverdiv1');
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("数据操作出错");
			},
			url : '${pageContext.request.contextPath}/saveOrUpdateParam.do',
			type : 'POST',
			dataType : "json",
			clearForm : true,
			resetForm : true
		};
		$('#editParamForm').unbind('submit');
		$('#editParamForm').submit(function() {
			$(this).ajaxSubmit(options);
			return false;
		});

		$("#editParamForm").submit();
	}

	function setEditParamValue(id, name, type) {
		$("#editParam #id").val(id);
		$("#editParam #paramName").val(name);
		$("#editParam input[name=paramType]").each(function() {
			if ($(this).attr("value") == type) {
				this.checked = true;
			} else {
				this.checked = false;
			}
		});
		showDiv('editParam', 'coverdiv1');
	}

	function loadParam() {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/loadParam.do',
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else if (data.item && data.item.length
								&& data.item.length > 0) {
							var tbodyHtml = '';
							for ( var i = 0; i < data.item.length; i++) {
								{
									var param = data.item[i];
									if (param.id == 'system') {
									} else if (param.id == 'contact') {
										$("#protectedProps").html(
												param.protectedPropNames);
									} else {
										var html = '<div class="item"><label>';
										html += param.paramName;
										html += '</label><div class="g-input"><i class="f"></i><input id="'
												+ param.id
												+ '" name="'
												+ param.id
												;
										html += '" type="text" /><i class="e"></i></div></div>';
										tbodyHtml += html;
									}
								}
							}
							$("#userDefineTab").html(tbodyHtml);
						}
					}
				});
	}

	loadParam();

	var removeRoleId = null;
	var removeRoleName = null;
	function prepareRemoveRole(roleId, roleName) {
		removeRoleId = roleId;
		removeRoleName = roleName;
		$("#removeRoleDesc").html("确认将" + roleName + "从列表中删除吗？");
		showDiv('removeRoleTip', 'coverdiv1');
	}

	var sort = 't9_pinyin asc';
	function changeSort(id) {
		if (id == 'cnSortI') {
			sort = 'um.t9_pinyin';
			$("#uidSortI").removeClass("ico-sort-asc");
			$("#uidSortI").removeClass("ico-sort-dsc");
			$("#deptSortI").removeClass("ico-sort-asc");
			$("#deptSortI").removeClass("ico-sort-dsc");
		} else if (id == 'uidSortI') {
			sort = 'um.uid';
			$("#cnSortI").removeClass("ico-sort-asc");
			$("#cnSortI").removeClass("ico-sort-dsc");
			$("#deptSortI").removeClass("ico-sort-asc");
			$("#deptSortI").removeClass("ico-sort-dsc");
		} else if (id == 'deptSortI') {
			sort = 'ug.pinyin';
			$("#uidSortI").removeClass("ico-sort-asc");
			$("#uidSortI").removeClass("ico-sort-dsc");
			$("#cnSortI").removeClass("ico-sort-asc");
			$("#cnSortI").removeClass("ico-sort-dsc");
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
		loadUserList();
	}

	function removeRole() {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/removeRole.do',
					data : {
						roleId : removeRoleId,
						roleName : removeRoleName
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							$("#main")[0].contentWindow.listRole();
						} else if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert("操作失败");
						}
					},
					complete : function() {
						hideDiv('removeRoleTip', 'coverdiv1');
					}
				});
	}

	function removeClient() {

		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/removeClient.do',
					data : {
						cid : remove_client_ids,
						cname : remove_client_names
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							$("#main")[0].contentWindow.load();
						} else if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert("操作失败");
						}
					},
					complete : function() {
						hideDiv('removeClientTip', 'coverdiv1');
					}
				});
	}

	function showSelectFile(div) {
		$("#" + div + " #capk").val(
				$("div#" + div + " #capkFile").val().split('\\').pop());
	}

	function addClient() {

		if ($("#addUploading").css('display') == 'block') {
			alert("请等待文件处理成功");
			return;
		}

		var capkUrl = $("#addClient #capkUrl").val();
		if (capkUrl == "") {
			alert("请先上传文件");
			return;
		}

		if ($("#addClient #ctype").val() == "") {
			alert("请先选择发布类型");
			return;
		}

		if ($("#addClient input[name='cisActive']:checked").length == 0) {
			alert("请先选择是否激活");
			return;
		}
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/addClient.do',
					data : {
						ctype : $("div#addClient #ctype").val(),
						capkUrl : $("div#addClient #capkUrl").val(),
						cname : $("div#addClient #cname").val(),
						ciconUrl : $("div#addClient #ciconUrl").val(),
						cos : $("div#addClient #cos").val(),
						cisActive : $(
								"#addClient input[name='cisActive']:checked")
								.val(),
						cupdateLog : $("div#addClient #cupdateLog").val(),
						version : $("div#addClient #version").val(),
						capk : $("div#addClient #capk").val()
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else {
							$("#main")[0].contentWindow.load();
							hideDiv('addClient', 'coverdiv1');
						}
					}
				});
	}

	function editClient() {

		if ($("#editUploading").css('display') == 'block') {
			alert("请等待文件处理成功");
			return;
		}

		var capkUrl = $("#editClient #capkUrl").val();
		if (capkUrl == "") {
			alert("请先上传文件");
			return;
		}

		if ($("#editClient #ctype").val() == "") {
			alert("请先选择发布类型");
			return;
		}
		if ($("#editClient input[name='cisActive']:checked").length == 0) {
			alert("请先选择是否激活");
			return;
		}
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/editClient.do',
					data : {
						cid : $("div#editClient #cid").val(),
						ctype : $("div#editClient #ctype").val(),
						capkUrl : $("div#editClient #capkUrl").val(),
						cname : $("div#editClient #cname").val(),
						ciconUrl : $("div#editClient #ciconUrl").val(),
						cos : $("div#editClient #cos").val(),
						cisActive : $(
								"#editClient input[name='cisActive']:checked")
								.val(),
						cupdateLog : $("div#editClient #cupdateLog").val(),
						version : $("div#editClient #version").val(),
						capk : $("div#editClient #capk").val()
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '2') {
							window.location.href = '${pageContext.request.contextPath}/';
						} else {
							$("#main")[0].contentWindow.load();
							hideDiv('editClient', 'coverdiv1');
						}
					}
				});
	}

	function addApk() {
		var file = $("#addClient #capkFile").val();
		if (file == "") {
			alert("请先选择文件");
			return;
		}
		if (!isAllowedAttach(file, ' .apk .ipa')) {
			alert("文件格式不正确");
			$("#addClient #capkFile").val('');
			$("#addClient #capk").val('');
			//针对ie的clear实现
			var fileObj = $("#addClient #capkFile");
			fileObj.replaceWith( fileObj = fileObj.clone( true ) );
			return;
		}
		$("#addUploading").css('display', 'block');
		var uploadP = {
			target : '#output2',
			success : function(data) {
				if (data.res.reCode && data.res.reCode == '1') {
					$("#addClient #capk").val(data.fileUrl);
					$("#addClient #capkUrl").val(data.fileUrl);
					$("#addClient #capkFile").val('');
					$("#addClient #cname").val(data.data.cname);
					$("#addClient #cicon").attr('src', data.data.ciconUrl);
					$("#addClient #ciconUrl").val(data.data.ciconUrl);
					$("#addClient #version").val(data.data.version);
					$("#addClient #cos").val(data.data.cos);
					$("#addClient #capk").val(data.data.capk);
				} else if (data.res.reCode && data.res.reCode == '2') {
					window.location.href = '${pageContext.request.contextPath}/';
				} else {
					alert("上传失败");
				}
			},
			complete : function() {
				$("#addUploading").css('display', 'none');
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("数据操作出错");
			},
			url : '${pageContext.request.contextPath}/uploadClient.do',
			type : 'POST',
			dataType : "json",
			clearForm : false,
			resetForm : false
		};
		$('#aapkForm').unbind('submit');
		$('#aapkForm').submit(function() {
			$(this).ajaxSubmit(uploadP);
			return false;
		});
		$("#aapkForm").submit();

	}

	function editApk() {
		var file = $("#editClient #capkFile").val();
		if (file == "") {
			alert("请先选择文件");
			return;
		}
		if (!isAllowedAttach(file, ' .apk .ipa')) {
			alert("文件格式不正确");
			$("#editClient #capkFile").val('');
			$("#editClient #capk").val('');
			//针对ie的clear实现
			var fileObj = $("#editClient #capkFile");
			fileObj.replaceWith( fileObj = fileObj.clone( true ) );
			return;
		}
		$("#editUploading").css('display', 'block');
		var uploadP = {
			target : '#output2',
			success : function(data) {
				if (data.res.reCode && data.res.reCode == '1') {
					$("#editClient #capk").val(data.fileUrl);
					$("#editClient #capkUrl").val(data.fileUrl);
					$("#editClient #capkFile").val('');
					$("#editClient #cname").val(data.data.cname);
					$("#editClient #cicon").attr('src', data.data.ciconUrl);
					$("#editClient #ciconUrl").val(data.data.ciconUrl);
					$("#editClient #version").val(data.data.version);
					$("#editClient #cos").val(data.data.cos);
					$("#editClient #capk").val(data.data.capk);
				} else if (data.res.reCode && data.res.reCode == '2') {
					window.location.href = '${pageContext.request.contextPath}/';
				} else {
					alert("上传失败");
				}
			},
			complete : function() {
				$("#editUploading").css('display', 'none');
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("数据操作出错");
			},
			url : '${pageContext.request.contextPath}/uploadClient.do',
			type : 'POST',
			dataType : "json",
			clearForm : false,
			resetForm : false
		};
		$('#eapkForm').unbind('submit');
		$('#eapkForm').submit(function() {
			$(this).ajaxSubmit(uploadP);
			return false;
		});
		$("#eapkForm").submit();

	}
	function addIcon() {
		var file = $("#addClient #ciconFile").val();
		if (file == "") {
			alert("请先选择上传文件");
			return false;
		}
		var uploadP = {
			target : '#output2',
			success : function(data) {
				if (data.res.reCode && data.res.reCode == '1') {
					$("#addClient #cicon").attr("src", data.fileUrl);
					$("#addClient #ciconUrl").val(data.fileUrl);
					$("#addClient #ciconFile").val('');
				} else if (data.res.reCode && data.res.reCode == '2') {
					window.location.href = '${pageContext.request.contextPath}/';
				} else {
					alert("上传失败");
				}
			},
			complete : function() {
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("数据操作出错");
			},
			url : '${pageContext.request.contextPath}/uploadFile.do',
			type : 'POST',
			dataType : "json",
			clearForm : false,
			resetForm : false
		};
		$('#aiconForm').unbind('submit');
		$('#aiconForm').submit(function() {
			$(this).ajaxSubmit(uploadP);
			return false;
		});
		$("#aiconForm").submit();

	}
	function editIcon() {
		var file = $("#editClient #ciconFile").val();
		if (file == "") {
			alert("请先选择上传文件");
			return false;
		}
		var uploadP = {
			target : '#output2',
			success : function(data) {
				if (data.res.reCode && data.res.reCode == '1') {
					$("#editClient #cicon").attr("src", data.fileUrl);
					$("#editClient #ciconUrl").val(data.fileUrl);
					$("#editClient #ciconFile").val('');
				} else if (data.res.reCode && data.res.reCode == '2') {
					window.location.href = '${pageContext.request.contextPath}/';
				} else {
					alert("上传失败");
				}
			},
			complete : function() {
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("数据操作出错");
			},
			url : '${pageContext.request.contextPath}/uploadFile.do',
			type : 'POST',
			dataType : "json",
			clearForm : false,
			resetForm : false
		};
		$('#eiconForm').unbind('submit');
		$('#eiconForm').submit(function() {
			$(this).ajaxSubmit(uploadP);
			return false;
		});
		$("#eiconForm").submit();
	}
	
	$(function(){
		$("#userSearch").bind('input propertychange', function() {
			var key = $.trim($("#userSearch").val());
			$("#userListInfo input").each(function() {
				if (key == '' || this.name.indexOf(key) != -1) {
					$(this).parent().parent().css("display", "");
				} else {
					$(this).parent().parent().css("display", "none");
				}
			});
		});
	});
	
	function showAddClient() {
		$("#addClient input[type='text']").val('');
		$("#addClient input[type='hidden']").val('');
		$("#addClient input[type='checkbox']").removeAttr('checked');
		$("#cupdateLog").val('');
		$("#cicon").attr("src", "");
		showDiv('addClient', 'coverdiv1');
	}

	function showMsg() {
		if ($("#msgLi").hasClass("msg-new")) {
			$("#msgLi").removeClass("msg-new");
			$("#msgLi").addClass("msg");
			$("#msgNum").html("");
			showDiv('messageDiv', 'coverdiv1');
		}
	}

	function showProtectedProp(props) {
		$("#protectedProp input").each(function() {
			if (props.indexOf(this.value) != -1) {
				this.checked = true;
			} else {
				this.checked = false;
			}
		});
		showDiv('protectedProp', 'coverdiv1');
	}

	function showEditProp(props) {
		$("#editableProp input").each(function() {
			if (props.indexOf(this.value) != -1) {
				this.checked = true;
			} else {
				this.checked = false;
			}
		});
		showDiv('editableProp', 'coverdiv1');
	}

	function showFeedBack(desc) {
		$("#feedbackDesc").html(desc);
		showDiv('feedback', 'coverdiv1');
	}

	function cancelSelectDept() {
		hideDiv('chooseDept', 'coverdiv2');
		$('#groupTree').jstree('uncheck_all');
	}
</script>
</head>
<body>
	<div class="g-header clearfix">
		<div class="logo">
			<img src="${pageContext.request.contextPath}/images/logo.png" />企信·管理平台
		</div>
		<div class="menu">
			<ul class="options l">
				<!-- <li class="info"><a href="#"></a></li> -->
				<!-- <li class="call"><a href="#"></a></li> -->
				<li id="msgLi" class="msg">
					<!-- 				<li id="msgLi" class="msg-new"> --> <!-- 				<a href="javascript:showMsg();"></a> -->
					<i id="msgNum"></i>
				</li>
				<li class="logout"><a href="${pageContext.request.contextPath}/logout.do"></a></li>
			</ul>
			<div class="user-info l">
				<img src="${pageContext.request.contextPath}/temp/avatar.png"
					class="l" />
				<div class="l">
					<h3 class="name"><%=session.getAttribute("uname")%></h3>
					<div class="num"><%=session.getAttribute("userid")%></div>
				</div>
			</div>
		</div>
	</div>
	<div class="g-main clearfix">
		<div class="g-main-nav">
			<div class="inner">
				<ul>
					<li id="mainLi" class="nav-item nav-item-structure active">
						<div class="hd" onclick="changeDisplay('mainMenu')">
							<i></i><a>组织架构</a>
						</div>
					</li>
					<li id="orgLi" class="nav-item nav-item-company">
						<div class="hd" onclick="changeDisplay('orgMenu')">
							<i></i>企业管理<em class="ico-arrow-d"></em>
						</div>
						<ol id="orgMenu" style="display: none;">
							<li id="newsLi" class="item"><a
								href="javascript:openUrl('newsLi','${pageContext.request.contextPath}/page/system/company/company-news-list.jsp','main');">
									---<i class="ico-arrow-right"></i>企业新闻
							</a></li>
							<li id="infoLi" class="item updateCompInfo"><a
								href="javascript:openUrl('infoLi','${pageContext.request.contextPath}/page/system/company/company-info.jsp','main');">---<i
									class="ico-arrow-right"></i>企业信息
							</a></li>
							<li id="passLi" class="item"><a
								href="javascript:openUrl('passLi','${pageContext.request.contextPath}/page/system/company/company-edit-password.jsp','main');">---<i
									class="ico-arrow-right"></i>密码修改
							</a></li>
						</ol>
					</li>
					<li id="sysLi" class="nav-item nav-item-system">
						<div class="hd" onclick="changeDisplay('sysMenu')">
							<i></i>系统管理<em class="ico-arrow-d"></em>
						</div>
						<ol id="sysMenu" style="display: none;">
							<li id="roleLi" class="item roleManage"><a
								href="javascript:openUrl('roleLi','${pageContext.request.contextPath}/page/system/manage/role-manage.jsp','main');">---<i
									class="ico-arrow-right"></i>角色管理
							</a></li>
							<li id="paramLi" class="item"><a
								href="javascript:openUrl('paramLi','${pageContext.request.contextPath}/page/system/manage/params-setting.jsp','main');">---<i
									class="ico-arrow-right"></i>参数设置
							</a></li>
							<li id="liceLi" class="item licenseManage"><a
								href="javascript:openUrl('liceLi','${pageContext.request.contextPath}/page/system/manage/license-manage.jsp','main');">---<i
									class="ico-arrow-right"></i>许可证管理
							</a></li>
							<li id="systemLi" class="item"><a
								href="javascript:openUrl('systemLi','${pageContext.request.contextPath}/page/system/manage/system-log.jsp','main');">---<i
									class="ico-arrow-right"></i>系统日志
							</a></li>
							<li id="clientLi" class="item clientManage"><a
								href="javascript:openUrl('clientLi','${pageContext.request.contextPath}/page/system/manage/client-manage.jsp','main');">---<i
									class="ico-arrow-right"></i>客户端管理
							</a></li>
							<li id="feedbackLi" class="item"><a
								href="javascript:openUrl('feedbackLi','${pageContext.request.contextPath}/page/system/manage/feedback.jsp','main');">---<i
									class="ico-arrow-right"></i>信息反馈
							</a></li>
						</ol>
					</li>
				</ul>
			</div>
		</div>
		<div class="iframe-container">
			<iframe id="main" name="main" src="structure.jsp" frameborder="0"
				class="g-iframe"></iframe>
		</div>

		<div id="footer" class="footer" style="padding-left: 395px;">
			<div class="ft-inner">深圳市中兴云服务有限公司 粤ICP备13026534号
				经营许可证：粤B2-20130730 Copyright @ 2013-2014 版权所有</div>
			<!-- 			<div class="ft-inner">烽火星空通信发展有限公司 Copyright @ 2013-2014 版权所有</div> -->
		</div>

		<div id='coverdiv1'></div>

		<div id='coverdiv2'></div>

		<!-- 添加部门 -->
		<div id="addDept" class="g-dialog d-depart dialog">
			<div class="hd">
				添加部门<a href="javascript:hideDiv('addDept','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<ul>
					<li><label>部门名称:</label>
						<div class="g-input">
							<i class="f"></i><input type="text" id="gname" name="gname" /><i
								class="e"></i>
						</div></li>
					<li><label>上级部门:</label> <span class="rel"
						onclick="selectDept('addDept');">
							<div class="g-input">
								<i class="f"></i><input id="deptName" name="deptName"
									type="text" readonly="readonly" /> <input id="deptId"
									name="deptId" type="hidden" /><input id="fullId" name="deptId"
									type="hidden" /><i class="e"></i>
							</div> <a href="javascript:" class="ico-block"></a>
					</span></li>
					<li><label>部门描述:</label>
						<div class="g-input">
							<i class="f"></i><input id="deptDesc" name="deptDesc" type="text" /><i
								class="e"></i>
						</div></li>
					<li><label>排序号:</label>
						<div class="g-input">
							<i class="f"></i><input id="sequ" name="sequ" type="text" /><i
								class="e"></i>
						</div>
						<div style="color: #909090; font-size: 15px;">注：排序只在当前级别下排序</div></li>
				</ul>
			</div>
			<div class="ft">
				<a href="javascript:addGroup();" class="btn-true">添加</a> <a
					href="javascript:hideDiv('addDept','coverdiv1');" class="btn-false">取消</a>
			</div>
		</div>

		<!-- 编辑部门 -->
		<div id="editDept" class="g-dialog d-depart dialog">
			<div class="hd">
				编辑部门<span class="sub"><i></i><span id="gtname"></span></span><a
					href="javascript:hideDiv('editDept','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<ul>
					<li><label>部门名称:</label>
						<div class="g-input">
							<i class="f"></i><input id="gname" name="gname" type="text" /> <input
								id="gid" name="gid" type="hidden" value="${gid}" /> <input
								id="gfullId" name="gfullId" type="hidden" /><i class="e"></i>
						</div></li>
					<li><label>上级部门:</label> <span class="rel"
						onclick="selectDept('editDept');">
							<div class="g-input">
								<i class="f"></i><input id="deptName" name="deptName"
									type="text" readonly="readonly" /> <input id="deptId"
									name="deptId" type="hidden" /><input id="fullId" name="deptId"
									type="hidden" /><i class="e"></i>
							</div> <a href="javascript:" class="ico-block"></a>
					</span></li>
					<li><label>部门描述:</label>
						<div class="g-input">
							<i class="f"></i><input type="text" /><i class="e"></i>
						</div></li>
					<li><label>排序号:</label>
						<div class="g-input">
							<i class="f"></i><input id="sequ" name="sequ" type="text" /><i
								class="e"></i>
						</div>
						<div style="color: #909090; font-size: 15px;">注：排序只在当前级别下排序</div></li>
				</ul>
			</div>
			<div class="ft">
				<a href="javascript:removeDept();" class="btn-warm">删除部门</a> <a
					href="javascript:editGroup();" class="btn-true">更新</a> <a
					href="javascript:hideDiv('editDept','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>


		<!-- 选择部门 -->
		<div id="chooseDept" class="g-dialog d-depart-choose dialog1">
			<div class="hd">
				选择部门<a href="javascript:hideDiv('chooseDept','coverdiv2');"
					class="ico-close"></a>
			</div>


			<div class="bd"
				style="color: #303030; font-size: 16px; overflow: scroll;">
				<div id="groupTree"></div>
				<div class="g-search g-input">
					<span class="ico-search"></span> <i class="f"></i> <input
						id="orgSearch" type="text" /> <i class="e"></i>
				</div>
			</div>

			<div class="ft">
				<a href="javascript:chooseDept();" class="btn-true">确定</a> <a
					href="javascript:cancelSelectDept();" class="btn-false">取消</a>
			</div>
		</div>


		<!-- 添加/编辑员工 -->
		<div id="addUser" class="g-dialog d-staff dialog">
			<div class="hd">
				<span id="userOperDesc">添加员工</span> <a
					href="javascript:hideDiv('addUser','coverdiv1');resetUserForm();"
					class="ico-close"></a>
			</div>
			<form id="addUserForm" autocomplete="off">
				<input id="id" name="id" type="hidden" /> <input id="photoUrl"
					name="photoUrl" type="hidden" /> <input id="deptRelationId"
					name="deptRelationId" type="hidden" /> <input id="roleRelationId"
					name="roleRelationId" type="hidden" />
				<div class="bd">
					<ul class="g-tab-hd clearfix">
						<li id="userInfoLi" class="active"><a
							href="javascript:showUserTab(0);">员工信息</a></li>
						<li id="userDetailLi"><a href="javascript:showUserTab(1);">详细资料</a></li>
						<li id="userDefineLi"><a href="javascript:showUserTab(2);">自定义</a></li>
					</ul>
					<ul class="tab-bd">
						<li id="userInfoTab" style="display: block; height: 245px;"
							class="item item0">
							<table>
								<colgroup>
									<col width="80" />
									<col width="300" />
									<col width="90" />
									<col />
								</colgroup>
								<tr>
									<td><label for="">工号：</label></td>
									<td><div class="g-input">
											<i class="f"></i><input id="uid" name="uid" type="text" /><i
												class="e"></i>
										</div></td>
									<td><label for="">姓名：</label></td>
									<td><div class="g-input">
											<i class="f"></i><input id="cn" name="cn" type="text" /><i
												class="e"></i>
										</div></td>
								</tr>
								<tr>
									<td><label for="">部门：</label></td>
									<td>
										<div class="rel" onclick="selectDept('addUser');">
											<div class="g-input">
												<i class="f"></i><input id="deptName" name="deptName"
													type="text" readonly="readonly" style="width: 202px;" /> <input
													id="deptId" name="deptId" type="hidden" /><i class="e"></i>
											</div>
											<a href="javascript:" class="ico-block" style="left: 202px"></a>
										</div>
									</td>
									<td><label for="">性别：</label></td>
									<td>
										<div class="g-input" style="z-index: 1;">
											<i class="f"></i>
											<div class="g-drop rel">
												<div onclick="switchShow('sexUl')">
													<input id="sexDisplay" name="sexDisplay" type="text"
														readonly="readonly" /><input id="sex" name="sex"
														type="hidden" /> <i class="ico-arrow-down"></i>
												</div>
												<ul id="sexUl" class="bd"
													style="display: none; text-align: center; width: 220px;">
													<li><a
														href="javascript:javascript:setVal('sex','男','1');">男</a></li>
													<li><a
														href="javascript:javascript:setVal('sex','女','0');">女</a></li>
												</ul>
											</div>
											<i class="e"></i>
										</div>
									</td>
								</tr>
								<tr>
									<td><label for="">职级：</label></td>
									<td>
										<div class="g-input" style="z-index: 1;">
											<i class="f"></i>
											<div class="g-drop rel" style="width: 202px;">
												<div onclick="switchShow('posUl')" style="width: 202px;">
													<i class="ico-arrow-down"></i><input id="posDisplay"
														name="posDisplay" type="text" readonly="readonly" /><input
														id="pos" name="pos" type="hidden" />
												</div>
												<ul id="posUl" class="bd"
													style="display: none; text-align: center; width: 220px;">
												</ul>
											</div>
											<i class="e"></i>
										</div>
									</td>
									<td><label for="">出生日期：</label></td>
									<td><div class="g-input">
											<i class="f"></i><input type="text" id="birthday"
												name="birthday" value=""
												onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"
												class="Wdate" style="width: 202px;" /><i class="e"></i>
										</div></td>
								</tr>
								<tr>
									<td><label for="">手机：</label></td>
									<td>
										<div class="g-input">
											<i class="f"></i><input id="mobile" name="mobile" type="text" /><i
												class="e"></i>
										</div>
									</td>
									<td><label for="">登录账号：</label></td>
									<td><div class="g-input">
											<i class="f"></i><input id="l" name="l" type="text" /><i
												class="e"></i>
										</div></td>
								</tr>
								<tr>
									<td><label for="">邮箱：</label></td>
									<td>
										<div class="g-input">
											<i class="f"></i><input id="mail" name="mail" type="text"
												autocomplete="off" /><i class="e"></i>
										</div>
									</td>
									<td><label for="">密码：</label></td>
									<td><div id="pwdDiv" class="g-input">
											<i class="f"></i>
											<!-- 假字段防止chrome自动填充 -->
											<input style="display: none" type="text"
												name="fakeusernameremembered" /> <input
												style="display: none" type="password"
												name="fakepasswordremembered" /> <input id="tenantId"
												name="tenantId" type="hidden" /><input id="password"
												name="password" type="password" autocomplete="off"
												style="padding: 0; margin-top: 3px; width: 202px;" /><i
												class="e"></i>
										</div></td>
								</tr>
							</table>
						</li>
						<li id="userDetailTab" style="display: none; height: 245px;"
							class="item item1">
							<table>
								<colgroup>
									<col width="80" />
									<col width="300" />
									<col width="90" />
									<col />
								</colgroup>

								<tr>
									<td><label for="">头像：</label></td>
									<td class="rel">
										<div class="upload-w">
											<a href="javascript:;" class="upload"><span id="imgSpan">上传头像</span></a>
											<img id="photoImg" src="" alt="" class="avatar"
												style="position: absolute; left: 20px; margin: 0; width: 45px; height: 45px; top: 0;" />
											<input id="photo" name="photo" type="file" value=""
												class="upload" onchange="javascript:selectPhoto();" />
										</div>
										<div class="tip">
											格式：JPEG、PNG、GIF<br />大小：不超过1M
										</div>
									</td>
									<td><label for="">微信：</label></td>
									<td><div class="g-input">
											<i class="f"></i><input id="weixin" name="weixin" type="text" /><i
												class="e"></i>
										</div></td>
								</tr>
								<tr>
									<td><label for="">个性签名：</label></td>
									<td>
										<div class="rel">
											<div class="g-input">
												<i class="f"></i><input id="photoSign" name="photoSign"
													type="text" /><i class="e"></i>
											</div>
											<a href="javascript:;" class="ico-block"></a>
										</div>
									</td>
									<td><label for="">微博：</label></td>
									<td>
										<div class="g-input">
											<i class="f"></i><input id="weibo" name="weibo" type="text" /><i
												class="e"></i>
										</div>
									</td>
								</tr>
								<tr>
									<td><label for="">爱好：</label></td>
									<td>
										<div class="g-input">
											<i class="f"></i><input id="hobby" name="hobby" type="text" /><i
												class="e"></i>
										</div>
									</td>
									<td><label for="">家庭电话：</label></td>
									<td><div class="g-input">
											<i class="f"></i><input id="hometelephone"
												name="hometelephone" type="text" /><i class="e"></i>
										</div></td>
								</tr>
								<tr>
									<td><label for="">QQ：</label></td>
									<td>
										<div class="g-input">
											<i class="f"></i><input id="qq" name="qq" type="text" /><i
												class="e"></i>
										</div>
									</td>
									<td><label for="">传真电话：</label></td>
									<td><div class="g-input">
											<i class="f"></i><input id="fax" name="fax" type="text" /><i
												class="e"></i>
										</div></td>
								</tr>
								<tr>
									<td><label for="">地址：</label></td>
									<td>
										<div class="g-input">
											<i class="f"></i><input id="address" name="address"
												type="text" /><i class="e"></i>
										</div>
									</td>
									<td><label for="">备注：</label></td>
									<td><div class="g-input">
											<i class="f"></i><input id="info" name="info" type="text" /><i
												class="e"></i>
										</div></td>
								</tr>
							</table>
						</li>
						<li id="userDefineTab" style="display: none; height: 245px;">
							<div class="item">
								<label>短号:</label>
								<div class="g-input">
									<i class="f"></i><input id="shortCode" name="shortCode"
										type="text" /><i class="e"></i>
								</div>
							</div>
							<div class="item">
								<label>设备账号:</label>
								<div class="g-input">
									<i class="f"></i><input id="deviceId" name="deviceId"
										type="text" /><i class="e"></i>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>

			<div class="ft">
				<a id="removeUserA" style="display: none;"
					href="javascript:removeSingleUser();" class="btn-warm">删除员工</a> <a
					href="javascript:addUser();" class="btn-true">提交</a> <a
					href="javascript:javascript:hideDiv('addUser','coverdiv1');resetUserForm();"
					class="btn-false">取消</a>
			</div>
		</div>


		<!-- 编辑员工 -->
		<div id="editUser" class="g-dialog d-staff dialog">
			<div class="ft">
				<a href="javascript:showDiv('removeUser','coverdiv1');"
					class="btn-warm">删除员工</a> <a
					href="javascript:hideDiv('editUser','coverdiv1');" class="btn-true">添加</a>
				<a href="javascript:;" class="btn-false">取消</a>
			</div>
		</div>

		<!-- 数据导入 -->
		<div id="importData" class="g-dialog d-data-import dialog">
			<div class="hd">
				数据导入<a href="javascript:hideDiv('importData','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<table>
					<colgroup>
						<col width="313" />
					</colgroup>
					<tr>
						<td class="upload">
							<div class="title rel">
								<form id="fileForm">
									<label>选择数据源：</label> <a href="">上传文件</a> <input id="userFile"
										name="photo" type="file" value="上传文件"
										onchange="uploadUserFile()" />
								</form>
							</div>
							<ul id="uploadTip" class="tips">
								<li>步骤：1、模板下载后填写</li>
								<li class="p">2、选择填写完成的模板上传</li>
								<li class="p">3、确认提交</li>
							</ul>
							<ul id="uploadedFile" class="list clearfix"
								style="display: none;">
								<li><i class="ico-excel"></i><span id="fileSpan"></span></li>

							</ul> <input id="uploadedFileUrl" type="hidden" />
						</td>
						<td id="templateTd" class="download">
							<div class="title">
								<a
									href="${pageContext.request.contextPath}/data/user template.xlsx">下载模板</a>
							</div>
							<ul class="list clearfix">
								<li><a
									href="${pageContext.request.contextPath}/data/user template.xlsx"><i
										class="ico-excel"></i></a></li>
							</ul>
						</td>
					</tr>
				</table>
			</div>
			<div class="ft">
				<a href="javascript:importUser();" class="btn-true">导入</a> <a
					href="javascript:hideDiv('importData','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>

		<div id="removeUser" class="g-dialog g-dialog-tip dialog1">
			<div class="hd">
				提醒<a href="javascript:hideDiv('removeUser','coverdiv2');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<div id="removeDesc" class="con" style="padding-left: 21px;"></div>
			</div>
			<div class="ft">
				<a href="javascript:removeUser();" class="btn-true">确定</a> <a
					href="javascript:hideDiv('removeUser','coverdiv2');"
					class="btn-false">取消</a>
			</div>
		</div>


		<div id="removeDept" class="g-dialog g-dialog-tip dialog1">
			<div class="hd">
				提醒<a href="javascript:hideDiv('removeDept','coverdiv2');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<div class="con" id="sdelGroup" style="padding-left: 21px;">确认删除技术部？</div>
				<div class="tip" id="removeDeptTip" style="padding-left: 21px;">
					<i class="ico-warm"></i>请先删除员工或将员工移至其他部门
				</div>
			</div>
			<div class="ft">
				<a href="javascript:delGroup();" class="btn-true">确定</a> <a
					href="javascript:hideDiv('removeDept','coverdiv2');"
					class="btn-false">取消</a>
			</div>
		</div>

		<!-- 保护设置 -->
		<div id="permissionSet" class="g-dialog d-right dialog">
			<div class="hd">
				保护设置<span style="font-size: 15px; color: #909090;">（当前受保护的成员属性：<span
					id="protectedProps"></span>，可至“系统管理-参数设置”处修改）
				</span><a href="javascript:hideDiv('permissionSet','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<div style="margin-top: 15px; color: #2c5492; font-weight: bold;">
					<input id="permSet" type="checkbox" class="checkbox"
						style="border: 2px solid #2c5492; width: 17px; height: 17px;" />&nbsp;开启保护设置
				</div>
				<div id="permUserInfo" class="name" style="margin-top: 10px;">
				</div>
				<h2>可见职级</h2>
				<div class="box box-zj">
					<div id="permPosInfo" class="inner"></div>
				</div>
				<h2>
					可见部门<a href="javascript:selectDept('perm');" class="ico-block perm"></a>
				</h2>
				<textarea id="deptPermList" name="deptPermList" class="box box-txt"
					readonly="readonly"></textarea>
				<div id="deptPermDiv"></div>
				<h2>
					可见人员<a href="javascript:selectUser('struct');"
						class="ico-block perm"></a>
				</h2>
				<textarea id="userPermList" name="userPermList" class="box box-txt"
					readonly="readonly"></textarea>
				<div id="userPermDiv"></div>

			</div>
			<div id="ableDiv" class="ft">
				<a href="javascript:clearPerm();" class="btn-warm">清空权限</a> <a
					href="javascript:updatePerm();" class="btn-true">保存</a> <a
					href="javascript:hideDiv('permissionSet','coverdiv1');"
					class="btn-false">取消</a>
			</div>
			<div id="disableDiv" class="ft" style="display: none">
				<a href="javascript:" class="btn-warm" style="background: gray;">清空权限</a>
				<a href="javascript:" class="btn-true" style="background: gray;">保存</a>
				<a href="javascript:hideDiv('permissionSet','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>


		<!-- 用户列表 -->
		<div id="userList" class="g-dialog d-right dialog1">
			<div class="hd">
				选择人员<a href="javascript:hideDiv('userList','coverdiv2');"
					class="ico-close"></a>
			</div>
			<div class="">
				<div class="g-search g-input"
					style="position: absolute; top: 70px; right: 100px;">
					<span class="ico-search"></span> <i class="f"></i> <input
						id="userSearch" name="userSearch" type="text"/> <i
						class="e"></i>
				</div>
				<div style="padding-top: 50px; padding-left: 50px;">
					<div id="userScrollDiv" style="height: 251px; overflow: scroll;">
						<table class="tc g-table" style="width: 761px;">
							<thead>
								<tr>
									<th width="10px"><input id="userListCheckAll"
										name="userListCheckAll" type="checkbox" class="checkbox"
										onclick="userListCheckAll();" /></th>
									<th width="40px" class="sort">姓名<i id="cnSortI"
										class="ico-sort ico-sort-asc" onclick="changeSort('cnSortI');"></i></th>
									<th width="60px" class="sort">工号 <i id="uidSortI"
										class="ico-sort" onclick="changeSort('uidSortI');"></i>
									</th>
									<th width="60px" class="sort">部门<i id="deptSortI"
										class="ico-sort" onclick="changeSort('deptSortI');"></i></th>
								</tr>
							</thead>
							<tbody id="userListInfo">
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="ft">
				<a id="selectUserA" href="javascript:selectUserList();"
					class="btn-true">确定</a> <a
					href="javascript:hideDiv('userList','coverdiv2');"
					class="btn-false">取消</a>
			</div>
		</div>

		<div id="exportTip" class="g-dialog g-dialog-tip dialog">
			<div class="hd">
				提醒<a href="javascript:hideDiv('exportTip','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<div id="exportDesc" class="con" style="padding-left: 21px;"></div>
			</div>
			<div class="ft">
				<a href="javascript:exportUser();" class="btn-true">确定</a> <a
					href="javascript:hideDiv('exportTip','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>

		<div id="roleTip" class="g-dialog g-dialog-tip dialog">
			<div class="hd">
				提醒<a
					href="javascript:hideDiv('roleTip','coverdiv1');$('#main')[0].contentWindow.continueShow();"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<div id="roleTipDesc" class="con" style="padding-left: 21px;"></div>
			</div>
			<div class="ft">
				<a
					href="javascript:hideDiv('roleTip','coverdiv1');$('#main')[0].contentWindow.continueSaveAndShow();"
					class="btn-true">确定</a> <a
					href="javascript:hideDiv('roleTip','coverdiv1');$('#main')[0].contentWindow.continueShow();"
					class="btn-false">取消</a>
			</div>
		</div>

		<!-- 设置可更改属性 -->
		<div id="editableProp" class="g-dialog d-set-protect dialog">
			<div class="hd">
				设置可更改属性<a href="javascript:hideDiv('editableProp','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<label><input type="checkbox" class="checkbox"
					value="minipicurl" name="头像" />头像</label> <label><input
					type="checkbox" class="checkbox" value="autograph" name="签名" />签名</label>
				<label><input type="checkbox" class="checkbox" value="cname"
					name="部门" />部门</label> <label><input type="checkbox"
					class="checkbox" value="post" name="职位" />职位</label> <label><input
					type="checkbox" class="checkbox" value="mob" name="电话" />电话</label> <label><input
					type="checkbox" class="checkbox" value="email" name="邮箱" />邮箱</label> <label><input
					type="checkbox" class="checkbox" value="fax" name="传真" />传真</label> <label><input
					type="checkbox" class="checkbox" value="remark" name="备注" />备注</label>
			</div>
			<div class="ft">
				<a href="javascript:selectProp('editableProp');" class="btn-true">确定</a>
				<a href="javascript:hideDiv('editableProp','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>
		<!-- 设置受保护属性 -->
		<div id="protectedProp" class="g-dialog d-set-protect dialog">
			<div class="hd">
				设置受保护属性<a href="javascript:hideDiv('protectedProp','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<label><input type="checkbox" class="checkbox"
					value="minipicurl" name="头像" />头像</label> <label><input
					type="checkbox" class="checkbox" value="autograph" name="签名" />签名</label>
				<label><input type="checkbox" class="checkbox" value="cname"
					name="部门" />部门</label> <label><input type="checkbox"
					class="checkbox" value="post" name="职位" />职位</label> <label><input
					type="checkbox" class="checkbox" value="mob" name="电话" />电话</label> <label><input
					type="checkbox" class="checkbox" value="email" name="邮箱" />邮箱</label> <label><input
					type="checkbox" class="checkbox" value="fax" name="传真" />传真</label> <label><input
					type="checkbox" class="checkbox" value="remark" name="备注" />备注</label> <br />

			</div>
			<div class="ft">
				<a href="javascript:selectProp('protectedProp');" class="btn-true">确定</a>
				<a href="javascript:hideDiv('protectedProp','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>

		<!-- 增加字段 -->
		<div id="addParam" class="g-dialog d-add-field dialog">
			<div class="hd">
				增加字段<a href="javascript:hideDiv('addParam','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<form id="addParamForm">
					<ul>
						<li><span class="t">字段名称:</span>
							<div class="g-input">
								<i class="f"></i><input id="paramName" name="paramName"
									type="text" /><i class="e"></i>
							</div></li>
						<li><span class="t">字段类型:</span> <input type="checkbox"
							class="checkbox" name="paramType" value="number" /><label>数字</label>
							<input type="checkbox" class="checkbox" name="paramType"
							value="date" /><label>日期</label><input type="checkbox"
							class="checkbox" name="paramType" value="string" /><label>字符串</label></li>
					</ul>
				</form>
			</div>
			<div class="ft">
				<a href="javascript:addParam();" class="btn-true">确定</a> <a
					href="javascript:hideDiv('addParam','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>


		<!-- 增加字段 -->
		<div id="editParam" class="g-dialog d-add-field dialog">
			<div class="hd">
				增加字段<a href="javascript:hideDiv('editParam','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<form id="editParamForm">
					<input id="id" name="id" type="hidden" />
					<ul>
						<li><span class="t">字段名称:</span>
							<div class="g-input">
								<i class="f"></i><input id="paramName" name="paramName"
									type="text" /><i class="e"></i>
							</div></li>
						<li><span class="t">字段类型:</span> <input type="checkbox"
							class="checkbox" name="paramType" value="number"><label>数字</label>
								<input type="checkbox" class="checkbox" name="paramType"
								value="date"><label>日期</label><input type="checkbox"
									class="checkbox" name="paramType" value="string"><label>字符串</label></li>
					</ul>
				</form>
			</div>
			<div class="ft">
				<a href="javascript:editParam();" class="btn-true">确定</a> <a
					href="javascript:hideDiv('editParam','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>

		<!-- 新增 客户端-->
		<div id="addClient" class="g-dialog d-staff dialog">
			<div class="hd">
				新增<a href="javascript:hideDiv('addClient','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<ul class="tab-bd">
					<li class="item item0"><input id="cid" name="cid"
						type="hidden" /> <input id="capkUrl" name="capkUrl" type="hidden" />
						<input id="ciconUrl" name="ciconUrl" type="hidden" />
						<table>
							<colgroup>
								<col width="80" />
								<col width="300" />
								<col width="180" />
								<col />
							</colgroup>

							<tr>
								<td><label for="">发布类型：</label></td>
								<td>
									<div class="g-input" style="z-index: 1;">
										<i class="f"></i>
										<div class="g-drop rel">
											<div class="hd">
												<i class="ico-arrow-down"
													onclick="switchShow('addClientUl')"></i><input type="text"
													readonly="readonly" id="ctype" name="ctype" value="" />
											</div>
											<ul id="addClientUl" class="bd"
												style="display: none; text-align: center;">
												<li><a href="javascript:setAddCType('Android应用');">Android应用</a></li>
												<li><a href="javascript:setAddCType('IOS应用');;">IOS应用</a></li>
											</ul>
										</div>
										<i class="e"></i>
									</div>
								</td>
								<td><label style="width: 80px" for="">操作系统：</label></td>
								<td style="position: relative; left: -100px;">
									<div class="g-input">
										<i class="f"></i><input type="text" value="" id="cos" readonly
											name="cos" /><i class="e"></i>
									</div>
								</td>
							</tr>
							<tr>
								<td class="vt"><label style="width: 85px" for="">应用文件：</label></td>
								<td>
									<div class="g-input">
										<i class="f"></i><input type="text" readonly="" value=""
											id="capk" name="capk" /><i class="e"></i>
									</div>
									<div class="rel file-browse">
										<a href="javascript:;" class="upload">浏览</a>
										<form id="aapkForm">
											<input type="file" value="上传" class="upload" id="capkFile"
												name="photo" onchange="showSelectFile('addClient')" />
										</form>
									</div> <a href="javascript:addApk();" class="btn-uplod">上传</a> <img
									id="addUploading"
									src="${pageContext.request.contextPath}/images/loading.gif"
									alt="" class="ico-loading"
									style="display: none; padding-left: 15px;" />

								</td>
								<td><label for="">是否立即激活当前版本：</label></td>
								<td><input type="checkbox" id="cyActive" name="cisActive"
									class="checkbox" value="on"
									style="float: left; margin-top: 15px;" /><label
									class="dib mr30">是 </label><input id="cnActive"
									name="cisActive" type="checkbox" class="checkbox" value="off"
									style="float: left; margin-top: 15px;" /><label class="dib" />否</td>
							</tr>
							<tr>
								<td><label for="">应用名称：</label></td>
								<td>
									<div class="g-input">
										<i class="f"></i><input type="text" value="" id="cname"
											name="cname" /><i class="e"></i>
									</div>
								</td>
								<td colspan="2" rowspan="4" class="vt"><label for=""
									class="update-record">更新日志：</label> <textarea class="txt"
										id="cupdateLog" name="cupdateLog"></textarea></td>
							</tr>
							<tr>
								<td><label for="">图标：</label></td>
								<td><img src="" id="cicon" name="cicon" height="50px" /> <!-- <div class="rel file-browse">
										<a href="javascript:;" class="upload">上传</a>
										<form id="aiconForm">
											<input type="file" value="上传头像" class="upload" id="ciconFile"
												name="photo" onchange="addIcon();" />
										</form>
									</div> --></td>
							</tr>
							<tr>
								<td><label for="">版本：</label></td>
								<td>
									<div class="g-input">
										<i class="f"></i><input type="text" value="" id="version"
											readonly name="version" /><i class="e"></i>
									</div>
								</td>
							</tr>
						</table></li>
				</ul>
			</div>
			<div class="ft">
				<a href="javascript:addClient();" class="btn-true">确定</a> <a
					href="javascript:hideDiv('addClient','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>

		<!-- 修改 客户端-->
		<div id="editClient" class="g-dialog d-staff dialog">
			<div class="hd">
				修改<a href="javascript:hideDiv('editClient','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<ul class="tab-bd">
					<li class="item item0"><input id="cid" name="cid"
						type="hidden" /> <input id="capkUrl" name="capkUrl" type="hidden" />
						<input id="ciconUrl" name="ciconUrl" type="hidden" />
						<table>
							<colgroup>
								<col width="80" />
								<col width="300" />
								<col width="180" />
								<col />
							</colgroup>
							<tr>
								<td><label for="">发布类型：</label></td>
								<td>
									<div class="g-input" style="z-index: 1;">
										<i class="f"></i>
										<div class="g-drop rel">
											<div class="hd">
												<i class="ico-arrow-down"
													onclick="switchShow('editClientUl')"></i><input type="text"
													readonly="readonly" id="ctype" name="ctype" value="" />
											</div>
											<ul id="editClientUl" class="bd">
												<li><a href="javascript:setEditCType('Android应用');">Android应用</a></li>
												<li><a href="javascript:setEditCType('IOS应用');">IOS应用</a></li>
											</ul>
										</div>
										<i class="e"></i>
									</div>
								</td>
								<td><label style="width: 80px" for="">操作系统：</label></td>
								<td style="position: relative; left: -100px;">
									<div class="g-input">
										<i class="f"></i><input type="text" value="" id="cos" readonly
											name="cos" /><i class="e"></i>
									</div>
								</td>
							</tr>
							<tr>
								<td class="vt"><label style="width: 85px" for="">应用文件：</label></td>
								<td>
									<div class="g-input">
										<i class="f"></i><input type="text" readonly="" value=""
											id="capk" name="capk" /><i class="e"></i>
									</div>
									<div class="rel file-browse">
										<a href="javascript:;" class="upload">浏览</a>
										<form id="eapkForm">
											<input type="file" value="上传" class="upload" id="capkFile"
												name="photo" onchange="showSelectFile('editClient')" />
										</form>
									</div> <a href="javascript:editApk();" class="btn-uplod">上传</a> <img
									id="editUploading"
									src="${pageContext.request.contextPath}/images/loading.gif"
									alt="" class="ico-loading"
									style="display: none; padding-left: 15px;" />
								</td>
								<td><label for="">是否立即激活当前版本：</label></td>
								<td><input type="checkbox" id="cisActive" name="cisActive"
									class="checkbox" value="on"
									style="float: left; margin-top: 15px;" /><label
									class="dib mr30">是</label><input id="cisActive1"
									name="cisActive" type="checkbox" class="checkbox" value="off"
									style="float: left; margin-top: 15px;" /><label class="dib">否</label></td>
							</tr>
							<tr>
								<td><label for="">应用名称：</label></td>
								<td>
									<div class="g-input">
										<i class="f"></i><input type="text" value="" id="cname"
											name="cname" /><i class="e"></i>
									</div>
								</td>
								<td colspan="2" rowspan="4" class="vt"><label for=""
									class="update-record">更新日志：</label> <textarea class="txt"
										id="cupdateLog" name="cupdateLog"></textarea></td>
							</tr>
							<tr>
								<td><label for="">图标：</label></td>
								<td><img
									src="${pageContext.request.contextPath}/temp/tubiao.jpg"
									id="cicon" name="cicon" height="50px" /> <!-- <div class="rel file-browse">
										<a href="javascript:;" class="upload">上传</a>
										<form id="eiconForm">
											<input type="file" value="上传头像" class="upload" id="ciconFile"
												name="photo" onchange="editIcon();" />
										</form>
									</div> --></td>
							</tr>
							<tr>
								<td><label for="">版本：</label></td>
								<td>
									<div class="g-input">
										<i class="f"></i><input type="text" value="" id="version"
											readonly name="version" /><i class="e"></i>
									</div>
								</td>
							</tr>
						</table></li>
				</ul>
			</div>
			<div class="ft">
				<a href="javascript:editClient();" class="btn-true">确定</a> <a
					href="javascript:hideDiv('editClient','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>

		<!-- 插入视频 -->
		<div id="insertVideo" class="g-dialog g-dialog-site dialog">
			<div class="hd">
				插入视频<a href="javascript:hideDiv('insertVideo','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<div class="con">
					视频网址：
					<div class="g-input">
						<i class="f"></i><input id="videoSrc" name="videoSrc" type="text" /><i
							class="e"></i>
					</div>
				</div>
			</div>
			<div class="ft">
				<a href="javascript:submitVideo();" class="btn-true">确定</a> <a
					href="javascript:hideDiv('insertVideo','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>

		<!-- 提醒2 -->
		<div id="removeRoleTip" class="g-dialog g-dialog-tip dialog">
			<div class="hd">
				删除角色<a href="javascript:hideDiv('removeRoleTip','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<div id="removeRoleDesc" class="con">确认将角色一从列表中删除吗？</div>
			</div>
			<div class="ft">
				<a href="javascript:removeRole();" class="btn-true">确定</a> <a
					href="javascript:hideDiv('removeRoleTip','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>

		<!-- 提醒2 -->
		<div id="removeClientTip" class="g-dialog g-dialog-tip dialog">
			<div class="hd">
				删除客户端<a href="javascript:hideDiv('removeClientTip','coverdiv1');"
					class="ico-close"></a>
			</div>
			<div class="bd">
				<div id="removeClientDesc" class="con">确认将角色一从列表中删除吗？</div>
			</div>
			<div class="ft">
				<a href="javascript:removeClient();" class="btn-true">确定</a> <a
					href="javascript:hideDiv('removeClientTip','coverdiv1');"
					class="btn-false">取消</a>
			</div>
		</div>


		<!-- 消息提醒 -->
		<div id="messageDiv" class="g-dialog g-dialog-tip dialog">
			<div class="hd">
				消息提醒<a href="javascript:hideDiv('messageDiv','coverdiv1');"
					class="ico-close" style="display: block;"></a>
			</div>
			<div class="bd license-warm">
				<div class="con">License已过期，请及时更新！</div>
			</div>
		</div>

		<!-- 反馈详情  -->
		<div id="feedback" class="g-dialog d-staff dialog">
			<div class="hd">
				反馈内容<a href="javascript:hideDiv('feedback','coverdiv1');"
					class="ico-close" style="display: block;"></a>
			</div>
			<div class="bd" style="padding-right: 0px;">
				<div id="feedbackDesc" class="con"
					style="height: 300px; overflow: scroll; padding-right: 98px;"></div>
			</div>
			<div class="ft"></div>
		</div>


	</div>
</body>
</html>