define(function(require, exports, module) {
	// 引入jquery库
	require('jquery-1.8.3.min');

	// 获取网站根路径
	var webPath = $("#webPath").val();

	// 加载系统参数
	$.ajax({
		type : "POST",
		url : webPath + 'getSysParam.do',
		dataType : "json",
		success : function(data) {
			if (data.result) {
				var param = data.data;
				$("input[name=pwdExpire][value=" + param.pwdExpire + "]").attr(
						"checked", 'checked');
				$("input[name=pwdLength][value=" + param.pwdLength + "]").attr(
						"checked", 'checked');
				$("input[name=pwdCheck][value=" + param.pwdCheck + "]").attr(
						"checked", 'checked');
				$(
						"input[name=pwdFirstCheck][value="
								+ param.pwdFirstCheck + "]").attr("checked",
						'checked');
				$("input[name=loginCheck][value=" + param.loginCheck + "]")
						.attr("checked", 'checked');
				$("input[name=ipCheck][value=" + param.ipCheck + "]").attr(
						"checked", 'checked');
				$(
						"input[name=loginAuthType][value="
								+ param.loginAuthType + "]").attr("checked",
						'checked');
				$("#pwdPeriod").val(param.pwdPeriod);
				$("#pwdMinLength").val(param.pwdMinLength);
				$("#pwdMaxLength").val(param.pwdMaxLength);
				$("#ldapUrl").val(param.ldapUrl);
				$("#baseDN").val(param.baseDN);
				$("#domain").val(param.domain);
			} else {
				top.layer.msg(data.msg, 1, -1);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.layer.msg("数据操作出错", 1, -1);
		}
	});

	// 保存
	// 引入验证插件
	require.async('jquery-validation-1.13.1/jquery.validate.min', function() {
		require.async('jquery-validation-1.13.1/additional-methods',
				function() {
					$("#settingSystemForm").validate({
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
	var allPrivileges = ["updateSystemParam"];
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