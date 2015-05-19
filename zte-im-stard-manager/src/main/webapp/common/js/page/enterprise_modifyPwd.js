define(function(require, exports, module) {
	// 引入jquery库
	require('jquery-1.8.3.min');

	// 获取网站根路径
	var webPath = $("#webPath").val();

	// 引入验证插件
	require.async('jquery-validation-1.13.1/jquery.validate.min', function() {
		require.async('jquery-validation-1.13.1/additional-methods', function() {
			$("#modifyPwdForm").validate({
				rules : {
					opass : {
						required : true
					},
					pass : {
						required : true
					},
					rpass : {
						required : true,
						equalTo : "#pass"
					}
				},
				messages : {
					opass : {
						required : "请输入原始密码"
					},
					pass : {
						required : "请输入新密码"
					},
					rpass : {
						required : "请再输入新密码",
						equalTo : "两次密码不一致"
					}
				},
				errorElement : 'em',
				errorPlacement : function(error, element) {
					element.parent().siblings().html(error);
				},
				submitHandler : function(form) {
					require.async('jquery.form.min', function() {
						$(form).ajaxSubmit({
							dataType : 'json',
							success : function(data) {
								if (data.result) {
									top.layer.alert('修改成功，点击确定重新登录', -1, function() {
										window.location.href = webPath + 'logout.do';
									});
								} else {
									top.layer.msg(data.msg, 1, -1);
									$("#opass").val("").focus();
								}
							}
						});
					});
				}
			});
		});
	});

});