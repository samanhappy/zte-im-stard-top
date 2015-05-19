<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>系统参数</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/common/style/reset.css"
	type="text/css" media="screen" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/common/style/page.css"
	type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js"
	type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath"
		value="${pageContext.request.contextPath}/">
	<div id="framePage">
		<div id="pageWrap">
			<div class="toolBar">
				<div class="fl newsBar">
					<a href="javascript:;" class="selected">系统参数</a> <a
						href="addressBook.jsp">通讯录参数</a> <a href="customAddressBook.jsp">通讯录自定义</a>
				</div>
			</div>
			<div class="">
				<form id="settingSystemForm"
					action="${pageContext.request.contextPath}/updateParam.do">
					<input type="hidden" id="id" name="id" value="system">
					<table class="systemDataTable">
						<tr>
							<th>密码定时过期:</th>
							<td><label><input type="radio" name="pwdExpire"
									value="1">是</label> <label><input type="radio"
									name="pwdExpire" value="0">否</label> 密码有效期<input id="pwdPeriod"
								name="pwdPeriod" type="text" class="txt1">天</td>
							<td width="300">如果超过密码有效期，系统在用户登录时强制修改密码</td>
						</tr>
						<tr>
							<th>密码长度：</th>
							<td><label><input type="radio" name="pwdLength"
									value="1">是</label> <label><input type="radio"
									name="pwdLength" value="0">否</label> 密码长度<input type="text"
								id="pwdMinLength" name="pwdMinLength" class="txt1">- <input
								type="text" id="pwdMaxLength" name="pwdMaxLength" class="txt1"></td>
							<td>设置密码长度，以保证密码的安全性</td>
						</tr>
						<tr>
							<th>密码强度：</th>
							<td><label><input type="radio" name="pwdCheck"
									value="1">是</label> <label><input type="radio"
									name="pwdCheck" value="0">否</label></td>
							<td>设置密码强度，密码必须包含数字和字母</td>
						</tr>
						<tr>
							<th>首次登录强制修改密码：</th>
							<td><label><input type="radio" name="pwdFirstCheck"
									value="1">是</label> <label><input type="radio"
									name="pwdFirstCheck" value="0">否</label></td>
							<td>首次登陆的用户，只能修改密码后才能做其他操作</td>
						</tr>
						<tr>
							<th>登录验校码支持：</th>
							<td><label><input type="radio" name="loginCheck"
									value="1">是</label> <label><input type="radio"
									name="loginCheck" value="0">否</label></td>
							<td>web登录页面，采用校验码增加登录安全性</td>
						</tr>
						<tr>
							<th>是否校验IP地址：</th>
							<td><label><input type="radio" name="ipCheck"
									value="1">是</label> <label><input type="radio"
									name="ipCheck" value="0">否</label></td>
							<td>如果开启IP检测，只允许设定的IP范围内登录</td>
						</tr>
						<tr>
							<th>登录认证方式：</th>
							<td><label><input type="radio" name="loginAuthType"
									value="LDAP">LDAP</label> <label><input type="radio"
									name="loginAuthType" value="DATABASE">数据库 </label> <label><input
									type="radio" name="loginAuthType" value="THIRD">三方数据库 </label></td>
							<td>登录 认证方式</td>
						</tr>
						<tr>
							<th>LDAP服务器URL：</th>
							<td>
								<div class="inputBox">
									<i class="f"></i> <input type="text" id="ldapUrl"
										name="ldapUrl"> <i class="e"></i>
								</div>
							</td>
							<td>例如：192.168.160.1：389</td>
						</tr>
						<tr>
							<th>baseDN：</th>
							<td>
								<div class="inputBox">
									<i class="f"></i> <input type="text" id="baseDN" name="baseDN">
									<i class="e"></i>
								</div>
							</td>
							<td>例如：192.168.160.1：389</td>
						</tr>
						<tr>
							<th>域名：</th>
							<td>
								<div class="inputBox">
									<i class="f"></i> <input type="text" id="domain" name="domain">
									<i class="e"></i>
								</div>
							</td>
							<td>例如：it.zte.com</td>
						</tr>
					</table>
					<input type="submit" value="保存" class="saveBtn updateSystemParam"> <input
						type="button" value="默认设置" class="resetBtn updateSystemParam" id="defaultValue" />
				</form>
			</div>
		</div>
	</div>
	<script>
		seajs.use("page/system_setting_sysem");
	</script>
</body>
</html>

