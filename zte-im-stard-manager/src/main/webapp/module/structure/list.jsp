<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("deptId");
	String deptName = null;
	if (request.getParameter("deptName") != null) {
		deptName = new String(request.getParameter("deptName")
				.getBytes("ISO-8859-1"), "UTF-8");
	} else if (session.getAttribute("deptName") != null) {
		deptName = (String) session.getAttribute("deptName");
	}
	String idInSession = null;
	if (session.getAttribute("groupId") != null) {
		idInSession = (String) session.getAttribute("groupId");
	}
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>员工列表</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
	<input type="hidden" id="id" name="id" value="<%=id%>">
	<input type="hidden" id="idInSession" name="idInSession" value="<%=idInSession%>">
	<input type="hidden" id="deptName" name="deptName" value="<%=deptName%>">
	<div id="framePage">
		<div id="pageWrap">
			<div class="tipsBox">
				<i class="iconfont">&#xe615;</i>
				<div class="fl">
					<p id="overdue">
						您所使用的服务将于<span class="endDate"></span>日到期，为了不影响系统的正常使用，请立刻联系我们。
					</p>
					<p id="expired">您所使用的服务已过期，请立刻联系我们。</p>
					<p>服务热线：400-8888888</p>
				</div>
				<div class="fr">
					<p>
						服务期：<span class="startDate"></span> 至 <span class="endDate"></span>
					</p>
				</div>
			</div>
			<div class="toolBar">
				<div class="fl barLink">
					<a href="javascript:;" title="添加员工" id="addEmployeesBtn" class="userManage">添加员工<i class="iconfont">&#xe60b;</i></a> <a
						href="javascript:;" title="编辑" id="editEmployeesBtn" class="userManage">编辑<i class="iconfont">&#xe60a;</i></a> <a href="javascript:;"
						title="删除" id="delEmployeesBtn" class="userManage">删除<i class="iconfont">&#xe60e;</i></a> <a href="javascript:;" title="导入"
						id="importEmployeesBtn" class="userImport">导入<i class="iconfont">&#xe60d;</i></a> <a href="javascript:;" title="导出"
						id="exportEmployeesBtn" class="userExport">导出<i class="iconfont">&#xe60c;</i></a>
				</div>
				<div class="SearchBoxBar">
					<i class="f"></i> <input id="keyword" name="keyword" type="text" class="txt"> <i class="e"></i> <i
						class="iconfont">&#xe617;</i>
				</div>
			</div>
			<div class="dataList">
				<table>
					<thead>
						<tr>
							<th width="20"><input id="checkAll" name="checkAll" type="checkbox" class="checkbox"></th>
							<th width="30">序号</th>
							<th>工号<em id="numSort"><a href="javascript:;" class="asc"></a></em></th>
							<th width="60">姓名<em id="nameSort"><a href="javascript:;" class="normal"></a></em></th>
							<th width="100">手机号</th>
							<th>邮箱</th>
							<th width="60">部门<em id="deptSort"><a href="javascript:;" class="normal"></a></em></th>
							<th width="40">职级</th>
							<th width="40">状态</th>
							<th width="40">操作</th>
						</tr>
					</thead>
					<tbody id="structureList"></tbody>
				</table>
				<div id="kkpager"></div>
			</div>
		</div>
	</div>
	<script>
		seajs.use("page/structure_list");
	</script>
</body>
</html>

