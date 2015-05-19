<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="iframe-html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>许可证管理</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />

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

	function showLicenseInfo() {
		$("#license-info-div").css("display", "block");
		$("#license-update-div").css("display", "none");
		$("#license-info-li").addClass("active");
		$("#license-update-li").removeClass("active");
	}

	function showLicenseUpdate() {
		$("#license-info-div").css("display", "none");
		$("#license-update-div").css("display", "block");
		$("#license-info-li").removeClass("active");
		$("#license-update-li").addClass("active");
	}
</script>
</head>
<body>
	<!-- 企业管理主体部分 [[ -->
	<div class="c-system">
		<div class="inner" style="height: 80%">
			<div class="c-top">
				<ul class="g-tab-hd clearfix">
					<li id="license-info-li" class="active"><a
						href="javascript:showLicenseInfo();">licensed信息</a></li>
					<li id="license-update-li"><a
						href="javascript:showLicenseUpdate();">更新License</a></li>
				</ul>
			</div>
			<div id="license-info-div">
				<table class="g-table t-license"
					style="margin-top: 25px; margin-bottom: 0px;">
					<colgroup>
						<col width="238" />
						<col />
					</colgroup>
					<tr>
						<td class="t">版本信息：</td>
						<td>正式版</td>
					</tr>
					<tr>
						<td class="t">授权总天数：</td>
						<td>无限制</td>
					</tr>
					<tr>
						<td class="t">当前剩余天数：</td>
						<td>无限制</td>
					</tr>
					<tr>
						<td class="t">最大用户数：</td>
						<td>100000</td>
					</tr>
					<tr>
						<td class="t">已用用户数：</td>
						<td>1000</td>
					</tr>
				</table>
			</div>

			<div id="license-update-div" style="display: none;">
				<table class="g-table t-license"
					style="margin-top: 25px; margin-bottom: 0px;">
					<colgroup>
						<col width="238" />
						<col />
					</colgroup>
					<tr>
						<td class="t">机器码：</td>
						<td></td>
					</tr>
					<tr>
						<td class="t">更新您的license：</td>
						<td><span class="l">选择文件</span>
							<div class="g-input l">
								<i class="f"></i><input type="text" /><i class="e"></i>
							</div> <span class="upload l"><a href="#">上传文件</a><input
								type="file" /></span></td>
					</tr>
				</table>
				<a href="javascript:;" class="btn-true mt30">保存</a>
			</div>

		</div>
	</div>
	<!-- 企业管理主体部分 ]] -->
</body>
</html>