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
<title>密码修改</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
	<div id="framePage">
		<div id="pageWrap">
			<div class="modifyPwdWrap">
				<form id="modifyPwdForm" action="${pageContext.request.contextPath}/editPass.do">
					<div class="formitm">
						<label class="lab">原始密码：</label>
						<div class="ipt">
							<div class="fl inputBox">
								<i class="f"></i><input type="password" id="opass" name="opass" placeholder="请输入原始密码"><i class="e"></i>
							</div>
							<span class="fl"></span>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">新的密码：</label>
						<div class="ipt">
							<div class="fl inputBox">
								<i class="f"></i><input type="password" id="pass" name="pass" placeholder="输入新密码" /><i class="e"></i>
							</div>
							<span class="fl"></span>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">确认新密码：</label>
						<div class="ipt">
							<div class="fl inputBox">
								<i class="f"></i><input type="password" name="rpass" placeholder="确认密码" /><i class="e"></i>
							</div>
							<span class="fl"></span>
						</div>
					</div>
					<div>
						<input type="submit" value="保存" class="saveBtn">
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
		seajs.use("page/enterprise_modifyPwd");
	</script>
</body>
</html>

