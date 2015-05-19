<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String roleId = request.getParameter("roleId");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>添加角色</title>
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
	<input type="hidden" id="roleId" name="roleId" value="<%=roleId%>">
	<div id="framePage">
		<div id="pageWrap">
			<div class="toolBar">
				<div class="fl barLink">
					<a href="roleAdd.jsp" title="添加角色">添加角色<i class="iconfont">&#xe614;</i></a>
					<span class="disable">编辑角色&nbsp;<i class="iconfont">&#xe613;</i><a></a></span>
					<span class="disable">删除角色&nbsp;<i class="iconfont">&#xe60e;</i><a></a></span>
				</div>
			</div>
			<div class="roleShowWrap">
				<form
					action="${pageContext.request.contextPath}/saveOrUpdateRole.do"
					id="addRoleForm">
					<div class="formitm" style="padding-bottom: 25px;">
						<label class="lab">角色名称：</label>
						<div class="ipt" style="padding-top: 20px;">
							<div class="fl inputBox">
								<i class="f"></i> <input id="roleName" name="roleName"
									type="text" placeholder="请输入角色名称"> <i class="e"></i>
							</div>
							<b class="dot">*</b> <span></span>
						</div>
					</div>
					<div class="formitm" style="padding-bottom: 25px;">
						<label class="lab">角色描述：</label>
						<div class="ipt" style="padding-top: 20px;">
							<div class="fl inputBox">
								<i class="f"></i> <input id="desc" name="desc" type="text"
									placeholder="请输入角色描述"> <i class="e"></i>
							</div>
						</div>
					</div>
					<div class="formitm" style="padding-bottom: 0;">
						<label class="lab">权限设置：</label>
						<div class="ipt">
							<ul>
								<li><span>组织架构：</span>
									<dl>
										<dt>
											<label><input name="privileges" value="deptManage"
												type="checkbox">部门管理</label>
										</dt>
										<dd>增加、修改、删除部门的权利</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges" value="userManage"
												type="checkbox">员工管理</label>
										</dt>
										<dd>增加、修改、删除员工的权利</dd>
									</dl>
									<dl>
										<dt>
											<label><input type="checkbox" name="privileges"
												value="userPrivManage">员工权限设置</label>
										</dt>
										<dd>设置个人信息访问的权利</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges" value="userAbleManage"
												type="checkbox">员工启用、停用</label>
										</dt>
										<dd>启用或停用员工的权利</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges" value="userImport"
												type="checkbox">导入</label>
										</dt>
										<dd>批量导入员工的权利</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges" value="userExport"
												type="checkbox">导出</label>
										</dt>
										<dd>批量导出员工的权利</dd>
									</dl></li>
								<li><span>企业管理：</span>
									<dl>
										<dt>
											<label><input name="privileges" value="publishNews"
												type="checkbox">发布新闻</label>
										</dt>
										<dd>发布企业新闻的权利</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges" value="editNews"
												type="checkbox">编辑新闻</label>
										</dt>
										<dd>修改已发布企业新闻的权利</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges" value="removeNews"
												type="checkbox">删除新闻</label>
										</dt>
										<dd>删除已发布企业新闻的权利</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges" value="updateCompInfo"
												type="checkbox">企业信息修改</label>
										</dt>
										<dd>修改企业名称、logo等信息的权利</dd>
									</dl></li>
								<li><span>系统管理：</span>
									<dl>
										<dt>
											<label><input name="privileges" value="roleManage"
												type="checkbox">角色管理</label>
										</dt>
										<dd>设置角色权限和用户的权利</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges"
												value="updateSystemParam" type="checkbox">系统参数修改</label>
										</dt>
										<dd>设置账号登录的权利</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges"
												value="updateContactParam" type="checkbox">通讯录参数修改</label>
										</dt>
										<dd>设置通讯录单位名称、用户同步、成员属性保护、通讯录个人可设置属性</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges" value="defineParam"
												type="checkbox">通讯录自定义</label>
										</dt>
										<dd>通讯录自定义字段的权利</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges" value="licenseManage"
												type="checkbox">License管理</label>
										</dt>
										<dd>管理系统Licnse</dd>
									</dl>
									<dl>
										<dt>
											<label><input name="privileges" value="clientManage"
												type="checkbox">客户端管理</label>
										</dt>
										<dd>管理企业信息的权利</dd>
									</dl></li>
							</ul>
						</div>
					</div>
					<div class="formitm" style="border-bottom: 0; padding-bottom: 0;">
						<label class="lab">角色成员：</label>
						<div class="ipt">
							<div>
								<i class="iconfont" title="选择成员" id="selectPerson">&#xe611;</i>
							</div>
							<div style="padding-top: 11px;">
								<textarea id="roleUsers" readonly="readonly"></textarea>
							</div>
							<div id="roleUsersDiv">
							</div>
							<div>
								<input type="submit" value="保存" class="saveBtn"> <input
									type="button" value="取消" class="resetBtn" id="cancelBtn" />
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
		seajs.use("page/system_role_roleAdd");
	</script>
</body>
</html>

