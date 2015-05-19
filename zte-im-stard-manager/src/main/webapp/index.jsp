<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String name = "",jid = "";
if (session.getAttribute("name") != null) {
	name = (String) session.getAttribute("name");
}
if (session.getAttribute("jid") != null) {
	jid = (String) session.getAttribute("jid");
}
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
<title>领客企信管理平台</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/index.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
    <div id="header">
        <div id="title" class="fl logo">领客企信·管理平台</div>
        <div class="fr topBar">
            <a href="javascrit:;" class="fl"><i class="iconfont">&#xe604;</i></a>
            <a href="${pageContext.request.contextPath}/logout.do" title="退出登录" class="fl"><i class="iconfont">&#xe605;</i></a>
            <div class="fl userInfo">
                <img src="${pageContext.request.contextPath}/common/images/avatar.png" class="fl"/>
                <dl class="fl">
                    <dt><%=name%></dt>
                    <dd><%=jid%></dd>
                </dl>
            </div>
        </div>
    </div>
    <div id="leftMenu" class="nano">
        <ul class="menu">
            <li>
                <a href="${pageContext.request.contextPath}/module/structure/framePage.jsp" target="mainiframe" class="head selected"><i class="iconfont">&#xe608;</i>组织架构</a>
            </li>
            <li>
                <a href="javascript:;" class="head"><i class="iconfont">&#xe609;</i>企业管理<em class="iconfont">&#xe612;</em></a>
                <ul>
                	<li><a href="${pageContext.request.contextPath}/module/enterprise/workBench/list.jsp" target="mainiframe">工作台图片</a></li>
                    <li><a href="${pageContext.request.contextPath}/module/enterprise/news/list.jsp" target="mainiframe">企业新闻</a></li>
                    <li class="updateCompInfo"><a href="${pageContext.request.contextPath}/module/enterprise/enterpriseInfo.jsp" target="mainiframe">企业信息</a></li>
                    <li><a href="${pageContext.request.contextPath}/module/enterprise/modifyPwd.jsp" target="mainiframe">密码修改</a></li>
                </ul>
            </li>
            <li>
                <a href="javascript:;" class="head"><i class="iconfont">&#xe600;</i>安全审计<em class="iconfont">&#xe612;</em></a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/module/security/record/list.jsp" target="mainiframe">聊天记录</a></li>
                    <li><a href="${pageContext.request.contextPath}/module/security/sensitiveInfo/list.jsp" target="mainiframe">敏感信息</a></li>
                    <li><a href="${pageContext.request.contextPath}/module/security/ruleConfig/list.jsp" target="mainiframe">规则配置</a></li>
                </ul>
            </li>
            <li>
                <a href="javascript:;" class="head"><i class="iconfont">&#xe607;</i>系统管理<em class="iconfont">&#xe612;</em></a>
                <ul>
                    <li class="roleManage"><a href="${pageContext.request.contextPath}/module/system/role/roleList.jsp" target="mainiframe">角色管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/module/system/setting/sysem.jsp" target="mainiframe">参数设置</a></li>
                    <li class="licenseManage"><a href="${pageContext.request.contextPath}/module/system/license/info.jsp" target="mainiframe">许可证管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/module/system/systemLog/list.jsp" target="mainiframe">系统日志</a></li>
                    <li class="clientManage"><a href="${pageContext.request.contextPath}/module/system/clientManagement/list.jsp" target="mainiframe">客户端管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/module/system/feedBack/list.jsp" target="mainiframe">信息反馈</a></li>
                </ul>
            </li>
        </ul>
    </div>
    <span class="leftOpt leftClose"></span>
    <div id="main">
        <iframe src="${pageContext.request.contextPath}/module/structure/framePage.jsp" frameborder="0" width="100%" height="auto" scrolling="no" name="mainiframe" id="mainiframe"></iframe>
    </div>
    <div id="footer">深圳市中兴云服务有限公司  粤ICP备13026534号 经营许可证：粤B2-20130730 Copyright @ 2013-2014 版权所有</div>
    <input type="text" id="iframeIdVal" value="" />
    <script>seajs.use("page/index");</script>
</body>
</html>
