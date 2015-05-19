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
<title>删除评论</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
<input type="hidden" id="id" value="${param.id}">
<div class="dialogBd">
	<div class="accountOpenWrap">
		<p>确定删除选中的1条评论吗？</p>
	</div>
</div>
<div class="dialogBtns">
	<a href="javascript:;" class="add" id="dialogOkBtn">确定</a>
    <a href="javascript:;" class="cancel" id="dialogCancelBtn">取消</a>
</div>
<script>seajs.use("page/microblog_delCommentTips");</script>
</body>
</html>

