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
<title>聊天记录</title>
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
                    <a href="javascript:;" title="导出" id="exportRecordBtn">导出<i class="iconfont">&#xe60c;</i></a>
                </div>
                <select class="fl recordSelectBox">
                	<option value="1">全局搜索</option>
                	<option value="2">按时间搜索</option>
                	<option value="3">全局搜索</option>
                </select>
                <div class="fl dateSelectBox">
                	起<input type="text" id="startTime" />止<input type="text" id="endTime"/>
                </div>
                <div class="SearchBoxBar">
                    <i class="f"></i>
                    <input id="keyword" name="keyword" type="text" class="txt">
                    <i class="e"></i>
                    <i class="iconfont">&#xe617;</i>
                </div>
            </div>
            <div class="dataList">
                <table>
                    <thead>
                        <tr>
                            <th width="20"><input id="checkAll" name="checkAll" type="checkbox" class="checkbox"></th>
                            <th>标题</th>
                            <th width="60">发起</th>
                            <th width="60">分类</th>
                            <th width="130">消息时间</th>
                            <th width="80">处理情况</th>
                        </tr>
                    </thead>
                    <tbody id="recordList"></tbody>
                </table>
                <div id="kkpager"></div>
            </div>
        </div>
    </div>
    <script>seajs.use("page/security_sensitiveInfo_list");seajs.use("laydate-v1.1/laydate");</script>
</body>
</html>

