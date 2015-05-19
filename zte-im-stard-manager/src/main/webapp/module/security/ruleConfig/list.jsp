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
<title>规则配置</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
    <div id="framePage">
        <div id="pageWrap">
            <div class="toolBar">
                <div class="fl barLink">
                    	设置可发送的敏感字&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" id="addSendBtn">添加<i class="iconfont">&#xe614;</i></a>
                </div>
            </div>
            <ul class="configBox" id="canSendList">
            </ul>
            <div class="toolBar">
                <div class="fl barLink">
                    	设定禁止发送的敏感字&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" id="addRuleBtn">添加<i class="iconfont">&#xe614;</i></a>
                </div>
            </div>
            <ul class="configBox" id="prohibitSendList">
            </ul>
        </div>
    </div>
    <script>seajs.use("page/security_ruleConfig_list");</script>
</body>
</html>

