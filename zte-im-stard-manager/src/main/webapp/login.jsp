<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录-领客企信·管理平台</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/login.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
<script type="text/javascript">
	if (window != top)
		top.location.href = location.href;
</script>
</head>
<body>
<!--[if IE 8]> 
<img src="${pageContext.request.contextPath}/common/images/loginBg.jpg" class="loginbg">
<![endif]--> 
<div class="loginWrap">
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
	<h1 id="title">领客企信·管理平台</h1>
	<div class="loginBd">
		<form id="login" action="${pageContext.request.contextPath}/login.do" method="post" autocomplete="off">
			<ul>
				<li class="userName"><i class="iconfont">&#xe603;</i><input type="text" name="name" id="name" placeholder="账号" autocomplete="off"></li>
				<li class="userPwd"><i class="iconfont">&#xe600;</i><input type="password" name="password" id="password" placeholder="密码" autocomplete="off"></li>
				<li class="loginTipMsg"><em></em></li>
				<li><a href="${pageContext.request.contextPath}/forgetPwd.jsp" class="forget">忘记密码？</a><input type="submit" value="登&nbsp;&nbsp;录" class="loginBtn"></li>
			</ul>
		</form>
	</div>
</div>
<script>seajs.use("page/login");</script>
</body>
</html>
