define(function(require, exports, module) {
	// 引入jquery库
	require('jquery-1.8.3.min');

	var index = parent.layer.getFrameIndex(window.name);
	// 获取iframeID
	var iframeId = window.parent.$("#iframeIdVal").val();

	// 获取部门名称TID
	var backDeptTId = $(window.parent.frames[iframeId].document).find("#deptTId").val();
	var selectDeptName = "";
	var selectDeptId = "";
	var selectDeptTId = "";
	// 加载部门
	require.async([ 'zTree_v3/jquery.ztree.core-3.5.min', 'zTree_v3/jquery.ztree.excheck-3.5.min',
			'zTree_v3/js/jquery.ztree.exhide-3.5.min', 'zTree_v3/zTreeStyle.css' ], function() {
		var setting = {
			async : {
				enable : true,
				url : "../../deptList.do?isSelect=" + true,
				autoParam : [ "id" ]
			},
			check : {
				enable : true,
				chkboxType : {
					"Y" : "",
					"N" : ""
				},
				autoCheckTrigger : false
			},
			view : {
				showIcon : false,
				showLine : false,
				autoCancelSelected : true,
				selectedMulti : true
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : 0
				}
			}
		};

		$.fn.zTree.init($("#deptTree"), setting);
	});

	// 确定
	$('#dialogDeptConfirm').on(
			'click',
			function() {
				var perms = ' ';
				var divPerms = '';
				var treeObj = $.fn.zTree.getZTreeObj("deptTree");
				var nodes = treeObj.getCheckedNodes();
				for ( var i = 0, l = nodes.length; i < l; i++) {
					perms += nodes[i].name + '；';
					divPerms += "<input id='" + nodes[i].id + "' type='hidden' name='" + nodes[i].name + "' value='"
							+ nodes[i].id + "' />";
				}
				if (perms.length > 0) {
					perms = perms.substring(0, perms.length - 1);
				}
				$(window.parent.frames[iframeId].document).find("#visibleDept").html(perms);
				$(window.parent.frames[iframeId].document).find("#deptPermDiv").html(divPerms);
				parent.layer.close(index);
			});

	// 取消操作
	$('#dialogDeptCancel').on('click', function() {
		parent.layer.close(index);
	});

	// 搜索
	$('#orgSearch').bind('input propertychange', function() {
		var key = $.trim($(this).val());
		var treeObj = $.fn.zTree.getZTreeObj("deptTree");
		if (key != '') {
			var all = treeObj.getNodesByParamFuzzy("name", "");
			treeObj.hideNodes(all);
			var objs = treeObj.getNodesByParamFuzzy("name", key);
			if (objs.length > 0) {
				for ( var i = 0; i < objs.length; i++) {
					var parent = objs[i].getParentNode();
					while (parent != null) {
						treeObj.showNode(parent);
						parent = parent.getParentNode();
					}
				}
			}
			treeObj.showNodes(objs);
		} else {
			var all = treeObj.getNodesByParamFuzzy("name", "");
			treeObj.showNodes(all);
		}
	});

});