<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="iframe-html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统日志</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />
<style type="text/css">
.odd {
	background: #fcfcfc;
}

.c-company .inner,.c-system .inner {
	height: 85%;
}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/page.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Base64.js"></script>
<script type="text/javascript">
	var currentHeight;
	var currentWidth;
	$(window).resize(
			function() {
				var windowHeight = $(window).height();
				var windowWidth = $(window).width();

				if (currentHeight == undefined || currentHeight != windowHeight
						|| currentWidth == undefined
						|| currentWidth != windowWidth) {
					parent.adjust();
					currentHeight = windowHeight;
					currentWidth = windowWidth;
				}
			});

	$(function() {
		$("li").bind("click", function() {
			$(this).addClass('active');
			$(this).siblings().removeClass('active');
		});
	});

	function base2Str(unicode) {
		var str = '';
		for ( var i = 0, len = unicode.length; i < len; ++i) {
			str += String.fromCharCode(unicode[i]);
		}
		return str;
	}
	
	var pSize = 10;
	function queryLog(type, cPage) {
		var requestUrl = '${pageContext.request.contextPath}/queryLog.do?type='
				+ type;
		if (cPage) {
			requestUrl += "&cPage=" + cPage;
		}
		requestUrl += "&pSize=" + pSize;
		$
				.ajax({
					type : "POST",
					url : requestUrl,
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						} else {
							var html = '';
							var pageHtml = '';
							if (data.syslog && data.syslog.logList
									&& data.syslog.logList.length > 0) {
								for ( var i = 0; i < data.syslog.logList.length; i++) {
									var log = data.syslog.logList[i];
									if (i % 2 == 1) {
										html += '<tr class="odd">';
									} else {
										html += '<tr>';
									}
									html += "<td>" + log.uname + "</td>";
									html += "<td>" + log.uid + "</td>";
									html += "<td>" + log.oper + "</td>";
									html += "<td>" + log.creatime + "</td>";
									html += "<td>" + base2Str(BASE64.decoder(log.content)) + "</td>";
									html += "</tr>";
								}

								pageHtml += "<a href='javascript:queryLog(\""
										+ type + "\"," + data.syslog.previous
										+ ")' class='prev'></a>";
								var startI = 1;
								if (data.syslog.totalPage > 10) {
									if (data.syslog.currentPage > 5) {
										startI = data.syslog.currentPage - 5;
									}
									if (data.syslog.totalPage
											- data.syslog.currentPage < 5) {
										startI -= 4 - (data.syslog.totalPage - data.syslog.currentPage);
									}
								}

								for ( var i = startI, j = 1; i <= data.syslog.totalPage
										&& j <= 10; i++, j++) {
									pageHtml += "<a href='javascript:queryLog(\""
											+ type + "\"," + i + ")'";
									if (i == data.syslog.currentPage) {
										pageHtml += " class='active'";
									}
									pageHtml += (">" + i + "</a>");
								}
								pageHtml += "<a href='javascript:queryLog(\""
										+ type
										+ "\","
										+ (data.syslog.next <= data.syslog.totalPage ? data.syslog.next
												: data.syslog.totalPage)
										+ ")'  class='next'></a>";

							}

							$("#pageDiv").html(pageHtml);
							$("#logData").html(html);
						}
					}
				});
	}

	queryLog('oper');
</script>
</head>
<body>

	<!-- 企业管理主体部分 [[ -->
	<div class="c-system">
		<div class="inner">
			<div class="c-top">
				<ul class="g-tab-hd clearfix">
					<li class="active"><a href="javascript:queryLog('oper');">操作日志</a></li>
					<li><a href="javascript:queryLog('run');">运行日志</a></li>
					<li><a href="javascript:queryLog('user');">用户日志</a></li>
					<li><a href="javascript:queryLog('secure');">安全日志</a></li>
				</ul>
				<form class="g-search g-input">
					<span class="ico-search"></span> <i class="f"></i> <input
						type="text" /> <i class="e"></i>
				</form>
			</div>
			<table class="g-table tc">
				<colgroup>
					<col width="95" />
					<col width="155" />
					<col width="100" />
					<col width="100" />
					<col width="155" />
				</colgroup>
				<tr>
					<th>姓名</th>
					<th>工号</th>
					<th>操作</th>
					<th>操作时间</th>
					<th>操作内容</th>
				</tr>
				<tbody id="logData"></tbody>
			</table>
		</div>
		<!-- 分页 -->
		<div id="pageDiv" class="g-paging tc mtb30"
			style="position: fixed; left: 185px; bottom: 100px; width: 100%; height: 54px; padding-left: 0 !important; padding-left: 185px;"></div>
	</div>
	<!-- 企业管理主体部分 ]] -->
</body>
</html>