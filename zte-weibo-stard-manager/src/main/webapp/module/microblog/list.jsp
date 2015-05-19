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
<title>分享管理列表</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
    <input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
    <input type="hidden" id="cPage" value="1">
    <div id="framePage">
        <div id="pageWrap">
            <div class="pageBar">
                <div class="fl barLink">
                    <a href="javascript:;" title="导出" id="exportBtn">导出</a>
                    <a href="javascript:;" title="删除分享" id="delBtn">删除分享</a>
                </div>
                <div class="fr microblogSearch">
                	<select name="type" id="type">
                		<option value="0">搜全部</option>
                		<option value="1">搜分享</option>
                		<option value="2">搜用户</option>
                	</select>
                	<input type="text" name="timeStart" id="timeStart" placeholder="发布时间 起" class="dateInput">
                	<input type="text" name="timeEnd" id="timeEnd" placeholder="发布时间 止" class="dateInput">
                	<div class="keywordBox">
	                	<input type="text" name="keyword" id="keyword" placeholder="关键字搜索" value="">
	                	<a href="javascript:;" class="searchBtn" title="搜索">搜索</a>
					</div>
                </div>
            </div>
            <div class="dataList">
                <table>
                    <thead>
                        <tr>
                            <th width="20"><input id="checkAll" name="checkAll" type="checkbox"></th>
                            <th>分享内容</th>
                            <th width="100">分享类型</th>
                            <th width="150">发布账号</th>
                            <th width="140">发布时间</th>
                            <th width="80">转发数</th>
                            <th width="80">评论数</th>
                            <th width="80">赞数</th>
                        </tr>
                    </thead>
                    <tbody id="microblogList"></tbody>
                </table>
                <div id="kkpager"></div>
            </div>
        </div>
    </div>
    <script>seajs.use("page/microblog_list",function(list){
    	window.search = list.search;
    });</script>
    <script src="${pageContext.request.contextPath}/common/js/laydate-v1.1/laydate.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/date.js"></script>
</body>
</html>

