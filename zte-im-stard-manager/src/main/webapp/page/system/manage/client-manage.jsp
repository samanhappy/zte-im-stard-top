<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="iframe-html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户端管理</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />
<style type="text/css">
.odd {
	background: #fcfcfc;
}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
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

	if (typeof (JSON) == 'undefined') {
		//如果浏览器不支持JSON，则载入json2.js
		$.getScript('${pageContext.request.contextPath}/js/json2.js');
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

	function showCover(coverdiv) {
		//追加一个层，使背景变灰  
		var iWidth = $(window.parent).width();//浏览器当前窗口可视区域宽度  
		var iHeight = $(window.parent).height();//浏览器当前窗口可视区域高度   
		var divStyle = "position:absolute;left:0px;top:0px;width:100%;height:100%;filter:Alpha(Opacity=30);opacity:0.3;background-color:#000000;";
		$(window.parent.document).find("#" + coverdiv).attr("style", divStyle);
	}

	function hideCover(coverdiv) {
		//删除变灰的层  
		$(window.parent.document).find("#" + coverdiv).removeAttr("style");
	}
	function load() {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/clientList.do',
					data : {
						page : 1
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							var html = '';
							if (data.item && data.item.length > 0) {
								for ( var i = 0; i < data.item.length; i++) {
									var client = data.item[i];
									if (i % 2 == 1) {
										html += '<tr class="odd">';
									} else {
										html += '<tr>';
									}
									html += "<td><input name='checkId' type='checkbox' class='checkbox' value='"
											+ JSON.stringify(client)
											+ "' /></td>";
									html += "<td><img width='46px' height='46px' src='" + client.iconUrl + "'/></td>";
									html += '<td>' + client.name + '</td>';
									html += '<td>' + client.os + '</td>';
									if (client.version) {
										html += '<td>' + client.version
												+ '</td>';
									} else {
										html += '<td></td>';
									}
									if (client.isActive == 'on') {
										html += '<td>激活</td>';
									} else {
										html += '<td>暂停</td>';
									}
									html += '<td class="manage">';
									if (client.isActive == 'on') {
										html += '<a href="javascript:setActive(\''
												+ client.id
												+ '\',\'off\''
												+ ')">暂停</a>';
									} else {
										html += '<a href="javascript:setActive(\''
												+ client.id
												+ '\',\'on\''
												+ ')">激活</a>';
									}

									html += '<a href="' + client.apkUrl + '">下载</a></td>';
								}
							}
							$("#dataBody").html(html);
						} else if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert("查询失败");
						}
					}
				});
	}

	function setActive(id, isActive) {
		$
				.ajax({
					type : "POST",
					url : '${pageContext.request.contextPath}/setClientActive.do',
					data : {
						id : id,
						isActive : isActive
					},
					dataType : "json",
					success : function(data) {
						if (data.res.reCode && data.res.reCode == '1') {
							load();
						} else if (data.res.reCode && data.res.reCode == '2') {
							parent.location.href = '${pageContext.request.contextPath}/';
						} else {
							alert("更新失败");
						}
					}
				});
	}

	function checkAll() {
		if ($("#checkAll").is(':checked')) {
			$("#dataBody input[type='checkbox']").each(function() {
				this.checked = true;
			});
		} else {
			$("#dataBody input[type='checkbox']").each(function() {
				this.checked = false;
			});
		}
	}

	function removeClient() {
		var checkedEle = $("input[name='checkId']:checked");
		if (checkedEle.length == 0) {
			alert("必须选择一个客户端");
		} else {
			var removeids = "";
			var removenames = "";
			for ( var i = 0; i < checkedEle.length; i++) {
				var client = JSON.parse(checkedEle[i].value);
				removeids += client.id + ',';
				removenames += client.name + ',';
			}
			removeids = removeids.substring(0, removeids.length - 1);
			removenames = removenames.substring(0, removenames.length - 1);
			parent.setClientRemoveValues(removeids, removenames);
			showDiv('removeClientTip', 'coverdiv1');
		}
	}

	function editClient() {
		var checkedEle = $("input[name='checkId']:checked");
		if (checkedEle.length > 1) {
			alert("只能选择一个客户端");
			return;
		}
		if (checkedEle.length == 0) {
			alert("必须选择一个客户端");
			return;
		} else {
			var client = JSON.parse(checkedEle[0].value);
			$
					.ajax({
						type : "POST",
						url : '${pageContext.request.contextPath}/toeditClient.do',
						data : {
							cid : client.id

						},
						dataType : "json",
						success : function(data) {
							showDiv('editClient', 'coverdiv1');
							$(window.parent.$("div#editClient #cid").val(
									data.cid));
							$(window.parent.$("div#editClient #ctype").val(
									data.ctype));
							$(window.parent.$("div#editClient #capkUrl").val(
									data.capkUrl));
							$(window.parent.$("div#editClient #ciconUrl").val(
									data.ciconUrl));
							$(window.parent.$("div#editClient #cicon").attr(
									"src", data.ciconUrl));
							$(window.parent.$("div#editClient #cname").val(
									data.cname));
							$(window.parent.$("div#editClient #cos").val(
									data.cos));
							$(window.parent.$("div#editClient #capk").val(
									data.capk));
							$(window.parent.$("div#editClient #capkFile").val(
									''));
							$(
									window.parent
											.$("#editClient input[name='cisActive']"))
									.each(function() {
										if (data.cisActive == $(this).val()) {
											this.checked = true;
										} else {
											this.checked = false;
										}
									});
							$(window.parent.$("div#editClient #cupdateLog")
									.val(data.cupdateLog));
							$(window.parent.$("div#editClient #version").val(
									data.version));
						}
					});

		}
	}
</script>
</head>
<body onload="load()">

	<!-- 企业管理主体部分 [[ -->
	<div class="c-system">
		<div class="inner" style="height: 80%">
			<div class="c-header c-header-gray">
				<ul>
					<li class="jia"><a href="javascript:parent.showAddClient();">新增<i></i></a></li>
					<li class="modify"><a href="javascript:editClient();">修改<i></i></a></li>
					<li class="del"><a href="javascript:removeClient();">删除<i></i></a></li>
				</ul>
			</div>
			<table class="g-table tc t-client-manage">
				<colgroup>
					<col width="40" />
					<col width="100" />
					<col width="120" />
					<col width="120" />
					<col width="120" />
					<col width="120" />
					<col />
				</colgroup>
				<tr>
					<th><input id="checkAll" type="checkbox" class="checkbox"
						onclick="checkAll();" /></th>
					<th>客户端标识</th>
					<th>客户端名称</th>
					<th>操作系统</th>
					<th>最新版本</th>
					<th>最新状态</th>
					<th>操作</th>
				</tr>
				<tbody id="dataBody"></tbody>
			</table>
		</div>
	</div>
	<!-- 企业管理主体部分 ]] -->
</body>
</html>