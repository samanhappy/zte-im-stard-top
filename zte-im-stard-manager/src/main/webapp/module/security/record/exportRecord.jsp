<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String ids = request.getParameter("ids");
String desc = new String(request.getParameter("desc").getBytes("ISO-8859-1"),"UTF-8");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
<title>数据导出</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
<input type="hidden" id="ids" value="<%=ids%>">
<div class="dialogBd">
	<div class="employeeBd">
		<div class="exportEmployee">
			<%=desc%>
		</div>
	</div>
</div>
<div class="dialogBtns">
	<a href="javascript:;" class="add" id="dialogOkBtn">确定</a>
    <a href="javascript:;" class="cancel" id="dialogCancelBtn">取消</a>
</div>
<script>seajs.use("page/structure_record_exportRecord");</script>
</body>
</html>

