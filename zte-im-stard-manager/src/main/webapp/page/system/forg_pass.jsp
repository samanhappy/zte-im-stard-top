<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="home">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>忘记密码</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
	function submit() {
		if ($("#username").val() == '') {
			alert('请输入账号');
		} else if ($('#email').val() == '') {
			alert('请输入邮箱');
		} else {
			$('#page').val('forgpass');
			$('#myForm').submit();
			//window.location.href = "${pageContext.request.contextPath}/page/system/index.jsp";
		}
	}
	
	function reset() {
		$("#username").val('');
		$('#email').val('');
		$('#page').val('reset');
		$('#myForm').submit();
		//window.location.href = "${pageContext.request.contextPath}/page/system/index.jsp";
	}
</script>
<style type="text/css">
/*消除chrome浏览器黄色底*/
input:-webkit-autofill {
	-webkit-box-shadow: 0 0 0 1000px rgb(238, 239, 242) inset;
}
.p-login .panel .bd .name i {
	width: 21px;
	height: 30px;
	margin: 11px 15px 0 15px;
	background-position: 0 -66px;
}
</style>
</head>
<body class="p-login">
	<img src="${pageContext.request.contextPath}/images/bg-login.jpg"
		alt="" class="bg" />
	<div class="panel">
		<div class="hd">
			<img src="${pageContext.request.contextPath}/images/logo-big.png"
				alt="" /> <span class="t">企业微信·管理平台</span>
		</div>
		<div class="bd find-pw">
			<form id="myForm" action="${pageContext.request.contextPath}/page/system/index.jsp" method="post">
				<input id="page" name="page" type="hidden"/>
				<div class="item name">
					<i></i><em></em><input id="username" type="text"
						placeholder="请输入账号" />
				</div>
				<div class="item email">
					<i></i><em></em><input id="email" type="text" placeholder="请输入邮箱" />
				</div>
				<div class="mt40 clearfix">
					<a href="javascript:reset();" class="btn-false l">取&nbsp;&nbsp;消</a> <a
						href="javascript:submit();" class="btn-true r">确&nbsp;&nbsp;定</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>