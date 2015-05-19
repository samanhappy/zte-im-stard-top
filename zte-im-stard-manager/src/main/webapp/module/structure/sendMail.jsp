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
<title>删除员工</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
<div class="dialogBd">
	<div class="employeeBd">
		<div class="sendMail">
			<p>新密码已成功发送至“张志义”的</p>
			<p>邮箱：zhangzhiyi@zte.com.cn</p> 
		</div>
	</div>
</div>
<div class="dialogBtns">
    <a href="javascript:;" class="add" id="dialogOkBtn">关闭</a>
</div>
<script>seajs.use("page/structure_sendMail");</script>
</body>
</html>

