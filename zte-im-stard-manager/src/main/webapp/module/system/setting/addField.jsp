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
<title>新增字段</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
<form id="addFieldForm" action="${pageContext.request.contextPath}/saveOrUpdateParam.do">
<div class="dialogBd">
	<div class="employeeBd">
		<div class="addFieldBd">
			<div class="formitm">
                <label class="lab">字段名称:</label>
                <div class="ipt">
                    <div class="fl inputBox">
                        <i class="f"></i>
                        <input type="text" id="paramName" name="paramName">
                        <i class="e"></i>
                    </div>
                    <span class="errorTipts"></span>
                </div>
            </div>
            <div class="formitm">
                <label class="lab">字段类型:</label>
                <div class="ipt">
                    <p>
                    	<input type="radio" name="paramType" value="number">数字
                    	<input type="radio" name="paramType" value="date">日期
                    	<input type="radio" name="paramType" value="string">字符串
                    </p>
                    <span class="errorTipts"></span>
                </div>
            </div>
		</div>
	</div>
</div>
<div class="dialogBtns">
	<input type="submit" value="确定" class="add">
    <a href="javascript:;" class="cancel" id="dialogCancelBtn">取消</a>
</div>
</form>
<script>seajs.use("page/system_setting_addField");</script>
</body>
</html>

