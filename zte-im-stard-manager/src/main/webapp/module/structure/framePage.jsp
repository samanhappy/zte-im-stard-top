<%@page import="com.zte.im.util.Constant"%>
<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Object tenantId = request.getSession().getAttribute(
			Constant.TENANT_ID);
	String groupId = null;
	if (session.getAttribute("groupId") != null) {
		groupId = (String) session.getAttribute("groupId");
	}
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>登录</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page1.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
	<input type="hidden" id="tenantId" name="tenantId" value="<%=tenantId%>">
	<input type="hidden" id="groupId" name="groupId" value="<%=groupId%>">
	<div id="structrue">
		<p>
			<a href="javascript:;" title="添加部门" id="addDepartmentBtn" class="fl deptManage">添加部门</a> <a href="javascript:;"
				title="编辑部门" id="editDepartmentBtn" class="fr deptManage">编辑部门</a>
		</p>
		<ul id="deptTree" class="ztree"></ul>
	</div>
	<div id="pageMain">
		<iframe src="${pageContext.request.contextPath}/module/structure/list.jsp" frameborder="0" width="100%" height="auto"
			scrolling="no" name="pageFrame" id="pageFrame"></iframe>
	</div>
	<script>
		seajs.use("page/structure_framePage");
	</script>
</body>
</html>