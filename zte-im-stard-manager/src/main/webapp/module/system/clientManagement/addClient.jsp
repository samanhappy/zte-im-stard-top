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
<title>添加客户端</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
	<form id="addClientForm" action="${pageContext.request.contextPath}/addClient.do">
		<div class="dialogBd">
			<div class="clientFormWrap addFieldBd">
				<div class="fl">
					<ul>
						<li><span class="lab">发布类型:</span>
							<div class="ipt">
								<div class="fl inputBox">
									<i class="f"></i> <select id="ctype" name="ctype">
										<option value="Android应用">Android应用</option>
										<option value="IOS应用">IOS应用</option>
									</select> <i class="e"></i>
								</div>
							</div></li>
						<li class='android'><span class="lab">应用文件:</span>
							<div class="ipt">
								<div class="fl inputBox">
									<i class="f"></i> <input type="text" id="capk" name="capk" readonly> <input id="capkUrl" name="capkUrl"
										type="hidden" /> <i class="e"></i>
								</div>
								<span class="errorTipts"></span>
								<div class="upbox">
									<div class="clientPicUpload"><a id="browse" href="javascript:;">浏览<input id="photo" name="photo"
											type="file" class="uploadInput" /></a></div> <a id="upload" href="javascript:;">上传</a> <img id="addUploading"
										height="32" width="32" src="${pageContext.request.contextPath}/images/loading.gif" style="display: none;" />
								</div>
							</div></li>
						<li class='ios'><span class="lab">PList地址:</span>
							<div class="ipt">
								<div class="fl inputBox">
									<i class="f"></i> <input type="text" id="plist" name="plist"> <i class="e"></i>
								</div>
								<span class="errorTipts"></span>
							</div></li>
						<li><span class="lab">应用名称:</span>
							<div class="ipt">
								<div class="fl inputBox">
									<i class="f"></i> <input type="text" id="cname" name="cname"> <i class="e"></i>
								</div>
								<span class="errorTipts"></span>
							</div></li>
						<li class='android'><span class="lab">图标:</span>
							<div class="ipt">
								<a href="javascript:;" class="uploadPic" title="上传图标"> <input id="ciconUrl" name="ciconUrl" type="hidden" />
									<img id="cicon" name="cicon" src="" height="43" width="43" /></a>
							</div></li>
						<li><span class="lab">版本:</span>
							<div class="ipt">
								<div class="fl inputBox">
									<i class="f"></i> <input type="text" id="version" name="version"> <input type="hidden" id="versionCode"
										name="versionCode" readonly> <i class="e"></i>
								</div>
								<span class="errorTipts"></span>
							</div></li>
					</ul>
				</div>
				<div class="fr">
					<ul>
						<li><span class="lab">操作系统:</span>
							<div class="ipt">
								<div class="fl inputBox">
									<i class="f"></i> <input type="text" id="cos" name="cos" readonly> <i class="e"></i>
								</div>
							</div></li>
						<li>是否立即更新当前版本： <label><input type="radio" id="cyActive" name="cisActive" value="on" checked>是</label>
							<label><input type="radio" id="cnActive" name="cisActive" value="off">否</label></li>
						<li>是否强制更新： <label><input type="radio" name="forceUpdate" value="1">是</label> <label><input
								type="radio" name="forceUpdate" value="0" checked>否</label></li>
						<li><span class="lab">更新日志:</span>
							<div class="ipt">
								<textarea id="cupdateLog" name="cupdateLog" placeholder="可手动输入"></textarea>
							</div></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="dialogBtns">
			<a href="javascript:;" class="cancel" id="dialogAddBtn">确定</a> <a href="javascript:;" class="cancel"
				id="dialogCancelBtn">取消</a>
		</div>
	</form>
	<script>
		seajs.use("page/system_clientManagement_addClient");
	</script>
</body>
</html>

