<%@ page language="java" import="java.util.*" pageEncoding="Utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
<title>分享管理列表</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
    <input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
    <input type="hidden" id="twitterId" value="${twitter.twitterId}">
	<input type="hidden" id="cPage" value="1">
    <div id="framePage">
        <div id="pageWrap">
            <div class="pageBar">
                <div class="fl barLink">
                	<a href="javascript:;" title="删除分享" data-val="1" id="delBtn">删除分享</a>
                    <a href="javascript:history.go(-1);" title="返回上级" id="exportBtn">返回上级</a>
                </div>
            </div>
            <span class="pageName">分享详情</span>
            <div class="microblogWrap">
            	<%-- <div class="microblogDetailWrap">
            		<img src="${pageContext.request.contextPath}/common/images/avatar.png" height="56" width="56" class="fl microblogAvatar">
            		<div class="microblogDetailBox">
            			<h1 class="account">名称</h1>
            			<p class="forwardBox">智慧园区落地智慧园区落地智慧园区落地智慧园区落地智慧园区落地智慧园区落地智慧园区落地智慧园区落地智慧园区落地</p>
            			<div class="microblogContent" style="background: none;padding:0;">
            				<ul class="picList" style="padding:0;">
		                		<li><img src="${pageContext.request.contextPath}/common/images/pic.jpg" height="80" width="80"></li>
		                		<li><img src="${pageContext.request.contextPath}/common/images/pic.jpg" height="80" width="80"></li>
		                		<li><img src="${pageContext.request.contextPath}/common/images/pic.jpg" height="80" width="80"></li>
		                		<li><img src="${pageContext.request.contextPath}/common/images/pic.jpg" height="80" width="80"></li>
		                		<li><img src="${pageContext.request.contextPath}/common/images/pic.jpg" height="80" width="80"></li>
		                		<li><img src="${pageContext.request.contextPath}/common/images/pic.jpg" height="80" width="80"></li>
	                		</ul>
						</div>
						<div style="padding-top:10px;"><span class="position" style="margin-left:0;">中兴全球云计算中心</span></div>
						<div class="sendBox"><span class="fl open">公开</span><span class="fr">1分钟前 来自 企业分享</span></div>
            		</div>
            	</div> --%>
                <div class="microblogDetailWrap">
                	<c:choose>
                		<c:when test="${not empty twitter.minipicurl }">
		                	<img src="${twitter.minipicurl}" height="56" width="56" class="fl microblogAvatar">
                		</c:when>
                		<c:otherwise>
                			<img src="${pageContext.request.contextPath}/common/images/face.png" height="56" width="56" class="fl microblogAvatar">
                		</c:otherwise>
                	</c:choose>
                	<div class="microblogDetailBox">
                		<!-- <h1 class="account">田雨辰</h1> -->
                		<h1 class="account">${twitter.userName }</h1>
                		<p class="forwardBox">${twitter.searchContent }
                		<c:if test="${not empty twitter.imgSrc }">
                			<div class="microblogContent" style="background: none;padding:0;">
	            				<ul class="picList" style="padding:0;">
	            					<c:forEach var="temp" items='${fn:split(twitter.imgSrc, ";")}'>
	                					<c:if test="${not empty temp }">
			                				<li><img src="${temp }" height="80" width="80"></li>
	                					</c:if>
	                				</c:forEach>
		                		</ul>
							</div>
                		</c:if>
<!--                 		<p class="forwardBox">转发分享//<span>@李阳阳</span>: 真棒！<span>@田雨辰</span><span>@赵欣</span><span>@丁丁</span> //<span>@梁振宁</span>: 转发分享</p> -->
						<c:if test="${not empty twitter.sourceTTwitter }">
                		<div class="microblogContent">
                			<em class="dot"></em>
                			<div class="content">@${twitter.sourceTTwitter.userName }：${twitter.sourceTTwitter.searchContent } 
                			<c:if test="${not empty twitter.sourceTTwitter.address }">
                				<span class="position">${twitter.sourceTTwitter.address }</span>
                			</c:if>
                			</div>
<!--                 			<div class="content">@田雨：智慧园区落地。 <span class="position">中兴全球云计算中心</span></div> -->
							<c:if test="${not empty twitter.sourceTTwitter.imgSrc }">
	                			<ul class="picList">
	                				<c:forEach var="temp" items='${fn:split(twitter.sourceTTwitter.imgSrc, ";")}'>
	                					<c:if test="${not empty temp }">
			                				<li><img src="${temp }" height="80" width="80"></li>
	                					</c:if>
	                				</c:forEach>
	<%--                 				<li><img src="${pageContext.request.contextPath}/common/images/pic.jpg" height="80" width="80"></li> --%>
	                			</ul>
							</c:if>
                			<div class="microblogNum">
                				<span class="fl sendTime">${twitter.sourceTTwitter.createTimeStr } 来自 企业分享 </span>
<!--                 				<span class="fl sendTime">11月12日 21:41 来自 企业分享 </span> -->
                				<p class="fr tools"><span class="forward">转发<em>${twitter.sourceTTwitter.forwardNum }</em></span>
                				<span class="commentary">评论<em>${twitter.sourceTTwitter.commentNum }</em></span>
                				<span class="praise">赞 <em>${twitter.sourceTTwitter.supportNum }</em></span></p>
<!--                 				<p class="fr tools"><span class="forward">转发<em>65</em></span><span class="commentary">评论<em>28</em></span><span class="praise">赞 <em>12</em></span></p> -->
                			</div>
                		</div>
                		</c:if>
                		<div style="padding-top:10px;">
               			<c:if test="${not empty twitter.address }">
               				<span class="position" style="margin-left: 0">${twitter.address }</span></p>
                		</c:if>
                		</div>
                		<div class="sendBox"><span class="fl open">${twitter.visibleRange }</span><span class="fr">${twitter.createTimeStr } 来自 企业分享</span></div>
                	</div>
                </div>
                <div class="commentWrap">
                	<ul class="commentOpt">
                		<li class="forward"><span>转发<em>${twitter.forwardNum }</em></span></li>
                		<li class="commentary"><span>评论<em>${twitter.commentNum }</em></span></li>
                		<li class="praise"><span>赞<em>${twitter.supportNum }</em></span></li>
<!--                 		<li class="forward"><span>转发<em>1</em></span></li>
                		<li class="commentary"><span>评论</span></li>
                		<li class="praise"><span>赞</span></li> -->
                	</ul>
                	<ul class="commentList" id="commentList">
                	</ul>
                	<div id="kkpager"></div>
                </div>
            </div>
        </div>
    </div>
    <script>seajs.use("page/microblog_detail",function(detail){
    	window.search = detail.search;
    });</script>
</body>
</html>