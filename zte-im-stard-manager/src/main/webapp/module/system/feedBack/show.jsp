<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>反馈内容</title>
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
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
	<input type="hidden" id="id" name="id" value="<%=id%>">
	<div class="dialogBd">
		<div id="feedBackShow" class="feedBackShow">
			
		</div>
	</div>
	<div class="dialogBtns"></div>
	<script>
		seajs.use("page/system_feedback_show");
	</script>
</body>
</html>

