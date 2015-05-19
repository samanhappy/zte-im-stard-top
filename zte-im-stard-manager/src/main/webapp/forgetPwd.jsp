<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>忘记密码-企业微信·管理平台</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/login.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<!--[if IE 8]> 
<img src="{pageContext.request.contextPath}/common/images/loginBg.jpg" class="loginbg">
<![endif]--> 
<div class="loginWrap">
	<h1>企业微信·管理平台</h1>
	<div class="loginBd">
		<form id="login" action="${pageContext.request.contextPath}/json/forgetPwd.json" method="post" autocomplete="off">
			<ul>
				<li class="userName"><i class="iconfont">&#xe603;</i><input type="text" name="name" id="name" placeholder="请输入账号" autocomplete="off"></li>
				<li class="userMail"><i class="iconfont">&#xe606;</i><input type="text" name="email" id="email" placeholder="请输入邮箱" autocomplete="off"></li>
				<li class="loginTipMsg"><em></em></li>
				<li><a href="${pageContext.request.contextPath}/login.jsp" class="back">返回登录</a><input type="submit" value="提&nbsp;&nbsp;交" class="loginBtn"></li>
			</ul>
		</form>
	</div>
</div>
<script>seajs.use("page/forgetPwd");</script>
</body>
</html>
