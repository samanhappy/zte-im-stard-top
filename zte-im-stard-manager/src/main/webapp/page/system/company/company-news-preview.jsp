<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="iframe-html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻预览</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />
<script type="text/javascript">
	
</script>
</head>
<body>
	<%
		String title = new String(request.getParameter("title").getBytes(
				"ISO-8859-1"), "utf-8");
		String author = new String(request.getParameter("author").getBytes(
				"ISO-8859-1"), "utf-8");
		String date = request.getParameter("date");
		String imgUrl = request.getParameter("imgUrl");
		String content = new String(request.getParameter("con").getBytes(
				"ISO-8859-1"), "utf-8");
		String videoUrl = new String(request.getParameter("videoUrl")
				.getBytes("ISO-8859-1"), "utf-8");
		String newsType = request.getParameter("newsType");
	%>
	<input type="hidden" id="title" name="title" value="<%=title%>" />
	<input type="hidden" id="author" name="author" value="<%=author%>" />
	<input type="hidden" id="date" name="date" value="<%=date%>" />
	<input type="hidden" id="imgUrl" name="imgUrl" value="<%=imgUrl%>" />
	<input type="hidden" id="content" name="content" value="<%=content%>" />
	<input type="hidden" id="videoUrl" name="videoUrl"
		value="<%=videoUrl%>" />
	<input type="hidden" id="newsType" name="newsType"
		value="<%=newsType%>" />
	<!-- 企业管理主体部分 [[ -->
	<div class="c-company">
		<div class="inner" style="height: 80%">
			<div class="news-preview">
				<div class="top">
					<a href="company-news-list.jsp">返回</a>
				</div>
				<div class="title clearfix">
					<span class="l">标题：<%=title%></span>
				</div>
				<div class="info">
					<span>作者： <%=author%></span><span class="ml30">日期：<%=date%></span>
				</div>
				<div class="con">
					<%
						if (!imgUrl.equals("undefined")) {
					%>
					<img src="<%=imgUrl%>" class="mr20" alt="" />
					<%
						}
					%>

					<div id="player" name="player">
						<%
							if (videoUrl != null && !"".equals(videoUrl)) {
						%>
						<embed allowFullScreen="true" id="embedid" quality="high"
							width="620" height="500" align="middle"
							allowScriptAccess="always" type="application/x-shockwave-flash"
							src="<%=videoUrl%>"></embed>
						<%
							}
						%>
					</div>
					<p id="newsCon" name="newsCon">
						<%=content%>
					</p>
				</div>

				<!-- 
				<div class="btn-group mt30">
					<a href="company-news-list.jsp" class="btn-true">确定</a><a href="company-news-list.jsp" class="btn-false">取消</a>
				</div>
				-->
			</div>
		</div>
	</div>
	<!-- 企业管理主体部分 ]] -->
	</div>
</body>
</html>