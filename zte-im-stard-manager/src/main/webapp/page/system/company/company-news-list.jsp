<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="iframe-html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>最近新闻</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />
<style type="text/css">
.c-company .news-add .btn-group {
	padding-bottom: 2px;
}

.c-company .news-add .top .g-tab-hd {
	padding: 8px 0 0;
}

#timeUl li {
	border-bottom: 1px solid #e7e8ea;
}

.g-drop .bd a:hover {
	background: #eee;
}

.g-drop .bd a {
	background: #f5f6fa;
}
</style>
<%
	String author = request.getSession().getAttribute("userid")
			.toString();
%>

<link rel="stylesheet" 
	href="${pageContext.request.contextPath}/js/kindeditor-4.1.10/themes/default/default.css" />
<link rel="stylesheet" 
	href="${pageContext.request.contextPath}/js/kindeditor-4.1.10/plugins/code/prettify.css" />
<script type="text/javascript" charset="utf-8" 
	src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/kindeditor.js"></script>
<script type="text/javascript" charset="utf-8" 
	src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" 
	src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/plugins/code/prettify.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Base64.js"></script>
<script type="text/javascript">
	KindEditor.ready(function(K) {
		window.editor1 = K.create('textarea[name="newsCon"]', {
			cssPath : '${pageContext.request.contextPath}/js/kindeditor-4.1.10/plugins/code/prettify.css',
			uploadJson : '${pageContext.request.contextPath}/js/kindeditor-4.1.10/jsp/upload_json.jsp',
			fileManagerJson : '${pageContext.request.contextPath}/js/kindeditor-4.1.10/jsp/file_manager_json.jsp',
			allowFileManager : true,
		});
		prettyPrint();
	});
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript">
	if (typeof (JSON) == 'undefined') {
		//如果浏览器不支持JSON，则载入json2.js
		$.getScript('${pageContext.request.contextPath}/js/json2.js');
	}

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

	var privileges = null;
	$(function() {
		// 加载角色权限
		$.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/getPrivileges.do',
			dataType : "json",
			success : function(data) {
				privileges = JSON.stringify(data);
			},
			complete : function() {
				if (privileges == null) {
					$(".publishNews").css('display', 'none');
				} else if (privileges != '\"all\"') {
					if (privileges.indexOf('publishNews') == -1) {
						$(".publishNews").css('display', 'none');
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});

		$("#datepicker").datepicker();

		//单选组事件
		$(":checkbox").bind("click", function() {
			if (this.checked) {
				$(this).siblings().each(function() {
					this.checked = false;
				});
			}
		});
	});

	function changeDisplay(id) {
		var display = $("#" + id).css("display");
		if (display == 'none') {
			$("#" + id).css("display", "block");
		} else {
			$("#" + id).css("display", "none");
		}
	}

	function showNewsSee() {
		$("#news-see-div").css("display", "block");
		$("#news-add-div").css("display", "none");
		$("#news-see-li").addClass("active");
		$("#news-add-li").removeClass("active");
	}

	function showNewsAdd() {
		$("#news-see-div").css("display", "none");
		$("#news-add-div").css("display", "block");
		$("#news-see-li").removeClass("active");
		$("#news-add-li").addClass("active");
	}

	function showPreviewDiv() {
		$("#news-see-div").css("display", "none");
		$("#news-add-div").css("display", "none");
		$("#previewDiv").css("display", "block");
		$("#prebtn").css("display", "block");
		$("#preback").css("display", "block");
		$("#preback2").css("display", "none");
		$("#news-see-li").removeClass("active");
		$("#news-add-li").removeClass("active");
	}
	
	function showPreviewDiv2() {
		$("#news-see-div").css("display", "none");
		$("#news-add-div").css("display", "none");
		$("#previewDiv").css("display", "block");
		$("#prebtn").css("display", "none");
		$("#preback").css("display", "none");
		$("#preback2").css("display", "back");
		$("#news-see-li").removeClass("active");
		$("#news-add-li").removeClass("active");
	}

	function goBackFromPre() {

		$("#news-see-div").css("display", "none");
		$("#news-add-div").css("display", "block");
		$("#news-see-li").removeClass("active");
		$("#news-add-li").addClass("active");
		$("#previewDiv").css("display", "none");
	}

	function showDiv(id, coverdiv) {
		//追加一个层，使背景变灰  
		var iWidth = $(window.parent).width();//浏览器当前窗口可视区域宽度  
		var iHeight = $(window.parent).height();//浏览器当前窗口可视区域高度   
		var divStyle = "position:absolute;left:0px;top:0px;width:100%;height:100%;filter:Alpha(Opacity=30);opacity:0.3;background-color:#000000;";
		$(window.parent.document).find("#" + coverdiv).attr("style", divStyle);

		var winNode = $(window.parent.document).find("#" + id);
		winNode.css("top", ((iHeight - winNode.height()) / 2));
		winNode.fadeIn("slow");
	}

	function hideCover(coverdiv) {
		//删除变灰的层  
		$(window.parent.document).find("#" + coverdiv).removeAttr("style");
	}

	function getNewsList() {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/getNewsList.do',
					data : {
						'type' : '',
						'title' : '',
						'days' : $("#days").val()
					},
					dataType : "json",
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						console.log(XMLHttpRequest);
						console.log(errorThrown);
						console.log(textStatus);
					},
					success : function(data) {
						var newsListHtml = "";
						if (data && data.item && data.item.length) {
							var len = data.item.length;
							for ( var i = 0; i < len; i++) {
								var item = data.item[i];
								var id = item.id;
								var title = item.title;
								var author = item.author;
								var type = item.type;
								if (type == '0') {
									type = "普通新闻";
								} else {
									type = "封面新闻";
								}
								var date = item.date;
								var d = new Date(date);
								d = d.format("yyyy-MM-dd");
								var flag = item.flag;
								if (flag == '0') {
									flag = "未发布";
								} else {
									flag = "已发布";
								}

								newsListHtml = newsListHtml
										+ "<tr><td class=\"link\"><a href=\"javascript:showContent4title('"
										+ id + "')\">" + title + "</a></td>"
										+ "<td>";
								if (privileges == '\"all\"'
										|| (privileges != null && privileges
												.indexOf('editNews') != -1)) {
									newsListHtml += "<a href=\"javascript:editNews('"
											+ id + "');\">编辑</a>";
								}

								if (privileges == '\"all\"'
										|| (privileges != null && privileges
												.indexOf('removeNews') != -1)) {
									newsListHtml += "<a href=\"javascript:delNews('"
											+ id
											+ "','" + title +  "');\" class=\"ml20\">删除</a>";
								}

								newsListHtml += "</td><td>" + author + "</td>"
										+ "<td>" + type + "</td><td>" + d
										+ "</td><td class=\"tr\">" + flag
										+ "</td></tr>";

							}
						}
						$('#newsList').html(newsListHtml);
					}
				});
	}
	
	function base2Str(unicode) {
		var str = '';
		for ( var i = 0, len = unicode.length; i < len; ++i) {
			str += String.fromCharCode(unicode[i]);
		}
		return str;
	}

	function editNews(id) {
		$.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/editNews.do',
			data : {
				'id' : id
			},
			dataType : "json",
			success : function(data) {
				var id = data.data.id;
				var title = data.data.title;
				var con = data.data.content;
				var type = data.data.type;
				$('#editNewsId').val(id);
				$('#newsTitle').val(title);
				editor1.html(base2Str(BASE64
						.decoder(con)));
				if (type == '0') {
					$('#newsType1').attr('checked', 'checked');
					$('#newsType2').removeAttr('checked');
				} else {
					$('#newsType2').attr('checked', 'checked');
					$('#newsType1').removeAttr('checked');
				}
				showNewsAdd();
			}
		});

	}

	function delNews(id, title) {
		$.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/delNews.do',
			data : {
				'news_id' : id,
				'news_title' : title
			},
			dataType : "json",
			success : function(data) {
				alert('删除成功');
				window.location.href = 'company-news-list.jsp';
			}
		});
	}

	function submitNews() {
		var id = $('#editNewsId').val();
		var title = $('#newsTitle').val();
		if (null == title || title == '') {
			alert("请输入标题!");
			return;
		}
		var newsType = "0";
		var obj = document.getElementsByName("newsType");//选择所有name="interest"的对象，返回数组      
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].checked) //取到对象数组后，我们来循环检测它是不是被选中  
				newsType = obj[i].value; //如果选中，将value添加到变量s中      
		}
		editor1.sync();
		var con = $('#newsCon').val();
		$.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/saveNews.do',
			data : {
				'news_id' : id,
				'news_title' : title,
				'news_type' : newsType,
				'news_con' : con
			},
			dataType : "json",
			success : function(data) {
				alert('发布新闻成功');
				window.location.href = 'company-news-list.jsp';
				showNewsSee();
			}
		});
	}

	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, //month
			"d+" : this.getDate(), //day
			"h+" : this.getHours(), //hour
			"m+" : this.getMinutes(), //minute
			"s+" : this.getSeconds(), //second
			"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter
			"S" : this.getMilliseconds()
		//millisecond
		}
		if (/(y+)/.test(format))
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(format))
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
		return format;
	}

	function showContent() {
		var id = $('#editNewsId').val();
		var title = $('#newsTitle').val();
		if (null == title || title == '') {
			alert("请输入标题!");
			return;
		}
		var newsType = "0";
		var obj = document.getElementsByName("newsType");//选择所有name="interest"的对象，返回数组      
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].checked) //取到对象数组后，我们来循环检测它是不是被选中  
				newsType = obj[i].value; //如果选中，将value添加到变量s中      
		}
		editor1.sync();
		var con = $('#newsCon').val();
		var d = new Date();
		d = d.format("yyyy-MM-dd");
		$('#pre_title').html("标题：" + title);
		$('#pre_date').html("日期：" + d);
		$('#pre_con').html(con);
		showPreviewDiv();
	}

	function showContent4title(id) {
		$.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/editNews.do',
			data : {
				'id' : id
			},
			dataType : "json",
			success : function(data) {
				var title = data.data.title;
				var con = data.data.content;
				var author = data.data.author;
				var date = data.data.date;
				var d = new Date(date);
				d = d.format("yyyy-MM-dd");
				$('#pre_title').html("标题：" + title);
				$('#pre_date').html("日期：" + d);
				$('#pre_con').html(base2Str(BASE64
						.decoder(con)));
				showPreviewDiv2();
			}
		});
	}

	function switchShow(id) {
		var ele = $("#" + id);
		if (ele.css("display") == 'none') {
			ele.css("display", "block");
		} else {
			ele.css("display", "none");
		}
	}

	function showNews(msg, days) {
		$("#dateLable").html(msg);
		$("#days").val(days);
		switchShow('timeUl');
		getNewsList();
	}
