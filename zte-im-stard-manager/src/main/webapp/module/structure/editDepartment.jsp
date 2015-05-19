<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("deptId");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
<title>编辑部门</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
<form id="editDepartmentForm" action="${pageContext.request.contextPath}/updateDept.do">
	<input type="hidden" id="id" name="id" value="<%=id%>">
	<div class="dialogBd">
	    <div class="dialog-dept">
	        <div class="formitm">
	            <label class="lab">部门名称:</label>
	            <div class="ipt">
	                <i class="f"></i>
	                <input type="text" id="gname" name="gname" class="txt" autocomplete="off">
	                <i class="e"></i>
	            </div>
	            <span></span>
	        </div>
	        <div class="formitm">
	            <label class="lab">上级部门:</label>
	            <div class="ipt" id="openDept">
	                <i class="f"></i>
	                <input id="deptName" name="deptName" type="text" readonly="readonly" class="txt">
	                <input id="deptId" name="deptId" type="hidden" value="">
	                <input id="deptTId" type="hidden" value="">
	                <!-- <input id="fullId" name="deptId" type="hidden"> -->
	                <i class="e"></i>
	                <a href="javascript:"><i class="iconfont">&#xe611;</i></a>
	            </div>
	            <span></span>
	        </div>
	        <div class="formitm">
	            <label class="lab">部门描述:</label>
	            <div class="ipt">
	                <i class="f"></i>
	                <input id="deptDesc" name="deptDesc" type="text" class="txt">
	                <i class="e"></i>
	            </div>
	        </div>
	        <div class="formitm">
	            <label class="lab">排序号:</label>
	            <div class="ipt">
	                <i class="f"></i>
	                <input id="sequ" name="sequ" type="text" class="txt" autocomplete="off">
	                <i class="e"></i>
	                <p>注：排序只在当前级别下排序</p>
	            </div>
	            <span></span>
	        </div>
	    </div>
	</div>
	<div class="dialogBtns">
		<a href="javascript:;" class="delDept" id="dialogDelDept">删除部门</a>
		<input type="submit" value="更新" class="add">
	    <a href="javascript:;" class="cancel" id="dialogCancelDept">取消</a>
	</div>
</form>
<script>seajs.use("page/structure_editDepartment");</script>
</body>
</html>

