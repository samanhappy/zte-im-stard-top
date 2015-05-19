<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String news_id = request.getParameter("id");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>企业新闻</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/common/style/reset.css"
	type="text/css" media="screen" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/common/style/page.css"
	type="text/css" media="screen" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/common/js/kindeditor-4.1.10/plugins/code/prettify.css" />
<script src="${pageContext.request.contextPath}/common/js/sea.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js"
	type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath"
		value="${pageContext.request.contextPath}/">
	<form id="newsForm"
		action="${pageContext.request.contextPath}/saveNews.do">
		<input type="hidden" id="news_id" name="news_id" value="<%=news_id%>">
		<div id="framePage">
			<div id="pageWrap">
				<div class="toolBar edit">
					<div class="fl newsBar">
						<a href="list.jsp">最近新闻</a> <a href="javascript:;"
							class="selected">发布新闻</a>
					</div>
				</div>
				<ul class="newsAddForm edit">
					<li><label class="fTitle">标题:</label>
						<div class="fl inputBox">
							<i class="f"></i> <input type="text" id="news_title"
								name="news_title" placeholder="请输入新闻标题"> <i class="e"></i>
						</div> <em class="fl">*</em>
						<div class="fr">
							<label><input type="radio" name="news_type" value="0">&nbsp;普通新闻</label>
							<label><input type="radio" name="news_type" value="1">&nbsp;封面新闻</label>
						</div></li>
					<li><label class="fTitle">内容:</label>
						<div class="newsEditor">
							<textarea id="news_con" name="news_con" cols="100" rows="8"
								style="width: 99.5%; height: 330px; visibility: hidden;"></textarea>
							<input id="news_con_text" name="news_con_text" type="hidden">
						</div></li>
					<li><label class="fTitle">&nbsp;</label>
						<div class="btns">
							<a href="javascript:" id="release" title="发布" class="release">发布</a><a
								href="list.jsp" title="取消" class="cancel">取消</a><a
								href="javascript:" id="preview" class="cancel">预览</a>
						</div></li>
				</ul>
				<div class="toolBar pre" style="display: none">
					<div class="fl barLink">
						<a id="pre_return" href="javascript:">返回</a>
					</div>
				</div>
				<div class="newsShowWrap pre" style="display: none">
					<h1 class="title">
						标题：<span id="pre_title"></span>
					</h1>
					<div class="info">
						作者：<span id="pre_author"></span> 日期：<span id="pre_date"></span>
					</div>
					<div id="pre_content" class="newsShowContent"></div>
					<div class="btns">
						<a href="javascript:" id="pre_release" title="发布" class="release">发布</a>
						<a href="list.jsp" title="取消" class="cancel">取消</a>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script>
		seajs.use("page/enterprise_news_editNews");
	</script>
</body>
</html>

