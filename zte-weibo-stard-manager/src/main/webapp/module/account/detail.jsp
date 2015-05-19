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
<title>详情</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
<input type="hidden" id="uid" value="${param.uid}" />
<div class="dialogBd1">
	<div class="detailWrap">
		<div class="fl datailFace">
			<img id="minipicurl" src="${pageContext.request.contextPath}/common/images/face.jpg" class="facePic"/>
			<p id="state">已启用</p>
		</div>
		<ul class="fr">
			<li><p ><span id="name"></span></p></li>
			<li>原创分享数：<span id="ycwbs"></span></li>
			<li>转发分享数：<span id="zfwbs"></span></li>
			<li>创建圈子数：<span id="cjqzs"></span></li>
			<li>参与圈子数：<span id="cyqzs"></span></li>
		</ul>
	</div>
</div>
<script>seajs.use("page/account_detail");</script>
</body>
</html>

