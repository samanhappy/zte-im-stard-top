<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
<title>通讯录参数</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
	<input type="hidden" id="webPath"
		value="${pageContext.request.contextPath}/">
    <div id="framePage">
        <div id="pageWrap">
            <div class="toolBar">
                <div class="fl newsBar">
                    <a href="sysem.jsp">系统参数</a>
                    <a href="javascript:;" class="selected">通讯录参数</a>
                    <a href="customAddressBook.jsp">通讯录自定义</a>
                </div>
            </div>
            <div class="">
            <form id="systemAddressBookForm" action="${pageContext.request.contextPath}/updateParam.do">
            	<input type="hidden" id="id" name="id" value="contact">
            	<table class="systemDataTable">
                    <tr>
                        <th>通讯录单位名称:</th>
                        <td>
                        	<div class="inputBox">
				                <i class="f"></i>
				                <input type="text" id="contactOrgName" name="contactOrgName">
				                <i class="e"></i>
				            </div>
						</td>
                        <td width="300">单位名称</td>
                    </tr>
                    <tr>
                        <th>是否同步LDAP:</th>
                        <td><label><input type="radio" name="syncLDAP" value="1">是</label>
                        <label><input type="radio" name="syncLDAP" value="0">否</label></td>
                        <td>从LDAP同步通讯录</td>
                    </tr>
                    <tr>
                        <th>LDAP服务器URL：</th>
                        <td>
                        	<div class="inputBox">
				                <i class="f"></i>
				                <input type="text" id="contactLdapUrl" name="contactLdapUrl">
				                <i class="e"></i>
				            </div>
						</td>
                        <td>例如：192.168.160.1：389</td>
                    </tr>
                    <tr>
                        <th>baseDN：</th>
                        <td>
                        	<div class="inputBox">
				                <i class="f"></i>
				                <input type="text" id="contactBaseDN" name="contactBaseDN">
				                <i class="e"></i>
				            </div>
						</td>
                        <td>例如：CN=Users,DC=nj,DC=zte,DC=com,DC=cn</td>
                    </tr>
                    <tr>
                        <th>成员属性访问保护：</th>
                        <td>
                        	<span id="protectedPropSpan">手机号码  、 固话 、 邮箱 </span>
                        	<input id="protectedPropNames" name="protectedPropNames" type="hidden" />
                        	<input id="protectedPropVals" name="protectedPropVals" type="hidden" />
                        	<a href="javascript:;" class="fr" id="memberPropertiesSetting">设置</a>
						</td>
                        <td>设置可更改属性</td>
                    </tr>
                    <tr>
                        <th>通讯录个人可设置的属性:</th>
                        <td>
                        	<span id="editablePropSpan">手机号码  、 固话 、 邮箱 </span>
                        	<input id="editablePropNames" name="editablePropNames" type="hidden" />
							<input id="editablePropVals" name="editablePropVals" type="hidden" />
                        	<a href="javascript:;" class="fr" id="addressBookSetting">设置</a>
						</td>
                        <td>设置受保护员工不可见属性</td>
                    </tr>
                </table>
                <input type="submit" value="保存" class="saveBtn updateContactParam">
                <input type="button" value="默认设置" class="resetBtn updateContactParam" id="defaultValue"/>
            </form>
            </div>
        </div>
    </div>
    <script>seajs.use("page/system_setting_addressBook");</script>
</body>
</html>

