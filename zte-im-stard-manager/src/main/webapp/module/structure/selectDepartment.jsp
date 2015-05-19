<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("id");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
<title>添加部门</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<input type="hidden" id="id" name="id" value="<%=id%>">
<div class="dialogBd">
    <div class="fl dialogDeptTree">
        <form action="">
            <ul id="deptTree" class="ztree"></ul>
        </form>
    </div>
    <div class="fr dialogDeptSearch">
    	<div class="inputBox">
           <i class="f"></i>
           <input type="text" id="orgSearch" name="orgSearch">
           <i class="e"></i>
           <i class="iconfont">&#xe617;</i>
        </div>
    </div>
</div>
<div class="dialogBtns">
    <a href="javascript:;" class="add" id="dialogDeptConfirm">确定</a>
    <a href="javascript:;" class="cancel" id="dialogDeptCancel">取消</a>
</div>
<script>seajs.use("page/structure_selectDepartment");</script>
</body>
</html>

