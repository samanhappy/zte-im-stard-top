<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>人员列表</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/common/style/reset.css"
	type="text/css" media="screen" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/common/style/page.css"
	type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js"
	type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath"
		value="${pageContext.request.contextPath}/">
	<input type="hidden" id="id" name="id" value="<%=id%>">
	<div class="dialogBd">
		<div class="selectPersonnel">
			<div class="selectPersonnelSearchBox">
				<div class="SearchBoxBar">
					<i class="f"></i> <input id="keyword" name="keyword" type="text"
						class="txt"> <i class="e"></i> <i class="iconfont">&#xe617;</i>
				</div>
			</div>
			<div class="selectPersonnelDataList">
				<table>
					<thead>
						<tr>
							<th width="30"><input id="checkAll" name="checkAll"
								type="checkbox" class="checkbox"></th>
							<th>姓名<em id="nameSort"><a href="javascript:;"
									class="asc"></a></em></th>
							<th>工号<em id="numSort"><a href="javascript:;"
									class="normal"></a></em></th>
							<th>部门<em id="deptSort"><a href="javascript:;"
									class="normal"></a></em></th>
						</tr>
					</thead>
					<tbody id="structureSelectPersonnelList"></tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="dialogBtns">
		<a href="javascript:;" class="add" id="dialogDeptConfirm">确定</a> <a
			href="javascript:;" class="cancel" id="dialogDeptCancel">取消</a>
	</div>
	<script>
		seajs.use("page/system_role_selectPerson");
	</script>
</body>
</html>

