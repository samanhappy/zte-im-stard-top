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
<script src="${pageContext.request.contextPath}${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
    <div id="framePage">
        <div id="pageWrap">
            <div class="toolBar">
                <div class="fl newsBar" >
                    <a href="javascript:;" class="selected">全部</a>
                    <a href="javascript:;">系统异常</a>
                    <a href="javascript:;">用户反馈</a>
                </div>
            </div>
            <div class="dataList">
                <table>
                    <thead>
                        <tr>
                            <th width="40">姓名</th>
                            <th>联系方式</th>
                            <th>终端型号</th>
                            <th>操作型号</th>
                            <th>分辨率</th>
                            <th>反馈内容</th>
                            <th>反馈时间</th>
                        </tr>
                    </thead>
                    <tbody id="informationFeedbackList"></tbody>
                </table>
                <div id="kkpager"></div>
            </div>
        </div>
    </div>
    <script>seajs.use("page/system_informationFeedback");</script>
</body>
</html>

