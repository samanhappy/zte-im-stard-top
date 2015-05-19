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
<title>企业元素定制</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
	<div id="framePage">
		<div id="pageWrap">
			<div class="toolBar">
				<div class="fl newsBar">
					<a href="enterpriseInfo.jsp">企业基本信息</a> <a href="javascript:;" class="selected">企业元素定制</a>
				</div>
			</div>
			<div class="enterpriseCustomizationWrap">
				<form id="enterpriseCustomizationForm" action="${pageContext.request.contextPath}/updateTenantCustomization.do">
					<div class="enterpriseCustomizationBox">
						<div class="fl enterpriseCustomizationFormBox">
							<div class="enterpriseCustomizationForm">
								<div class="formTitle">服务端</div>
								<div class="formitm">
									<label class="lab">LOGO:</label>
									<div class="ipt">
										<span class="enterprisePicUpload"> <a href="javascript:;" class="fl uploadPic"><img
												id="serverPhoto" name="serverPhoto" src="${pageContext.request.contextPath}/common/images/upPicTxt1.png"
												height="43" width="43" /> <input id="serverLogoFile" name="photo" type="file" class="uploadInput" /></a> <input
											id="serverLogo" name="serverLogo" type="hidden" /></span>
										<p class="fl">大小：256px*256px 格式：PNG</p>
									</div>
								</div>
								<div class="formitm">
									<label class="lab">管理平台名称:</label>
									<div class="ipt">
										<div class="fl inputBox">
											<i class="f"></i> <input type="text" id="serverManagerName" name="serverManagerName" value=""> <i
												class="e"></i>
										</div>
									</div>
								</div>
								<div class="formitm">
									<label class="lab">版权信息:</label>
									<div class="ipt">
										<div class="fl inputBox">
											<i class="f"></i> <input type="text" id="serverCopyright" name="serverCopyright" value=""> <i
												class="e"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="fr modifyDemo">
							<h2>修改示意：</h2>
							<div class="demoItem">
								<p class="itemTitle">登录页面</p>
								<div class="itemLogo">LOGO</div>
								<div class="itemName">管理平台名称</div>
							</div>
							<div class="demoItem">
								<div class="itemTitle1">
									<div class="fl itemLogo1"></div>
									<div class="fl itemName1">管理平台名称</div>
								</div>
								<p class="itemTitle2">首页面</p>
								<div class="itemCopyRight">版权信息</div>
							</div>
						</div>
						<div class="fr modifyDemo" style="display: none;">
							<h2>修改示意：</h2>
							<div class="fl demoBox">
								<div class="demoItem">
									<p class="itemTitle">登录页面</p>
									<div class="itemLogo">LOGO</div>
								</div>
								<div class="demoItem">
									<div class="itemTitle3">
										<div class="fl itemLogo3"></div>
									</div>
									<p class="itemTitle4">有导航栏的页面</p>
								</div>
							</div>
							<div class="fr demoBox">
								<div class="demoItem">
									<p class="itemTitle">关于我们页面</p>
									<div class="itemLogo2">LOGO</div>
									<div class="itemAbout">关于我们</div>
								</div>
							</div>
						</div>
					</div>
					<div class="formitm" style="border-bottom: 0;">
						<label class="lab"><a href="javascript:;" class="saveBtn" id="dialogAddBtn">保存</a></label>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
		seajs.use("page/enterprise_enterpriseCustomization");
	</script>
</body>
</html>

