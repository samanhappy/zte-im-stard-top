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
<title>数据导出</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
<div class="dialogBd">
	<div class="accountSettingWrap">
		<h2>确定要初始化数据吗？初始化时会清空t_dept和t_user表的数据！</h2>
		<p><label><input type="checkbox" id="setAttentionDept" value="true" checked="checked">初始化完成后设置部门关注</label></p>
	</div>
</div>
	<div class="dialogBtns">
		<input type="button" class="add" id="dialogOkBtn" value="确定">
	    <a href="javascript:;" class="cancel" id="dialogCancelBtn">取消</a>
	</div>
<script>seajs.use("page/account_initData");</script>
</body>
</html>

