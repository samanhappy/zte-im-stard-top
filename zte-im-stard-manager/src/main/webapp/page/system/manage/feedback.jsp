<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="iframe-html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息反馈</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/page.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Base64.js"></script>
<style type="text/css">
.odd {
	background: #fcfcfc;
}

.c-company .inner,.c-system .inner {
	height: 85%;
}
</style>
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

	var type = '';

	function clickLi(index) {
		for (i = 1; i <= 3; i++) {
			if (index == i) {
				$("#li" + i).addClass("active");
			} else {
				$("#li" + i).removeClass("active");
			}
		}

		if (index == 1) {
			type = '';
		} else if (index == 2) {
			type = 'system';
		} else if (index == 3) {
			type = 'user';
		}

		loadFeedback();
	}

	function base2Str(unicode) {
		var str = '';
		for ( var i = 0, len = unicode.length; i < len; ++i) {
			str += String.fromCharCode(unicode[i]);
		}
		return str;
	}

	function showDesc(id) {

		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/getFeedBack.do',
					data : {
						id : id
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							parent.showFeedBack(base2Str(BASE64
									.decoder(data.data.content)));
						} else if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						}
					},
					complete : function() {
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("数据操作出错");
					}
				});
	}

	var pSize = 10;
	function loadFeedback(cPage) {
		var requestUrl = '${pageContext.request.contextPath}/listFeedback.do?type='
				+ type + "&key=" + $("#keyword").val();
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
						if (data.res.reCode && data.res.reCode == '1') {
							var html = '';
							var pageHtml = '';
							if (data.feedbacks && data.feedbacks.feedbackList
									&& data.feedbacks.feedbackList.length > 0) {
								for ( var i = 0; i < data.feedbacks.feedbackList.length; i++) {
									var fb = data.feedbacks.feedbackList[i];
									if (i % 2 == 1) {
										html += '<tr class="odd">';
									} else {
										html += '<tr>';
									}
									html += '<td>' + fb.name + '</td>';
									html += '<td>' + fb.mobile + '</td>';
									html += '<td>' + fb.device + '</td>';
									html += '<td>' + fb.os + '</td>';
									html += '<td>' + fb.resolution + '</td>';
									html += "<td nowrap style='overflow:hidden;color:#2c5492;cursor:pointer' id='"
											+ fb.id
											+ "' onclick=showDesc('"
											+ fb.id
											+ "')>"
											+ base2Str(BASE64
													.decoder(fb.content))
											+ '</td>';
									html += '<td>' + fb.time + '</td></tr>';
								}

								pageHtml += "<a href='javascript:loadFeedback("
										+ data.feedbacks.previous
										+ ")' class='prev'></a>";
								var startI = 1;
								if (data.feedbacks.totalPage > 10) {
									if (data.feedbacks.currentPage > 5) {
										startI = data.feedbacks.currentPage - 5;
									}
									if (data.feedbacks.totalPage
											- data.feedbacks.currentPage < 5) {
										startI -= 4 - (data.feedbacks.totalPage - data.feedbacks.currentPage);
									}
								}

								for ( var i = startI, j = 1; i <= data.feedbacks.totalPage
										&& j <= 10; i++, j++) {
									pageHtml += "<a href='javascript:loadFeedback("
											+ i + ")'";
									if (i == data.feedbacks.currentPage) {
										pageHtml += " class='active'";
									}
									pageHtml += (">" + i + "</a>");
								}
								pageHtml += "<a href='javascript:loadFeedback("
										+ (data.feedbacks.next <= data.feedbacks.totalPage ? data.feedbacks.next
												: data.feedbacks.totalPage)
										+ ")'  class='next'></a>";
							}
							$("#pageDiv").html(pageHtml);
							$("#dataBody").html(html);
						} else if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						}
					},
					complete : function() {
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("数据操作出错");
					}
				});
	}
</script>
</head>
<body>
	<!-- 企业管理主体部分 [[ -->
	<div class="c-system">
		<div class="inner" style="overflow-y: hidden;">
			<div class="c-top">
				<ul class="g-tab-hd clearfix">
					<li id="li1" class="active"><a href="javascript:clickLi(1);">全部</a></li>
					<li id="li2"><a href="javascript:clickLi(2);">系统异常</a></li>
					<li id="li3"><a href="javascript:clickLi(3);">用户反馈</a></li>
				</ul>
				<form class="g-search g-input">
					<span class="ico-search"></span> <i class="f"></i> <input
						id="keyword" name="keyword" type="text" /> <i class="e"></i>
				</form>
			</div>
			<table class="g-table tc">
				<colgroup>
					<col width="100" />
					<col width="155" />
					<col width="100" />
					<col width="100" />
					<col width="100" />
					<col />
					<col width="180" />
				</colgroup>
				<tr>
					<th>姓名</th>
					<th>联系方式</th>
					<th>终端型号</th>
					<th>操作系统</th>
					<th>分辨率</th>
					<th>反馈内容</th>
					<th>反馈时间</th>
				</tr>
				<tbody id="dataBody">
				</tbody>
			</table>
		</div>
		<!-- 分页 -->
		<div id="pageDiv" class="g-paging tc mtb30"
			style="position: fixed; left: 185px; bottom: 100px; width: 100%; height: 54px; padding-left: 0 !important; padding-left: 185px;"></div>
	</div>
	<!-- 企业管理主体部分 ]] -->
	<script type="text/javascript">
		loadFeedback();
	</script>
</body>
</html>