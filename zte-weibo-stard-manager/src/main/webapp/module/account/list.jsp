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
<title>账号管理列表</title>
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
                    <a href="javascript:;" title="默认关注" id="setBtn">默认关注</a>
                    <a href="javascript:;" title="取消默认关注" id="cancleSetBtn">取消默认关注</a>
                    <!-- <a href="javascript:;" title="部门关注" id="setDeptBtn">部门关注</a>
                    <a href="javascript:;" title="取消部门关注" id="cancleDeptBtn">取消部门关注</a> -->
                    
                    <a href="javascript:;" title="启用" id="openBtn">启用</a>
                    <a href="javascript:;" title="停用" id="stopBtn">停用</a>
                    <!-- <a href="javascript:;" title="导入" id="importBtn">导入</a> -->
                    <a href="javascript:;" title="导出" id="exportBtn">导出</a>
                    <!-- <a href="javascript:;" title="初始化数据" id="initDataBtn">初始化数据</a> -->
                </div>
                <div class="fr searchBox">
                	<input type="text" name="name" id="name" placeholder="关键字搜索" value="">
                	<a href="javascript:;" class="searchBtn" title="搜索">搜索</a>
				</div>
            </div>
            <div class="dataList">
                <table>
                    <thead>
                        <tr>
                            <th width="20"><input id="checkAll" name="checkAll" type="checkbox" class="checkbox"></th>
                            <th width="30">序号</th>
                            <th>分享名</th>
                            <!-- <th width="90">
                            	<div class="titleItem">
                            		<span>状态<i class="icon downIco"></i></span>
                            		<div class="showItem">
                            			<em></em>
                            			<span id="state">
	                            			<a href="javascript:;" data-val="" class="selected">全部</a>
	                            			<a href="javascript:;" data-val="00">已启用</a>
	                            			<a href="javascript:;" data-val="99">已停用</a>
	                            		</span>
                            		</div>
                            	</div>
							</th> -->
                        </tr>
                    </thead>
                    <tbody id="accountList"></tbody>
                </table>
                <div id="kkpager"></div>
            </div>
        </div>
    </div>
    <script>seajs.use("page/account_list", function(test){
    	window.search = test.search;
    });</script>
</body>
</html>