<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="iframe-html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统参数</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />
<style type="text/css">
.t-params .set label {
	width: 50px;
}

.t-params .set1 label {
	width: 80px;
}

.t-params .set {
	padding-left: 10px;
}
</style>


<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.form.js"></script>

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
					$(".updateSystemParam").css('display', 'none');
					$(".updateContactParam").css('display', 'none');
					$(".defineParam").css('display', 'none');
				} else if (privileges != '\"all\"') {
					if (privileges.indexOf('updateSystemParam') == -1) {
						$(".updateSystemParam").css('display', 'none');
					}
					if (privileges.indexOf('updateContactParam') == -1) {
						$(".updateContactParam").css('display', 'none');
					}
					if (privileges.indexOf('defineParam') == -1) {
						$(".defineParam").css('display', 'none');
					}
				}

				loadParam();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});

		//单选组事件
		$(":checkbox").bind("click", function() {
			if (this.checked) {
				$(this).siblings().each(function() {
					this.checked = false;
				});
			}
		});
	});

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

	function showSystemParams() {
		$("#system-params-div").css("display", "block");
		$("#mail-params-div").css("display", "none");
		$("#mail-custom-div").css("display", "none");
		$("#system-params-li").addClass("active");
		$("#mail-params-li").removeClass("active");
		$("#mail-custom-li").removeClass("active");
	}

	function showMailParams() {
		$("#system-params-div").css("display", "none");
		$("#mail-params-div").css("display", "block");
		$("#mail-custom-div").css("display", "none");
		$("#system-params-li").removeClass("active");
		$("#mail-params-li").addClass("active");
		$("#mail-custom-li").removeClass("active");
	}

	function showMailCustom() {
		$("#system-params-div").css("display", "none");
		$("#mail-params-div").css("display", "none");
		$("#mail-custom-div").css("display", "block");
		$("#system-params-li").removeClass("active");
		$("#mail-params-li").removeClass("active");
		$("#mail-custom-li").addClass("active");
	}

	function updateSysParam() {
		var options = {
			success : function(data) {
				if (data.res.reCode && data.res.reCode == '1') {
					alert("保存成功");
					$('#scrollDiv').scrollTop(0);
				} else if (data.res.reCode && data.res.reCode == '2') {
					parent.location.href = '${pageContext.request.contextPath}/';
				} else {
					alert("更新失败");
				}

			},
			complete : function() {
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("数据操作出错");
			},
			url : '${pageContext.request.contextPath}/updateSysParam.do',
			type : 'POST',
			dataType : "json",
			clearForm : false,
			resetForm : false
		};
		$('#sysParam').unbind('submit');
		$('#sysParam').submit(function() {
			$(this).ajaxSubmit(options);
			return false;
		});

		$("#sysParam").submit();
	}

	function loadSysParam() {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/getSysParam.do',
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						} else if (data.data) {
							var param = data.data;
							$(
									"input[name=pwdExpire][value="
											+ param.pwdExpire + "]").attr(
									"checked", 'checked');
							$(
									"input[name=pwdLength][value="
											+ param.pwdLength + "]").attr(
									"checked", 'checked');
							$(
									"input[name=pwdCheck][value="
											+ param.pwdCheck + "]").attr(
									"checked", 'checked');
							$(
									"input[name=pwdFirstCheck][value="
											+ param.pwdFirstCheck + "]").attr(
									"checked", 'checked');
							$(
									"input[name=loginCheck][value="
											+ param.loginCheck + "]").attr(
									"checked", 'checked');
							$(
									"input[name=ipCheck][value="
											+ param.ipCheck + "]").attr(
									"checked", 'checked');
							$(
									"input[name=loginAuthType][value="
											+ param.loginAuthType + "]").attr(
									"checked", 'checked');
							$("#pwdPeriod").val(param.pwdPeriod);
							$("#pwdMinLength").val(param.pwdMinLength);
							$("#pwdMaxLength").val(param.pwdMaxLength);
							$("#ldapUrl").val(param.ldapUrl);
							$("#baseDN").val(param.baseDN);
							$("#domain").val(param.domain);
						}
					}
				});
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
										$(
												"input[name=pwdExpire][value="
														+ param.pwdExpire + "]")
												.attr("checked", 'checked');
										$(
												"input[name=pwdLength][value="
														+ param.pwdLength + "]")
												.attr("checked", 'checked');
										$(
												"input[name=pwdCheck][value="
														+ param.pwdCheck + "]")
												.attr("checked", 'checked');
										$(
												"input[name=pwdFirstCheck][value="
														+ param.pwdFirstCheck
														+ "]").attr("checked",
												'checked');
										$(
												"input[name=loginCheck][value="
														+ param.loginCheck
														+ "]").attr("checked",
												'checked');
										$(
												"input[name=ipCheck][value="
														+ param.ipCheck + "]")
												.attr("checked", 'checked');
										$(
												"input[name=loginAuthType][value="
														+ param.loginAuthType
														+ "]").attr("checked",
												'checked');
										$("#pwdPeriod").val(param.pwdPeriod);
										$("#pwdMinLength").val(
												param.pwdMinLength);
										$("#pwdMaxLength").val(
												param.pwdMaxLength);
										$("#ldapUrl").val(param.ldapUrl);
										$("#baseDN").val(param.baseDN);
										$("#domain").val(param.domain);
									} else if (param.id == 'contact') {
										$("#contactOrgName").val(
												param.contactOrgName);
										$(
												"input[name=syncLDAP][value="
														+ param.syncLDAP + "]")
												.attr("checked", 'checked');
										$("#contactLdapUrl").val(
												param.contactLdapUrl);
										$("#contactBaseDN").val(
												param.contactBaseDN);
										$("#protectedPropSpan").html(
												param.protectedPropNames);
										$("#protectedPropNames").val(
												param.protectedPropNames);
										$("#protectedPropVals").val(
												param.protectedPropVals);
										$("#editablePropSpan").html(
												param.editablePropNames);
										$("#editablePropNames").val(
												param.editablePropNames);
										$("#editablePropVals").val(
												param.editablePropVals);
									} else {
										var html = '<tr><td class="t">';
										html += param.paramName;
										html += "</td><td>";
										if (privileges == '\"all\"'
												|| (privileges != null && privileges
														.indexOf('defineParam') != -1)) {
											html += "<a href=\"javascript:parent.setEditParamValue('"
													+ param.id
													+ "','"
													+ param.paramName
													+ "','"
													+ param.paramType
													+ "');\" class='ml15'>编辑</a>";
											html += "<a href=\"javascript:removeParam('"
													+ param.id
													+ "','"
													+ param.paramName
													+ "')\" class='ml15'>删除</a>";
										}
										html += "</td><td></td></tr>";
										tbodyHtml += html;
									}
								}
							}
							$("#paramList").html(tbodyHtml);
						}
					}
				});
	}

	function resetSysParam() {

	}

	function setProp(div, names, vals) {
		$("#" + div + "Span").html(names);
		$("#" + div + "Names").val(names);
		$("#" + div + "Vals").val(vals);
	}

	function updateContactParam() {
		var options = {
			success : function(data) {
				if (data.res.reCode && data.res.reCode == '1') {
					alert("保存成功");
					$('#scrollDiv').scrollTop(0);
				} else if (data.res.reCode && data.res.reCode == '2') {
					parent.location.href = '${pageContext.request.contextPath}/';
				} else {
					alert("更新失败");
				}
			},
			complete : function() {
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("数据操作出错");
			},
			url : '${pageContext.request.contextPath}/updateContactParam.do',
			type : 'POST',
			dataType : "json",
			clearForm : false,
			resetForm : false
		};
		$('#contactForm').unbind('submit');
		$('#contactForm').submit(function() {
			$(this).ajaxSubmit(options);
			return false;
		});

		$("#contactForm").submit();
	}

	function resetContactParam() {

	}

	function removeParam(id, name) {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/removeParam.do',
					data : {
						id : id,
						paramName : name
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							loadParam();
							parent.loadParam();
						} else if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert("删除失败");
						}
					},
					complete : function() {
					}
				});
	}

	function showProtProp() {
		parent.showProtectedProp($("#protectedPropVals").val());
	}

	function showEditProp() {
		parent.showEditProp($("#editablePropVals").val());
	}
