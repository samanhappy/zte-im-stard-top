<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
<title>同事圈·管理平台</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/login.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<!--[if IE 8]> 
<img src="${pageContext.request.contextPath}/common/images/loginBg.png" class="loginbg">
<![endif]--> 
<div class="loginWrap">
	<h1 class="loginLogo"><img src="${pageContext.request.contextPath}/common/images/loginLogo.png" alt="同事圈·管理平台"></h1>
	<div class="loginBd">
		<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
		<form id="login" action="${pageContext.request.contextPath}/login.do" method="post" autocomplete="off">
			<ul>
				<li class="userName"><input type="text" name="name" id="name" placeholder="账   号" ></li>
				<li class="userPwd"><input type="password" name="password" id="password" placeholder="密   码" autocomplete="off"></li>
				<li class="loginTipMsg"><em></em></li>
				<li><input type="submit" value="登&nbsp;&nbsp;录" class="loginBtn"></li>
				<!-- <li class="loginLinks"><label class="fl"><input type="checkbox">下次自动登录</label> -->
				<%-- <a href="${pageContext.request.contextPath}/forgetPwd.jsp" class="fr forget">忘记密码？</a> --%>
				</li>
			</ul>
		</form>
	</div>
</div>
<script>seajs.use("page/login");</script>
</body>
</html>
