<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("id");
String name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
String post = new String(request.getParameter("post").getBytes("ISO-8859-1"),"UTF-8");

%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
<title>删除员工</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
<input type="hidden" id="id" name="id" value="<%=id%>">
<input type="hidden" id="name" name="name" value="<%=name%>">
<div class="dialogBd">
	<div class="employeeBd">
		<div class="employeeSetting">
			<p><span><%=name%></span><%=post%><label><input type="checkbox" id="openSitting" checked>开启保护设置</label></p>
			<dl>
				<dt>可见职级</dt>
				<dd>
					<div id="visibleRank">
					</div>
				</dd>
				<dt>可见部门<i class="iconfont" id="selectDept">&#xe611;</i></dt>
				<dd>
					<div id="visibleDept">
					</div>
					<div id="deptPermDiv"></div>
				</dd>
				<dt>可见人员<i class="iconfont" id="selectPersonnel">&#xe611;</i></dt>
				<dd>
					<div id="visiblePersonnel"></div>
					<div id="userPermDiv"></div>
				</dd>
			</dl>
		</div>
	</div>
</div>
<!--S 未设置 -->
<div class="dialogBtns" id="noSettingBtn">
	<a href="javascript:;" class="noetting">清空权限</a>
	<a href="javascript:;" class="add" id="dialogOkBtn">保存</a>
    <a href="javascript:;" class="cancel" id="dialogCancelBtn">取消</a>
</div>
<!--E 未设置 -->
<!--S 已设置 -->
<div class="dialogBtns" id="SettingBtn">
	<a href="javascript:;" class="delDept" id="dialogCleanBtn">清空权限</a>
	<a href="javascript:;" class="add" id="dialogOkBtn">保存</a>
    <a href="javascript:;" class="cancel" id="dialogCancelBtn">取消</a>
</div>
<!--E 已设置 -->
<script>seajs.use("page/structure_employeeSetting");</script>
</body>
</html>

