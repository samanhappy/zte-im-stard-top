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
<title>license信息</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/style/page.css" type="text/css" media="screen" />
<script src="${pageContext.request.contextPath}/common/js/sea.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/js/seajs-text.js" type="text/javascript"></script>
</head>
<body>
    <div id="framePage">
        <div id="pageWrap">
            <div class="toolBar">
                <div class="fl newsBar">
                    <a href="javascript:;" class="selected" id="infoBtn">license信息</a>
                    <a href="javascript:;" id="updateBtn">更新license</a>
                </div>
            </div>
            <div class="licenseInfo">
                <table class="dataTable">
                    <tr>
                        <th>版本信息：</th>
                        <td>正式版</td>
                    </tr>
                    <tr>
                        <th>授权总天数：</th>
                        <td>无限制</td>
                    </tr>
                    <tr>
                        <th>当前剩余天数：</th>
                        <td>无限制</td>
                    </tr>
                    <tr>
                        <th>最大用户数：</th>
                        <td>100000</td>
                    </tr>
                    <tr>
                        <th>已用用户数：</th>
                        <td>1000</td>
                    </tr>
                </table>
            </div>
            <div class="licenseUpdate" style="display:none;">
                <form id="licenseUpdate" action="">
                    <table class="dataTable">
                        <tr>
                            <th>机器码：</th>
                            <td>22</td>
                        </tr>
                        <tr>
                            <th>更新您的license：</th>
                            <td>
                                <span class="fl">选择文件</span>
                                <div class="inputBox fl">
                                    <i class="f"></i><input type="text"><i class="e"></i>
                                </div>
                                <span class="upload fl"><a href="javascript:;">上传文件</a><input type="file"></span>
                            </td>
                        </tr>
                    </table>
                    <input type="submit" value="保存" class="saveBtn">
                </form>
            </div>
        </div>
    </div>
    <script>seajs.use("page/system_license_info");</script>
</body>
</html>

