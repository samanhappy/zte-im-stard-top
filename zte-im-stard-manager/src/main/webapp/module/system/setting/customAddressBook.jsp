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
<title>通讯录自定义</title>
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
                    <a href="sysem.jsp">系统参数</a>
                    <a href="addressBook.jsp">通讯录参数</a>
                    <a href="javascript:;" class="selected">通讯录自定义</a>
                </div>
            </div>
            <div class="toolBar defineParam">
                <div class="fl barLink"><a href="javascript:;" id="addFieldBtn">新增字段<i class="iconfont">&#xe614;</i></a></div>
            </div>
            <ul class="customAddressBookList" id="customAddressBookList"></ul>
        </div>
    </div>
    <script>seajs.use("page/system_setting_customAddressBook");</script>
</body>
</html>

