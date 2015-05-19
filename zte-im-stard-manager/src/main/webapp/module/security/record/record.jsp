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
            <div class="recordBox">
            	<dl>
            		<dt>2014-12-09 16:11:05 谢辉0220000637 对 袁辉0220000810 说：</dt>
            		<dd>成功发送文件 "截图.zip"(1040.298 KB) </dd>
            		<dt>2014-12-09 16:11:05 谢辉0220000637 对 袁辉0220000810 说：</dt>
            		<dd>成功发送文件 "截图.zip"(1040.298 KB) </dd>
            		<dt>2014-12-09 16:11:05 谢辉0220000637 对 袁辉0220000810 说：</dt>
            		<dd>成功发送文件 "截图.zip"(1040.298 KB) </dd>
            	</dl>
            </div>
        </div>
    </div>
</body>
</html>