</script>
</head>
<body onload="getNewsList();">
	<!-- 企业管理主体部分 [[ -->
	<div class="c-company">
		<div class="inner" style="height: 80%">
			<div id="news-see-div" class="news-see">
				<div class="top rel">
					<ul class="g-tab-hd clearfix">
						<li id="news-see-li" class="active"><a
							href="javascript:showNewsSee();">最近新闻</a></li>
						<li id="news-add-li" class="publishNews"><a
							href="javascript:showNewsAdd();">发布新闻</a></li>
					</ul>
					<div class="options">
						<div class="g-drop">
							<div class="hd" style="width: 107px;">
								<label id="dateLable">显示所有日期</label> <i class="ico-arrow-down"
									onclick="switchShow('timeUl')"></i>
							</div>
							<input id="days" type="hidden" value="0" />
							<ul id="timeUl" class="bd" style="display: none; padding: 0;">
								<li><a href="javascript:showNews('显示所有日期',0);">显示所有日期</a></li>
								<li><a href="javascript:showNews('显示最近一周',7);">显示最近一周</a></li>
								<li><a href="javascript:showNews('显示最近1个月',30);">显示最近1个月</a></li>
								<li><a href="javascript:showNews('显示最近3个月',90);">显示最近3个月</a></li>
							</ul>
						</div>
					
						<form class="g-search g-input ml10">
							<span class="ico-search"></span> <i class="f"></i> <input
								type="text" /> <i class="e"></i>
						</form>
					</div>
				</div>

				<table id="newsList">
					<colgroup>
						<col></col>
						<col width="100"></col>
						<col width="140"></col>
						<col width="130"></col>
						<col width="140"></col>
						<col width="85"></col>
					</colgroup>
					<tr>
						<th align="left">标题</th>
						<th></th>
						<th>作者</th>
						<th>分类</th>
						<th>日期</th>
						<th>状态</th>
					</tr>
					<!-- &nbsp;为新闻为空时的占位符 如果不写的话在ie下这行是没有高度的 -->
				</table>
				<!--
				<div class="g-paging tc mt30">
					<a href="#" class="prev"></a> <a href="#">1</a> <a href="#">2</a> <a
						href="#">3</a> <a href="javascript:;" class="active">4</a> <a
						href="#">5</a> <a href="#" class="next"></a>
				</div>
				-->
			</div>

			<div id="news-add-div" class="news-add" style="display: none">
				<div class="top rel">
					<ul class="g-tab-hd clearfix">
						<li id="news-see-li"><a href="javascript:showNewsSee();">最近新闻</a></li>
						<li id="news-add-li" class="active"><a
							href="javascript:showNewsAdd();">发布新闻</a></li>
					</ul>
				</div>
				<form id="newsForm">
					<table class="news-pub">
						<colgroup>
							<col width="75"></col>
							<col></col>
							<col width="220"></col>
						</colgroup>
						<input type="hidden" id="editNewsId" value="" />
						<tr class="title">
							<td><label>标题：</label></td>
							<td>
								<div class="g-input">
									<i class="f"></i><input id="newsTitle" type="text" /><i
										class="e" style="color: red;">&nbsp;&nbsp;*</i>
								</div>
							</td>
							<td class="type tr"><span><input id="newsType1"
									name="newsType" type="checkbox" class="checkbox" value="0"
									checked=true /><label>普通新闻</label><input id="newsType2"
									name="newsType" type="checkbox" class="checkbox" value="1"
									style="margin-left: 25px;" /><label>封面新闻</label></span>
								</ul></td>
						</tr>
						<tr >
							<td class="vt"><label>内容：</label></td>
							<td colspan="2">
								<div class="editer">
									<textarea id="newsCon" name="newsCon" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;"></textarea>
								</div>
							</td>
						</tr>
					</table>
				</form>
				<div class="btn-group">
					<a href="javascript:submitNews();" class="btn-true">发布</a> <a
						href="company-news-list.jsp" class="btn-false ml35 ">取消</a><a
						href="javascript:showContent();" class="btn-false ml35">预览</a>
				</div>
			</div>
			<input type="hidden" id="author" name="author" value="<%=author%>" />

			<!-- 预览 -->
			<div id="previewDiv" class="news-preview" style="display: none">
				<div class="top" id="preback">
					<a href="javascript:goBackFromPre();">返回</a>
				</div>
				<div class="top" id="preback2">
					<a href="company-news-list.jsp">返回</a>
				</div>
				<div class="title clearfix">
					<span id="pre_title" class="l">标题：</span>

				</div>
				<div class="info">
					<span id="pre_author">作者：<%=author%></span><span id="pre_date"
						class="ml30">日期：</span>
				</div>
				<div class="con">
					<img id="pre_imgUrl" src="" class="l mr20" alt="" />

					<div id="pre_player" name="player"></div>
					<span id="pre_con" name="newsCon"> </span>
				</div>
				<div class="btn-group mt30" id="prebtn">
					<a href="javascript:submitNews();" class="btn-true">发布</a><a
						href="company-news-list.jsp" class="btn-false">取消</a>
				</div>
			</div>
		</div>


	</div>
	<!-- 企业管理主体部分 ]] -->

</body>
</html>