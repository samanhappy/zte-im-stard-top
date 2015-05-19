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
            <div class="toolBar recordToolBar">
                <div class="fl barLink">
                    <a href="javascript:;" title="导出" id="exportRecordBtn">导出<i class="iconfont">&#xe60c;</i></a>
                </div>
                <select class="recordSelectBox">
					<!--option value="0">全局搜索</option> -->
                	<option value="1">按人搜索</option>
                </select>
                <div class="dateSelectBox">
                	起<input type="text" id="startTime"/>
                	止<input type="text" id="endTime"/>
                </div>
                <div class="SearchBoxBar recordSearch">
                    <i class="f"></i>
                    <input id="keyword" name="keyword" type="text" class="txt">
                    <i class="e"></i>
                    <i class="iconfont">&#xe617;</i>
                </div>
                <a href="javascript:;" class="fr sBtn">搜索</a>
            </div>
            <div class="dataList recordDataList">
                <table>
                    <thead>
                        <tr>
                            <th width="20"><input id="checkAll" name="checkAll" type="checkbox" class="checkbox"></th>
                            <th width="30">内容类型</th>
                            <th width="30">消息类型</th>
                            <th width="30">发起</th>
                            <th width="30">接收</th>
                            <th width="80">时间</th>
                            <th width="150">内容</th>
                            <th width="60">是否违规</th>
                        </tr>
                    </thead>
                    <tbody id="recordList"></tbody>
                </table>
                <div id="kkpager"></div>
            </div>
        </div>
    </div>
    <script>seajs.use("page/security_record_list");</script>
</body>
</html>

