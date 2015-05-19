define(function(require, exports, module) {
	// 引入jquery库
	require('jquery-1.8.3.min');
	require('jquery-validation-1.13.1/jquery.validate.min');

	var index = parent.layer.getFrameIndex(window.name);
	// 获取iframeID
	var iframeId = window.parent.$("#iframeIdVal").val();
	// 获取部门名称
	var backDeptName = $(window.parent.frames[iframeId].document).find("#deptName").val();
	// 获取部门名称ID
	var backDeptId = $(window.parent.frames[iframeId].document).find("#deptId").val();
	// 获取部门名称TID
	var backDeptTId = $(window.parent.frames[iframeId].document).find("#deptTId").val();
	var selectDeptName = "";
	var selectDeptId = "";
	var selectDeptTId = "";
	// 加载部门
	require.async([ 'zTree_v3/jquery.ztree.core-3.5.min', 'zTree_v3/jquery.ztree.excheck-3.5.min', ,
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
				autoCheckTrigger : true,
				chkStyle : "radio",
				radioType : "all"
			},
			view : {
				showIcon : false,
				showLine : false,
				autoCancelSelected : false,
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : 0
				}
			},
			callback : {
				onCheck : deptOnCheck,
				onClick : deptOnClick,
				onAsyncSuccess : zTreeOnAsyncSuccess
			}
		};

		$.fn.zTree.init($("#deptTree"), setting);

		function deptOnClick(event, treeId, treeNode) {
			var treeObj = $.fn.zTree.getZTreeObj("deptTree");
			var nodes = treeObj.getSelectedNodes();
			for ( var i = 0, l = nodes.length; i < l; i++) {
				treeObj.checkNode(nodes[i], true, true);
			}
			selectDeptName = treeNode.name;
			selectDeptId = treeNode.id;
			selectDeptTId = treeNode.tId;
		}

		function deptOnCheck(event, treeId, treeNode) {
			if (treeNode.checked) {
				selectDeptName = treeNode.name;
				selectDeptId = treeNode.id;
				selectDeptTId = treeNode.tId;
			} else {
				selectDeptName = "";
				selectDeptId = "";
				selectDeptTId = "";
			}
		}

		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			var treeObj = $.fn.zTree.getZTreeObj("deptTree");
			if (backDeptTId != null && backDeptTId != '') {
				var node = treeObj.getNodeByTId(backDeptTId);
				treeObj.checkNode(node, true, true);
				selectDeptName = node.name;
				selectDeptId = node.id;
				selectDeptTId = node.tId;
			} else if ($("#id").val() != '') {
				var node = treeObj.getNodeByParam("id", $("#id").val(), null);
				treeObj.checkNode(node, true, true);
				selectDeptName = node.name;
				selectDeptId = node.id;
				selectDeptTId = node.tId;
			}
		}
	});

	// 确定
	$('#dialogDeptConfirm').on('click', function() {
		$(window.parent.frames[iframeId].document).find("#deptName").val(selectDeptName);
		$(window.parent.frames[iframeId].document).find("#deptName").valid();
		$(window.parent.frames[iframeId].document).find("#deptId").val(selectDeptId);
		$(window.parent.frames[iframeId].document).find("#deptTId").val(selectDeptTId);
		parent.layer.close(index);
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
				for (var i = 0; i < objs.length; i++) {
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