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
<title>license信息</title>
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
                    <a href="javascript:;" title="新增" id="addClientBtn">新增<i class="iconfont">&#xe614;</i></a>
                    <a href="javascript:;" title="修改" id="editClientBtn">修改<i class="iconfont">&#xe613;</i></a>
                    <a href="javascript:;" title="删除" id="delClientBtn">删除<i class="iconfont">&#xe60e;</i></a>
                </div>
            </div>
            <div class="dataList">
                <table>
                    <thead>
                        <tr>
                            <th width="20"><input type="checkbox" id="checkAll" class="checkbox"></th>
                            <th width="80">客户端标识</th>
                            <th>客户端名称</th>
                            <th width="90">操作系统</th>
                            <th width="90">最新版本</th>
                            <th width="90">最新状态</th>
                            <th width="90">强制更新</th>
                            <th width="120">操作</th>
                        </tr>
                    </thead>
                    <tbody id="clientList"></tbody>
                </table>
                <div id="kkpager"></div>
            </div>
        </div>
    </div>
    <script>seajs.use("page/system_client_list");</script>
</body>
</html>

