<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="iframe-html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业信息</title>
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

	function editPass() {
		var opass = $("div#tpass #opass").val();
		var pass = $("div#tpass #pass").val();
		var rpass = $("div#tpass #rpass").val();
		if ($("div#tpass #opass").val() == "") {
			alert('原始密码不能为空');
			return;
		}
		if ($("div#tpass #pass").val() == "") {
			alert('密码不能为空');
			return;
		}
		if (pass != rpass) {
			alert('两次密码不一致');
			return;
		}
		if (pass == opass) {
			alert('修改后与修改前的密码一样');
			return;
		}
		$.ajax({
			type : "POST",
			url : '${pageContext.request.contextPath}/editPass.do',
			data : {
				opass : $("div#tpass #opass").val(),
				pass : $("div#tpass #pass").val(),
				rpass : $("div#tpass #rpass").val()
			},
			dataType : "json",
			success : function(data) {
				if (data.res.reCode && data.res.reCode == '2') {
					alert("原始密码错误，请重新输入");
				} else {
					location.reload();
				}
			}
		});
	}
</script>
</head>
<body>
	<!-- 企业管理主体部分 [[ -->
	<div class="c-company">
		<div class="inner" id="tpass" style="height: 80%">
			<ul class="edit-pw" style="margin: 0;">
				<table>
					<colgroup>
						<col width="150"></col>
						<col></col>
					</colgroup>
					<tr>
						<td><label>原始密码:</label></td>
						<td>
							<div class="g-input">
								<i class="f"></i><input id="opass" type="password"
									class="g-input2" placeholder="请输入原始密码"><i class="e"></i>
							</div>
						</td>
						<tr>
							<td><label>新的密码：</label></td>
							<td>
								<div class="g-input">
									<i class="f"></i><input id="pass" type="password"
										class="g-input2" placeholder="输入新密码"><i class="e"></i>
								</div>
							</td>
							<tr>
								<td><label>确认新密码:</label></td>
								<td>
									<div class="g-input">
										<i class="f"></i><input id="rpass" type="password"
											class="g-input2" placeholder="确认密码"><i class="e"></i>
									</div>
								</td>
							</tr>
				</table>
			</ul>
			<a href="javascript:editPass();" class="btn-true mt30">保存</a>
		</div>
	</div>
	<!-- 企业管理主体部分 ]] -->
	</div>
</body>
</html>