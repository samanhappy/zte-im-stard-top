<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>企业信息</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/common/style/reset.css"
	type="text/css" media="screen" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/common/style/page.css"
	type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js"
	type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath"
		value="${pageContext.request.contextPath}/">
	<div id="framePage">
		<div id="pageWrap">
			<div class="toolBar">
				<div class="fl newsBar">
					<a href="javascript:;" class="selected">企业基本信息</a> <a
						href="enterpriseCustomization.jsp">企业元素定制</a>
				</div>
			</div>
			<div class="enterpriseInfoWrap">
				<form id="enterpriseInfoForm"
					action="${pageContext.request.contextPath}/updateTenant.do">
					<input type="hidden" id="id" name="id">
					<div class="formitm">
						<label class="lab">企业号：</label>
						<div id="platformIdDiv" class="ipt"></div>
						<input type="hidden" id="platformId" name="platformId">
					</div>
					<div class="formitm">
						<label class="lab">企业名称：</label>
						<div class="ipt">
							<div class="fl inputBox">
								<i class="f"></i> <input id="name" name="name" type="text">
								<i class="e"></i>
							</div>
							<span class="dot">*</span>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">企业联系人：</label>
						<div class="ipt">
							<div class="fl inputBox">
								<i class="f"></i> <input type="text" id="linkman" name="linkman">
								<i class="e"></i>
							</div>
							<span class="dot">*</span>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">联系电话：</label>
						<div class="ipt">
							<div class="fl inputBox">
								<i class="f"></i> <input type="text" id="mobile" name="mobile">
								<i class="e"></i>
							</div>
							<span class="dot">*</span>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">电子邮箱：</label>
						<div class="ipt">
							<div class="fl inputBox">
								<i class="f"></i> <input type="text" id="mail" name="mail">
								<i class="e"></i>
							</div>
							<span class="dot">*</span> <em class="fr">找回密码使用</em>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">固定电话：</label>
						<div class="ipt">
							<div class="fl inputBox">
								<i class="f"></i> <input type="text" id="tel" name="tel">
								<i class="e"></i>
							</div>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">企业规模：</label>
						<div class="ipt">
							<div class="fl inputBox">
								<i class="f"></i> <select id="guimo" name="guimo" class="guimo">
									<option value="1-50">1-50</option>
									<option value="50-100">50-100</option>
									<option value="100-500">100-500</option>
									<option value="500-2000">500-2000</option>
									<option value="2000-10000">2000-10000</option>
									<option value="10000-100000">10000-100000</option>
								</select> <i class="e"></i>
							</div>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">所在地区：</label>
						<div class="ipt">
							<div class="fl inputBox">
								<i class="f"></i>
								<div id="city">
									<select class="prov" id="prov" name="prov"><option
											value="江苏" class="province">江苏</option></select> <select class="city"
										id="city" name="city" disabled="disabled"><option
											value="南京" class="cityOpt">南京</option></select>
								</div>
								<i class="e"></i>
							</div>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">通讯地址：</label>
						<div class="ipt">
							<div class="fl inputBox">
								<i class="f"></i> <input type="text" id="address" name="address">
								<i class="e"></i>
							</div>
						</div>
					</div>
					<div><input type="submit" value="保存" class="saveBtn"></div>
				</form>
			</div>
		</div>
	</div>
	<script>
		seajs.use("page/enterprise_enterpriseInfo");
	</script>
</body>
</html>

