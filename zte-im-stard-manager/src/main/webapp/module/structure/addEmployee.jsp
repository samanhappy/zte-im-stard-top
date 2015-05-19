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
<title>添加员工</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath" value="${pageContext.request.contextPath}/">
	<form id="addEmployeeForm" action="${pageContext.request.contextPath}/addUser.do" enctype="multipart/form-data">
		<div class="dialogBd">
			<div class="employeeBd">
				<ul class="employeeTab">
					<li><a href="javascript:;">员工信息</a></li>
					<li><a href="javascript:;">详细资料</a></li>
					<li><a href="javascript:;">自定义</a></li>
				</ul>
				<div class="employeeContent">
					<div class="formitm">
						<label class="lab">工 号:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="uid" name="uid" class="txt" autocomplete="off"> <i class="e"></i>
						</div>
						<span></span>
					</div>
					<div class="formitm">
						<label class="lab">姓 名:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="cn" name="cn" class="txt" autocomplete="off"> <i class="e"></i>
						</div>
						<span></span>
					</div>
					<div class="formitm">
						<label class="lab">部门:</label>
						<div class="ipt" id="openDept">
							<i class="f"></i> <input id="deptName" name="deptName" type="text" readonly="readonly" class="txt"> <input
								id="deptId" name="deptId" type="hidden" value=""> <input id="deptTId" type="hidden" value="">
							<!-- <input id="fullId" name="deptId" type="hidden"> -->
							<i class="e"></i> <a href="javascript:"><i class="iconfont">&#xe611;</i></a>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">性 别:</label>
						<div class="ipt">
							<i class="f"></i> <select id="sex" name="sex" style="width: 210px;">
								<option value="1">男</option>
								<option value="0">女</option>
							</select> <input id="sexDisplay" name="sexDisplay" type="hidden" value=""> <i class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">职 级:</label>
						<div class="ipt">
							<i class="f"></i> <select id="pos" name="pos" style="width: 210px;" onselect="alert(1)">
							</select> <input id="posDisplay" name="posDisplay" type="hidden" value=""> <i class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">出生日期:</label>
						<div class="ipt" id="openDept">
							<i class="f"></i> <input id="birthday" name="birthday" readonly="readonly" type="text" class="txt Wdate" /> <i
								class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">手 机:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="mobile" name="mobile" class="txt" autocomplete="off"> <i
								class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">登录账号:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="l" name="l" class="txt" autocomplete="off"> <i class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">邮 箱:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="mail" name="mail" class="txt" autocomplete="off"> <i class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">密 码:</label>
						<div class="ipt">
							<i class="f"></i>
							<!-- 假字段防止chrome自动填充 -->
							<input style="display: none" type="text" name="fakeusernameremembered" /> <input style="display: none"
								type="password" name="fakepasswordremembered" /> <input type="password" id="password" name="password"
								class="txt" autocomplete="off"> <i class="e"></i>
						</div>
					</div>
				</div>
				<div class="employeeContent" style="display: none;">
					<div class="formitm">
						<label class="lab">头 像:</label>
						<div class="picUpBox">
							<div class="imPicUpload">
								<span id="imPicBox"><img id="photoImg" name="photoImg"
									src="${pageContext.request.contextPath}/common/images/upPicTxt.jpg" height="43" width="43" /></span> <input
									id="photo" name="photo" type="file" class="uploadInput" /> <input id="photoUrl" name="photoUrl" type="hidden" />
							</div>
							<p>
								格式：JPEG、PNG、GIF<br />大小：不超过1M
							</p>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">微 信:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="weixin" name="weixin" class="txt" autocomplete="off"> <i
								class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">个性签名:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="photoSign" name="photoSign" class="txt" autocomplete="off"> <i
								class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">微 博:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="weibo" name="weibo" class="txt" autocomplete="off"> <i class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">爱 好:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="hobby" name="hobby" class="txt" autocomplete="off"> <i class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">家庭电话:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="hometelephone" name="hometelephone" class="txt" autocomplete="off">
							<i class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">Q Q:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="qq" name="qq" class="txt" autocomplete="off"> <i class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">传真电话:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="fax" name="fax" class="txt" autocomplete="off"> <i class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">地 址:</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="address" name="address" class="txt" autocomplete="off"> <i
								class="e"></i>
						</div>
					</div>
					<div class="formitm">
						<label class="lab">备注：</label>
						<div class="ipt">
							<i class="f"></i> <input type="text" id="info" name="info" class="txt" autocomplete="off"> <i class="e"></i>
						</div>
					</div>
				</div>
				<div id="userDefineParam" class="employeeContent" style="display: none;"></div>
			</div>
		</div>
		<div class="dialogBtns">
			<a href="javascript:;" class="add" id="dialogAddBtn">添加</a> <a href="javascript:;" class="cancel"
				id="dialogCancelBtn">取消</a>
		</div>
	</form>
	<script>
		seajs.use("page/structure_addEmployee");
	</script>
</body>
</html>

