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
<title>系统日志</title>
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
                <div class="fl newsBar" id="type">
                    <a href="javascript:;" data-val="oper" class="selected">操作日志</a>
                    <a href="javascript:;" data-val="run">运行日志</a>
                    <a href="javascript:;" data-val="user">用户日志</a>
                    <a href="javascript:;" data-val="secure">安全日志</a>
                </div>
            </div>
            <div class="dataList">
                <table>
                    <thead>
                        <tr>
                            <th width="90">姓名</th>
                            <th width="90">工号</th>
                            <th width="90">操作</th>
                            <th width="120">操作时间</th>
                            <th>操作内容</th>
                        </tr>
                    </thead>
                    <tbody id="logList"></tbody>
                </table>
                <div id="kkpager"></div>
            </div>
        </div>
    </div>
    <script>seajs.use("page/system_systemLog_list");</script>
</body>
</html>

