<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/im.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<style type="text/css">
/*消除chrome浏览器黄色底*/
input:-webkit-autofill {
	-webkit-box-shadow: 0 0 0 1000px rgb(238, 239, 242) inset;
}

.p-login .panel .bd .forget-tip1 {
	position: absolute;
	padding: 10px 0 0;
	font-size: 16px;
	color: #50c944;
}

.p-login .panel .bd .forget-tip1 i {
	background-position: -246px -35px;
}

.p-login .panel .bd .name i {
	width: 21px;
	height: 30px;
	margin: 11px 15px 0 15px;
	background-position: 0 -66px;
}
</style>
<script>
	$(document).ready(function(){
	  	$("#name").focus();
	}); 
	function submitForm() {
		if ($("#name").val() == '') {
			alert("用户名不能为空");
			$("#name").focus();
			return;
		}
		if ($("#password").val() == '') {
			alert("密码不能为空");
			$("#password").focus();
			return;
		}
		document.getElementById("login").submit();
	}
	//回车事件
	document.onkeydown=function(event){
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if(e && e.keyCode==13){ // enter 键
			submitForm();
		}
	}; 
</script>
</head>
<body class="p-login">
	<img src="${pageContext.request.contextPath}/images/bg-login.jpg"
		alt="" class="bg" />
	<div class="panel">
		<div class="hd">
			<img src="${pageContext.request.contextPath}/images/logo-big.png"
				alt="" /> <span class="t">企业微信·管理平台</span>
		</div>
		<div class="bd">
			<form id="login"
				action="${pageContext.request.contextPath}/login.do"
				method="post" autocomplete="off">
				<div class="item name">
					<i></i><em></em><input type="text" name="name" id="name"
						placeholder="账号" />
				</div>
				<div class="item pw">
					<i></i><em></em><input type="password" name="password"
						id="password" placeholder="密码" />
				</div>
				<div id="forget-tip" class="forget-tip" style="display: none;">
					<i></i><span id="tip"></span>
				</div>
				<div id="forget-tip1" class="forget-tip1" style="display: none;">
					<i></i>密码已成功发送到邮箱
				</div>
				<div class="mt40">
					<a href="javascript:submitForm();" class="btn-true r">登录</a> <a
						href="${pageContext.request.contextPath}/page/system/forg_pass.jsp"
						class="forget">忘记密码？</a>
				</div>
			</form>
		</div>
	</div>
	<script>
	var page = '<%=request.getParameter("page")%>';
	if (page == "forgpass") {
		$("#forget-tip").css('display','none');
		$("#forget-tip1").css('display','block');
		$("#forget-tip1").fadeOut(3000);
	} else {
		$("#forget-tip1").css('display','none');
		if (page != "reset") {
			<%String error = (String) session.getAttribute("error");
			if (error != null) {%>
			$("#forget-tip").css('display','block');
			$("#tip").html('<%=error%>');
	<%}%>
		}
		}
	</script>
</body>

</html>