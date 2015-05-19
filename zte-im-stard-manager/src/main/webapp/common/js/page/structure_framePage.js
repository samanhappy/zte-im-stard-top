define(function(require, exports, module) {
	// 引入jquery库
	require('jquery-1.8.3.min');
	
	 //获取网站根路径
    var webPath=$("#webPath").val();
	
	// 载入部门列表
	getDeptList();
	var deptId = "";
	
	function getDeptList() {
		require.async([ 'zTree_v3/jquery.ztree.core-3.5.min', 'zTree_v3/jquery.ztree.excheck-3.5.min',
				'zTree_v3/zTreeStyle.css' ], function() {
			var setting = {
				async : {
					enable : true,
					url : "../../deptList.do?isSelect=" + false,
					autoParam : [ "id" ]
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
					onClick : deptOnClick,
					onAsyncSuccess : zTreeOnAsyncSuccess
				}
			};

			$.fn.zTree.init($("#deptTree"), setting);

			function deptOnClick(event, treeId, treeNode) {
				deptId = treeNode.id;
			}

			function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
				var treeObj = $.fn.zTree.getZTreeObj("deptTree");
				var groupId = $("#groupId").val();
				if (groupId != '' && groupId != 'null') {
					var node = treeObj.getNodeByParam("id", groupId, null);
					treeObj.selectNode(node, false);
					deptId = groupId;
				}
			}
		});
	}

	// 添加部门
	$('#addDepartmentBtn').on('click', function() {
		top.$.layer({
			type : 2,
			title : "添加部门",
			area : [ '600px', '356px' ],
			closeBtn : [ 0, true ],
			border : [ 0 ],
			shade : [ 0.7, '#000' ],
			shadeClose : false,
			fadeIn : 300,
			move : false,
			fix : true,
			iframe : {
				src : 'module/structure/addDepartment.jsp'
			}
		});
	});

	// 编辑部门
	$('#editDepartmentBtn').on('click', function() {
		if (deptId == '') {
			top.layer.msg('请选择部门', 1, -1);
		} else if (deptId == $("#tenantId").val()) {
			top.layer.msg('公司不能编辑', 1, -1);
		} else {
			top.$.layer({
				type : 2,
				title : "编辑部门",
				area : [ '600px', '356px' ],
				closeBtn : [ 0, true ],
				border : [ 0 ],
				shade : [ 0.7, '#000' ],
				shadeClose : false,
				fadeIn : 300,
				move : false,
				fix : true,
				iframe : {
					src : 'module/structure/editDepartment.jsp?deptId=' + deptId
				}
			});
		}
	});
	
	// 加载角色权限
	loadPrivileges();
	var privileges = null;
	var allPrivileges = ["deptManage","userManage","userImport","userExport"];
	function loadPrivileges() {
		$.ajax({
			type : "POST",
			url : webPath + 'getPrivileges.do?t=' + (new Date()).getTime(),
			dataType : "json",
			success : function(data) {
				privileges = JSON.stringify(data);
			},
			complete : function() {
				if (privileges == null) {
					for (var i in allPrivileges) {
						$("." + allPrivileges[i]).unbind('click');
						$("." + allPrivileges[i]).css('color','gray');
						$("." + allPrivileges[i]).css('cursor','text');
					}
				} else if (privileges != '\"all\"') {
					for (var i in allPrivileges) {
						if (privileges.indexOf(allPrivileges[i]) == -1) {
							$("." + allPrivileges[i]).unbind('click');
							$("." + allPrivileges[i]).css('color','gray');
							$("." + allPrivileges[i]).css('cursor','text');
						}
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
});