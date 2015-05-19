<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业微信工作台</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/easyui1.3/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/easyui1.3/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
<script type="text/javascript">
	//新增Tab
	function openTab(text, url) {
		if ($("#tabs").tabs('exists', text)) {
			$("#tabs").tabs('select', text);
		} else {
			var content = "<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="
					+ url + "></iframe>";
			$("#tabs").tabs('add', {
				title : text,
				closable : true,
				content : content
			});
		}
	}

	// 实例化树菜单
	$(function() {
		//var treeData=tree_data1.json;
		var urltree = '${pageContext.request.contextPath}/page/system/menu/tree_data1.json';
		$("#tree").tree(
				{
					//data:treeData,
					url : urltree,
					lines : true,
					method : 'get',
					animate : true,
					dnd : true,
					onContextMenu : function(e, node) {
						e.preventDefault();
						$(this).tree('select', node.target);
						$('#mm').menu('show', {
							left : e.pageX,
							top : e.pageY
						});
					},
					formatter : function(node) {
						var s = node.text;
						if (node.children) {
							s += '&nbsp;<span style=\'color:blue\'>('
									+ node.children.length + ')</span>';
						}
						return s;
					},
					onClick : function(node) {
						if (node.attributes) {
							openTab(node.text, node.attributes.url);
						}
					}
				});
	});
</script>
</head>
<body class="easyui-layout" fit="true">
	<div data-options="region:'north'"
		style="height:15%; 
		background-image: url(${pageContext.request.contextPath}/images/system/u2.png)">

	</div>
	<div
		data-options="region:'west',split:false,border:false,iconCls:'icon-man'"
		title="系统菜单" style="width: 20%;">
		<div class="easyui-accordion" data-options="fit:true,border:false">
			<div title="组织架构">
				<div class="easyui-panel">
					<ul id="tree">
					</ul>
				</div>

			</div>
			<div title="企业管理">
				<ul>
					<li><a href="#"
						onclick="javascript:openTab('企业新闻','${pageContext.request.contextPath}/user.do')">企业新闻</a></li>
					<li><a href="#"
						onclick="javascript:openTab('企业信息','${pageContext.request.contextPath}/user.do')">企业信息</a></li>
					<li><a href="#"
						onclick="javascript:openTab('配置管理','${pageContext.request.contextPath}/user.do')">配置管理</a></li>
					<li><a href="#"
						onclick="javascript:openTab('个人密码修改','${pageContext.request.contextPath}/user.do')">个人密码修改</a></li>
				</ul>
			</div>
			<div title="系统管理">
				<ul>
					<li><a href="#"
						onclick="javascript:openTab('参数设置','${pageContext.request.contextPath}/user.do')">参数设置</a></li>
					<li><a href="#"
						onclick="javascript:openTab('许可证管理','${pageContext.request.contextPath}/user.do')">许可证管理</a></li>
					<li><a href="#"
						onclick="javascript:openTab('系统日志','${pageContext.request.contextPath}/user.do')">系统日志</a></li>
					<li><a href="#"
						onclick="javascript:openTab('客户端管理','${pageContext.request.contextPath}/user.do')">客户端管理</a></li>
					<li><a href="#"
						onclick="javascript:openTab('信息反馈','${pageContext.request.contextPath}/user.do')">信息反馈</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center',title:'欢迎使用企业微信工作台'"
		style="width: 80%;">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
			<div title="首页"></div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div onclick="append()" data-options="iconCls:'icon-add'">Append</div>
		<div onclick="removeit()" data-options="iconCls:'icon-remove'">Remove</div>
		<div class="menu-sep"></div>
		<div onclick="expand()">Expand</div>
		<div onclick="collapse()">Collapse</div>
	</div>
	<script type="text/javascript">
		function append() {
			var t = $('#tree');
			var node = t.tree('getSelected');
			t.tree('append', {
				parent : (node ? node.target : null),
				data : [ {
					text : 'new item1'
				} ]
			});
		}
		function removeit() {
			var node = $('#tree').tree('getSelected');
			$('#tree').tree('remove', node.target);
		}
		function collapse() {
			var node = $('#tree').tree('getSelected');
			$('#tree').tree('collapse', node.target);
		}
		function expand() {
			var node = $('#tree').tree('getSelected');
			$('#tree').tree('expand', node.target);
		}
		/* js触发添加tab
		与
		<a href="#" onclick="javascript:openTab('baidu','http://www.baidu.com')">百度</a> 联合使用
		// 新增Tab
		function openTab(text,url){
			if($("#tabs").tabs('exists',text)){
				$("#tabs").tabs('select',text);
			}else{
				var content="<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="+url+"></iframe>";
				$("#tabs").tabs('add',{
					title:text,
					closable:true,
					content:content
				});
			}
		}	*/
	</script>

</body>
</html>