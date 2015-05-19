<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String news_id = request.getParameter("id");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
<title>企业新闻</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js"
	type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath"
		value="${pageContext.request.contextPath}/">
	<input type="hidden" id="news_id" name="news_id" value="<%=news_id%>">
    <div id="framePage">
        <div id="pageWrap">
            <div class="toolBar">
                <div class="fl barLink">
                    <a href="list.jsp">返回</a>
                </div>
            </div>
            <div class="newsShowWrap">  
                <h1 class="title">标题：<span id="news_title"></span></h1>
                <div class="info">作者：<span id="author"></span>      日期：<span id="date"></span></div>
                <div id="content" class="newsShowContent">
                </div>
            </div>
        </div>
    </div>
    <script>
		seajs.use("page/enterprise_news_show");
	</script>
</body>
</html>

