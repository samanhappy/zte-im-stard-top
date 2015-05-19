define(function(require, exports, module) {
	// 引入jquery库
	require('jquery-1.8.3.min');

	// 获取网站根路径
	var webPath = $("#webPath").val();
	
	// 加载通讯录参数
	$.ajax({
		type : "POST",
		url : webPath + 'getContactParam.do',
		dataType : "json",
		success : function(data) {
			if (data.result) {
				var param = data.data;
				$("#contactOrgName").val(param.contactOrgName);
				$("input[name=syncLDAP][value=" + param.syncLDAP + "]").attr(
						"checked", 'checked');
				$("#contactLdapUrl").val(param.contactLdapUrl);
				$("#contactBaseDN").val(param.contactBaseDN);
				$("#protectedPropSpan").html(param.protectedPropNames);
				$("#protectedPropNames").val(param.protectedPropNames);
				$("#protectedPropVals").val(param.protectedPropVals);
				$("#editablePropSpan").html(param.editablePropNames);
				$("#editablePropNames").val(param.editablePropNames);
				$("#editablePropVals").val(param.editablePropVals);
			} else {
				top.layer.msg(data.msg, 1, -1);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.layer.msg("数据操作出错", 1, -1);
		}
	});

	// 成员属性访问保护
	$("#memberPropertiesSetting").on('click', function() {
		top.$.layer({
			type : 2,
			title : "设置受保护属性",
			area : [ '600px', '356px' ],
			closeBtn : [ 0, true ],
			border : [ 0 ],
			shade : [ 0.7, '#000' ],
			shadeClose : false,
			fadeIn : 300,
			move : false,
			fix : true,
			iframe : {
				src : 'module/system/setting/memberPropertiesSetting.jsp?protectedPropVals=' + $("#protectedPropVals").val()
			}
		});
	});

	// 通讯录个人可设置的属性
	$("#addressBookSetting").on('click', function() {
		top.$.layer({
			type : 2,
			title : "设置可更改属性",
			area : [ '600px', '356px' ],
			closeBtn : [ 0, true ],
			border : [ 0 ],
			shade : [ 0.7, '#000' ],
			shadeClose : false,
			fadeIn : 300,
			move : false,
			fix : true,
			iframe : {
				src : 'module/system/setting/addressBookSetting.jsp?editablePropVals=' + $("#editablePropVals").val()
			}
		});
	})

	// 保存
	// 引入验证插件
	require.async('jquery-validation-1.13.1/jquery.validate.min', function() {
		require.async('jquery-validation-1.13.1/additional-methods',
				function() {
					$("#systemAddressBookForm").validate({
						submitHandler : function(form) {
							require.async('jquery.form.min', function() {
								$(form).ajaxSubmit({
									type: 'post',
						            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
						            dataType:"json",
									success : function(data) {
										if (data.result) {
											top.layer.msg('保存成功！', 1, -1);
										} else {
											top.layer.msg('保存失败！', 1, -1);
										}
									}
								});
							});
						}
					});
				});
	});

	// 默认设置
	$("#defaultValue").on('click', function() {

	});
	
	// 加载角色权限
	loadPrivileges();
	var privileges = null;
	var allPrivileges = ["updateContactParam"];
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
						$("." + allPrivileges[i]).css('display', 'none');
					}
				} else if (privileges != '\"all\"') {
					for (var i in allPrivileges) {
						if (privileges.indexOf(allPrivileges[i]) == -1) {
							$("." + allPrivileges[i]).css('display', 'none');
						}
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
});