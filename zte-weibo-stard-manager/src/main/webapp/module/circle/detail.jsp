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
<input type="hidden" id="groupId" value="${param.groupId}"/>
<div class="dialogBd1">
	<div class="detailWrap">
		<div class="fl datailFace">
			<img id="minipicurl" src="${pageContext.request.contextPath}/common/images/face.jpg" class="facePic"/>
			<p id="state">已启用</p>
		</div>
		<ul class="fr">
			<li><p id="name"></p></li>
			<li>圈子成员数：<span id="qzcys">0</span></li>
			<li>圈子分享数：<span id="qzwbs">0</span> </li>
			<li>圈子简介：<span id="groupIntroduction"></span> </li>
		</ul>
	</div>
</div>
<script>seajs.use("page/circle_detail");</script>
</body>
</html>

