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
<title>设置敏感词</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
<form id="addCanSendForm" action="${pageContext.request.contextPath}/json/login.json">
<div class="dialogBd">
	<div class="employeeBd">
		<div class="exportEmployee">
			<div class="inputBox ruleConfigBox" style="padding-top:40px;">
                <i class="f"></i>
                <input type="text" id="words" name="words">
                <i class="e"></i>
            </div>
		</div>
	</div>
</div>
<div class="dialogBtns">
	<input type="submit" value="确定" class="add">
    <a href="javascript:;" class="cancel" id="dialogCancelBtn">取消</a>
</div>
</form>
<script>seajs.use("page/security_ruleConfig_addCanSend");</script>
</body>
</html>

