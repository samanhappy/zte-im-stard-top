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
<title>企业新闻</title>
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
                <div class="fl newsBar">
                    <a href="javascript:;" class="selected">最近新闻</a>
                    <a href="addNews.jsp" class="publishNews">发布新闻</a>
                </div>
                <div class="selectBox">
                	<select name="days" id="days">
	                	<option value="0">显示所有日期</option>
	                	<option value="7">显示最近1周</option>
	                	<option value="30">显示最近1个月</option>
	                	<option value="90">显示最近3个月</option>
                	</select>
                </div>
                <div class="SearchBoxBar">
                    <i class="f"></i>
                    <input id="keyword" name="keyword" type="text" class="txt">
                    <i class="e"></i>
                    <i class="iconfont">&#xe617;</i>
                </div>
            </div>
            <div class="newsList">
                <table>
                    <thead>
                        <tr>
                            <th class="title">标题</th>
                            <th width="80">操作</th>
                            <th width="80">作者</th>
                            <th width="90">分类</th>
                            <th width="90">日期</th>
                            <th width="80">状态</th>
                        </tr>
                    </thead>
                    <tbody id="newsList"></tbody>
                </table>
                <div id="kkpager"></div>
            </div>
        </div>
    </div>
    <script>seajs.use("page/enterprise_news_list");</script>
</body>
</html>

