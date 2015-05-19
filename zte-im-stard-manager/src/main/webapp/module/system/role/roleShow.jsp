<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String roleId = request.getParameter("roleId");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
<title>登录</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
<input type="hidden" id="roleId" value="<%=roleId%>">
    <div id="framePage">
        <div id="pageWrap">
            <div class="toolBar">
                <div class="fl barLink">
                    <a href="roleAdd.jsp?roleId=<%=roleId%>" title="添加角色">添加角色<i class="iconfont">&#xe614;</i></a>
                    <a href="javascript:;" title="编辑角色" id="editRoleBtn">编辑角色<i class="iconfont">&#xe613;</i></a>
                    <a href="javascript:;" title="删除角色" id="delRoleBtn">删除角色<i class="iconfont">&#xe60e;</i></a>
                </div>
            </div>
            <div class="roleShowWrap">
                <div class="formitm">
                    <label class="lab">角色名称：</label>
                    <div id="roleName" class="ipt"></div>
                </div>
                <div class="formitm">
                    <label class="lab">角色描述：</label>
                    <div id="desc" class="ipt"> </div>
                </div>
                <div class="formitm" style="padding-bottom:0;">
                    <label class="lab">权限设置：</label>
                    <div class="ipt">
                        <ul>
                            <li id="strutureManage" class="display-none">
                                <span>组织架构：</span>
                                <dl id="deptManage" class="display-none">
                                    <dt>部门管理</dt>
                                    <dd>增加、修改、删除部门的权利</dd>
                                </dl>
                                <dl id="userManage" class="display-none">
                                    <dt>员工管理</dt>
                                    <dd>增加、修改、删除员工的权利</dd>
                                </dl>
                                <dl id="userPrivManage" class="display-none">
                                    <dt>员工权限设置</dt>
                                    <dd>设置个人信息访问的权利</dd>
                                </dl>
                                <dl id="userAbleManage" class="display-none">
                                    <dt>员工启用、停用</dt>
                                    <dd>启用或停用员工的权利</dd>
                                </dl>
                                <dl id="userImport" class="display-none">
                                    <dt>导入</dt>
                                    <dd>批量导入员工的权利</dd>
                                </dl>
                                <dl id="userExport" class="display-none">
                                    <dt>导出</dt>
                                    <dd>批量导出员工的权利</dd>
                                </dl>
                            </li>
                            <li id="orgManage" class="display-none">
                                <span>企业管理：</span>
                                <dl id="publishNews" class="display-none">
                                    <dt>发布新闻</dt>
                                    <dd>发布企业新闻的权利</dd>
                                </dl>
                                <dl id="editNews" class="display-none">
                                    <dt>编辑新闻</dt>
                                    <dd>修改已发布企业新闻的权利</dd>
                                </dl>
                                <dl id="removeNews" class="display-none">
                                    <dt>删除新闻</dt>
                                    <dd>删除已发布企业新闻的权利</dd>
                                </dl>
                                <dl id="updateCompInfo" class="display-none">
                                    <dt>企业信息修改</dt>
                                    <dd>修改企业名称、logo等信息的权利</dd>
                                </dl>
                            </li>
                            <li id="systemManage" class="display-none">
                                <span>系统管理：</span>
                                 <dl id="roleManage" class="display-none">
                                    <dt>角色管理</dt>
                                    <dd>设置角色权限和用户的权利</dd>
                                </dl>
                                <dl id="updateSystemParam" class="display-none">
                                    <dt>系统参数修改</dt>
                                    <dd>设置账号登录的权利</dd>
                                </dl>
                                <dl id="updateContactParam" class="display-none">
                                    <dt>通讯录参数修改</dt>
                                    <dd>设置通讯录单位名称、用户同步、成员属性保护、通讯录个人可设置属性</dd>
                                </dl>
                                <dl id="defineParam" class="display-none">
                                    <dt>通讯录自定义</dt>
                                    <dd>通讯录自定义字段的权利</dd>
                                </dl>
                                <dl id="licenseManage" class="display-none">
                                    <dt>License管理</dt>
                                    <dd>管理系统Licnse</dd>
                                </dl>
                                <dl id="clientManage" class="display-none">
                                    <dt>客户端管理</dt>
                                    <dd>管理企业信息的权利</dd>
                                </dl>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="formitm" style="border-bottom:none;">
                    <label class="lab">角色成员：</label>
                    <div class="ipt"><textarea id="roleUsers" readonly="readonly"></textarea></div>
                </div>
            </div>
        </div>
    </div>
    <script>seajs.use("page/system_role_roleShow");</script>
</body>
</html>