</script>
</head>
<body>
	<!-- 企业管理主体部分 [[ -->
	<div class="c-system">

		<div id="scrollDiv" class="inner" style="height: 80%">
			<div class="c-top">
				<ul class="g-tab-hd clearfix">
					<li id="system-params-li" class="active"><a
						href="javascript:showSystemParams();">系统参数</a></li>
					<li id="mail-params-li"><a href="javascript:showMailParams();">通讯录参数</a></li>
					<li id="mail-custom-li"><a href="javascript:showMailCustom();">通讯录自定义</a></li>
				</ul>
			</div>
			<div id="system-params-div">
				<form id="sysParam">
					<input name="id" type="hidden" value="system" />
					<table class="g-table t-mail-param t-params"
						style="margin-top: 25px; margin-bottom: 0px;">
						<colgroup>
							<col width="235" />
							<col width="170" />
							<col width="260" />
							<col />
						</colgroup>
						<tr>
							<td class="t">密码定时过期：</td>
							<td class="set"><input name="pwdExpire" type="checkbox"
								value="1" class="checkbox" /><label>是</label><input
								name="pwdExpire" type="checkbox" value="0" class="checkbox" /><label>否</label></td>
							<td><span class="l">密码有效期&nbsp;&nbsp;&nbsp;</span>
								<div class="g-input l" style="width: 26px;">
									<i class="f"></i><input id="pwdPeriod" name="pwdPeriod"
										type="text" style="width: 26px;" /><i class="e"></i>
								</div> <span class="l">&nbsp;&nbsp;&nbsp;天</span></td>
							<td>如果超过密码有效期，系统在用户登录时强制修改密码</td>
						</tr>
						<tr>
							<td class="t">密码长度：</td>
							<td class="set"><input name="pwdLength" type="checkbox"
								class="checkbox" value="1" /><label>是</label><input
								name="pwdLength" type="checkbox" class="checkbox" value="0" /><label>否</label></td>
							<td><span class="l">密码长度&nbsp;&nbsp;&nbsp;</span>
								<div class="g-input l" style="width: 26px;">
									<i class="f"></i><input id="pwdMinLength" name="pwdMinLength"
										type="text" style="width: 26px;" /><i class="e"></i>
								</div> <span class="l">&nbsp;&nbsp;&nbsp;——&nbsp;&nbsp;&nbsp;</span>
								<div class="g-input l" style="width: 26px;">
									<i class="f"></i><input id="pwdMaxLength" name="pwdMaxLength"
										type="text" style="width: 26px;" /><i class="e"></i>
								</div></td>
							<td>设置密码长度，以保证密码的安全性</td>
						</tr>
						<tr>
							<td class="t">密码强度：</td>
							<td class="set"><input name="pwdCheck" type="checkbox"
								class="checkbox" value="1" /><label>是</label><input
								name="pwdCheck" type="checkbox" class="checkbox" value="0" /><label>否</label></td>
							<td></td>
							<td>设置密码强度，密码必须包含数字和字母</td>
						</tr>
						<tr>
							<td class="t">首次登录强制修改密码：</td>
							<td class="set"><input name="pwdFirstCheck" type="checkbox"
								class="checkbox" value="1" /><label>是</label><input
								name="pwdFirstCheck" type="checkbox" class="checkbox" value="0" /><label>否</label></td>
							<td></td>
							<td>首次登陆的用户，只能修改密码后才能做其他操作</td>
						</tr>
						<tr>
							<td class="t">登录验校码支持：</td>
							<td class="set"><input name="loginCheck" type="checkbox"
								class="checkbox" value="1" /><label>是</label><input
								name="loginCheck" type="checkbox" class="checkbox" value="0" /><label>否</label></td>
							<td></td>
							<td>web登录页面，采用校验码增加登录安全性</td>
						</tr>
						<tr>
							<td class="t">是否校验IP地址：</td>
							<td class="set"><input name="ipCheck" type="checkbox"
								class="checkbox" value="1" /><label>是</label><input
								name="ipCheck" type="checkbox" class="checkbox" value="0" /><label>否</label></td>
							<td></td>
							<td>如果开启IP检测，只允许设定的IP范围内登录</td>
						</tr>
						<tr>
							<td class="t">登录认证方式：</td>
							<td class="set" colspan="2" style="border-right-color: #e4e4e4;"><input
								name="loginAuthType" type="checkbox" class="checkbox"
								value="LDAP" /><label>LDAP</label><input name="loginAuthType"
								type="checkbox" class="checkbox" value="DATABASE" /><label
								style="width: 80px; padding-right: 10px;">本地数据库</label><input
								name="loginAuthType" type="checkbox" class="checkbox"
								value="THIRD" /><label style="width: 80px;">三方数据库</label></td>
							<td>登录 认证方式</td>
						</tr>
						<tr>
							<td class="t">LDAP服务器URL：</td>
							<td colspan="2"><div class="g-input">
									<i class="f"></i><input id="ldapUrl" name="ldapUrl" type="text" /><i
										class="e"></i>
								</div></td>
							<td>例如：192.168.160.1：389</td>
						</tr>
						<tr>
							<td class="t">baseDN：</td>
							<td colspan="2"><div class="g-input">
									<i class="f"></i><input id="baseDN" name="baseDN" type="text" /><i
										class="e"></i>
								</div></td>
							<td>例如：192.168.160.1：389</td>
						</tr>
						<tr>
							<td class="t">域名：</td>
							<td colspan="2"><div class="g-input">
									<i class="f"></i><input id="domain" name="domain" type="text" /><i
										class="e"></i>
								</div></td>
							<td>例如：it.zte.com</td>
						</tr>
					</table>
				</form>
				<a href="javascript:updateSysParam();"
					class="btn-true mt30 updateSystemParam">保存</a><a
					href="javascript:resetSysParam();"
					class="btn-false mt30 ml30 updateSystemParam">默认设置</a>
			</div>
			<div id="mail-params-div" style="display: none"
				class="updateContactParam">
				<form id="contactForm">
					<input name="id" type="hidden" value="contact" />
					<table class="g-table t-mail-param"
						style="margin-top: 25px; margin-bottom: 0px;">
						<colgroup>
							<col width="235" />
							<col width="430" />
							<col />
						</colgroup>
						<tr>
							<td class="t">通讯录单位名称:</td>
							<td><div class="g-input">
									<i class="f"></i><input id="contactOrgName"
										name="contactOrgName" type="text" /><i class="e"></i>
								</div></td>
							<td>单位名称</td>
						</tr>
						<tr>
							<td class="t">是否同步LDAP:</td>
							<td><input name="syncLDAP" type="checkbox" class="checkbox"
								value="1" /><label>是</label><input name="syncLDAP"
								type="checkbox" class="checkbox" value="0"
								style="margin-left: 35px;" /><label>否</label></td>
							<td>从LDAP同步通讯录</td>
						</tr>
						<tr>
							<td class="t">LDAP服务器URL:</td>
							<td><div class="g-input">
									<i class="f"></i><input id="contactLdapUrl"
										name="contactLdapUrl" type="text" /><i class="e"></i>
								</div></td>
							<td>例如：192.168.160.1：389</td>
						</tr>
						<tr>
							<td class="t">BaseDN：</td>
							<td><div class="g-input">
									<i class="f"></i><input id="contactBaseDN" name="contactBaseDN"
										type="text" /><i class="e"></i>
								</div></td>
							<td>例如：CN=Users,DC=nj,DC=zte,DC=com,DC=cn</td>
						</tr>
						<tr>
							<td class="t">成员属性访问保护：</td>
							<td><a href="javascript:showProtProp();" class="r mr15">设置</a><span
								id="protectedPropSpan"></span><input id="protectedPropNames"
								name="protectedPropNames" type="hidden" /><input
								id="protectedPropVals" name="protectedPropVals" type="hidden" /></td>
							<td>设置受保护员工不可见属性</td>
						</tr>
						<tr>
							<td class="t">通讯录个人可设置的属性:</td>
							<td><a href="javascript:showEditProp();" class="r mr15">设置</a><span
								id="editablePropSpan"></span><input id="editablePropNames"
								name="editablePropNames" type="hidden" /><input
								id="editablePropVals" name="editablePropVals" type="hidden" /></td>
							<td>设置可更改属性</td>
						</tr>
					</table>
				</form>
				<a href="javascript:updateContactParam();"
					class="btn-true mt30 updateContactParam">保存</a><a
					href="javascript:resetContactParam();"
					class="btn-false mt30 ml30 updateContactParam">默认设置</a>
			</div>
			<div id="mail-custom-div" style="display: none; margin-bottom: 0px;">
				<div class="c-header c-header-gray mail-custom">
					<ul>
						<li class="jia defineParam"><a
							href="javascript:showDiv('addParam','coverdiv1');">新增字段<i></i></a></li>
					</ul>
				</div>
				<table class="g-table t-mail-custom">
					<colgroup>
						<col width="90" />
						<col width="250" />
						<col />
					</colgroup>
					<tbody id="paramList"></tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- 企业管理主体部分 ]] -->
</body>
</html>