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
<title>数据导入</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
	<form id="importUserForm" action="${pageContext.request.contextPath}/importUser.do">
		<div class="dialogBd">
			<div style="overflow: hidden;">
				<div class="fl upflieBox">
					<p>
						选择数据源： <span id="imPicBox" class="imExcelUpload"><a href="javascript:;" class="upFileBtn"
								title="上传文件">上传文件</a> <input id="photo" name="photo" type="file" class="uploadInput" /></span>
					</p>
					<ul id="uploadTip">
						<li><span>步骤：</span>1、模板下载后填写</li>
						<li><span>&nbsp;</span>2、选择填写完成的模板上传</li>
						<li><span>&nbsp;</span>3、确认提交</li>
					</ul>
					<input id="fileUrl" name="fileUrl" type="hidden" />
				</div>
				<div id="templateTd" class="fr downTplBox">
					<p>
						<a href="${pageContext.request.contextPath}/data/user template.xlsx" class="downTplBtn" title="下载模板">下载模板</a>
					</p>
					<p style="margin-top: 20px;">
						<a href="${pageContext.request.contextPath}/data/user template.xlsx" title="下载模板"><img
							src="${pageContext.request.contextPath}/common/images/tplIco.png"></a>
					</p>
				</div>
			</div>
			<p id="uploadedFile" class="uploadedFile" style="display: none;">
				<span id="fileSpan"></span>
			</p>
		</div>
		<div class="dialogBtns">
			<a href="javascript:;" class="add" id="dialogAddBtn">导入</a>
			<a href="javascript:;" class="cancel" id="dialogCancelBtn">取消</a>
		</div>
	</form>
	<script>
		seajs.use("page/structure_importEmployee");
	</script>
</body>
</html>

