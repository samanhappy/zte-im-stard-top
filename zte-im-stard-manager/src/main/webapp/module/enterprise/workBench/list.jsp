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
<title>工作台图片</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
    <div id="framePage">
        <div id="pageWrap">
            <div class="workBenchWrap">
            	<div class="workBenchBox">
            		<ul id="workBenchList">
            		</ul>
            	</div>
            	<div class="workBenchOpt">
            		<ul class="fl">
            			<li><span>说明：</span>1、工作台图片不得少于1张，最多只能上传4张</li>
            			<li><span>&nbsp;</span>2、编辑状态下，可在左右手动拖动图片进行排序</li>
            			<li><span>&nbsp;</span>3、图片样式</li>
            			<li><span>&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;尺寸：360px*200px</li>
            			<li><span>&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;大小:不超过300K</li>
            			<li><span>&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格式:PNG、JPG、GIF</li>
            		</ul>
            		<div class="fr workBenchBtn">
            			<a href="javascript:;" class="disSaveBtn" id="saveBtn" title="保存">保存</a>
            			<a href="edit.jsp" class="editBtn" id="editBtn" title="编辑">编辑</a>
            		</div>
            	</div>
            </div>
        </div>
    </div>
    <script>seajs.use("page/enterprise_workBench_list");</script>
</body>
</html>