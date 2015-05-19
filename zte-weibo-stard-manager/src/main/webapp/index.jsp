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
<title>同事圈·管理平台</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/index.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
    <div id="header">
        <div class="fl logo"><img src="${pageContext.request.contextPath}/common/images/logo.png" width="208" height="36"></div>
        <div class="fr topBar">
        	<a href="${pageContext.request.contextPath}/logout.do" title="退出登录" class="fl icon loginOutIco">退出登录</a>
            <!-- <a href="javascrit:;" class="fl icon msgIco" title="消息">消息</a> -->
            <div class="fl userInfo">
                <img src="${pageContext.request.contextPath}/common/images/avatar.png" class="fl"/>
                <dl class="fl">
                    <dt>${sessionScope.userid }</dt>
                    <dd>你好！</dd>
                </dl>
            </div>
        </div>
    </div>
    <div id="leftMenu">
        <ul class="menu">
            <li><a href="${pageContext.request.contextPath}/module/account/list.jsp" target="mainiframe" class="normal selected"><i class="icon accountIcon"></i>账号管理</a></li>
            <%-- <li><a href="${pageContext.request.contextPath}/module/circle/list.jsp" target="mainiframe" class="normal"><i class="icon circleIcon"></i>圈子管理</a></li> --%>
            <li><a href="${pageContext.request.contextPath}/module/microblog/list.jsp" target="mainiframe" class="normal"><i class="icon microblogIcon"></i>分享管理</a></li>
        </ul>
    </div>
    <div id="leftMinMenu">
    	<ul class="minMenu">
    		<li><a href="${pageContext.request.contextPath}/module/account/list.jsp" target="mainiframe" class="normal icon accountIcon selected" title="账号管理">账号管理</a></li>
           <%--  <li><a href="${pageContext.request.contextPath}/module/circle/list.jsp" target="mainiframe" class="normal icon circleIcon" title="圈子管理">圈子管理</a></li> --%>
            <li><a href="${pageContext.request.contextPath}/module/microblog/list.jsp" target="mainiframe" class="normal icon microblogIcon" title="分享管理">分享管理</a></li>
    	</ul>
    </div>
    <span class="leftOpt leftClose"></span>
    <div id="main">
        <iframe src="${pageContext.request.contextPath}/module/account/list.jsp" frameborder="0" width="100%" height="auto" scrolling="no" name="mainiframe" id="mainiframe"></iframe>
    </div>
    <div id="footer">深圳市中兴云服务有限公司  粤ICP备13026534号 经营许可证：粤B2-20130730 Copyright @ 2013-2014 版权所有</div>
    <input type="text" id="iframeIdVal" value="" />
    <script>seajs.use("page/index");</script>
</body>
</html>
