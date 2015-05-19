define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var common = require('common');

    //加载企业元素
	$.ajax({
		type : "POST",
		url : webPath + 'getTenantCustomization.do',
		dataType : "json",
		success : function(data) {
			if (data.result) {
				if (data.data) {
					if (data.data.serverLogo) {
						$("#serverLogo").val(data.data.serverLogo);
						$("#serverPhoto").attr('src',data.data.serverLogo);
					}
					if (data.data.serverLoginLogo) {
						$("#serverLoginLogo").val(data.data.serverLoginLogo);
						$("#serverLoginPhoto").attr('src',data.data.serverLoginLogo);
					}
					$("#serverManagerName").val(data.data.serverManagerName);
					$("#serverCopyright").val(data.data.serverCopyright);
					
					if (data.data.serverLogo != '') {
						$("#title",parent.document).css('background',
								'url(' + data.data.serverLogo + ') 15px 13px no-repeat');
					}
					if (data.data.serverManagerName != '') {
						$("#title",parent.document).html(data.data.serverManagerName);
					}
					if (data.data.serverCopyright != '') {
						$("#footer",parent.document).html(data.data.serverCopyright);
					}
					
					if (data.data.clientLogo) {
						$("#clientLogo").val(data.data.clientLogo);
						$("#clientPhoto").attr('src',data.data.clientLogo);
					}
					$("#clientName").val(data.data.clientName);
					$("#clientCopyright").val(data.data.clientCopyright);
					$("#clientHomePage").val(data.data.clientHomePage);
					$("#clientContact").val(data.data.clientContact);
				}
			} else {
				top.layer.msg(data.msg, 1, -1);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.layer.msg("数据操作出错", 1, -1);
		}
	});
    
    // 上传图片
    var logoFileId = null;
    $('#serverLogoFile').on('change', function(){
    	logoFileId = 'serverLogoFile';
    	submitForm();
    });
    $('#loginLogoFile').on('change', function(){
    	logoFileId = 'loginLogoFile';
    	submitForm();
    });
    $('#clientLogoFile').on('change', function(){
    	logoFileId = 'clientLogoFile';
    	submitForm();
    });
    
    function submitForm() {
    	$("#serverLogoFile").attr('disabled','disabled');
    	$("#loginLogoFile").attr('disabled','disabled');
    	$("#clientLogoFile").attr('disabled','disabled');
    	$("#" + logoFileId).removeAttr('disabled');
    	var photo = $("#" + logoFileId).val();
		if (photo != "") {
			var fileObj = $("#" + logoFileId);
			if (!common.isAllowedAttach(photo, ' .jpeg .gif .jpg .png ')) {
				$("#" + logoFileId).val('');
				//针对ie的clear实现
				fileObj.replaceWith( fileObj = fileObj.clone( true ) );
				top.layer.msg("图片格式不正确", 1, -1);
				return false;
			}
			if (!common.checkfileById(logoFileId)) {
				$("#" + logoFileId).val('');
				//针对ie的clear实现
				fileObj.replaceWith( fileObj = fileObj.clone( true ) );
				return false;
			}
			$("#enterpriseCustomizationForm").attr("action", webPath + "uploadFile.do");
			$("#enterpriseCustomizationForm").submit();
		}
    }
    

    //更新企业元素
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
    	require.async('jquery-validation-1.13.1/additional-methods',function(){
    		$("#enterpriseCustomizationForm").validate({
                submitHandler:function(form){
                    require.async('jquery.form.min',function(){
                        $(form).ajaxSubmit({
                            dataType:'json',
                            type: 'post',
                            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
                            success:function(data){
                                if(data.result){
                                	if (logoFileId == 'serverLogoFile') {
                            			$("#serverPhoto").attr("src", data.sourceFileUrl);
                						$("#serverLogo").val(data.sourceFileUrl);
                            		} else if (logoFileId == 'loginLogoFile') {
                            			$("#serverLoginPhoto").attr("src", data.sourceFileUrl);
                						$("#serverLoginLogo").val(data.sourceFileUrl);
                            		} else if (logoFileId == 'clientLogoFile') {
                            			$("#clientPhoto").attr("src", data.sourceFileUrl);
                						$("#clientLogo").val(data.sourceFileUrl);
                            		} else {
                            			window.location.reload();
                            		}
                                }else{
                                	top.layer.msg(data.msg, 1, -1);
                                }
                            },
                            complete:function() {
                            	$("#serverLogoFile").removeAttr('disabled');
                            	$("#loginLogoFile").removeAttr('disabled');
                            	$("#clientLogoFile").removeAttr('disabled');
                            	var fileObj = $("#" + logoFileId);
                            	$("#" + logoFileId).val('');
                				//针对ie的clear实现
                            	fileObj.replaceWith( fileObj = fileObj.clone( true ) );
                            }
                        });
                    });
                 } 
            });
    	});
    });
    
	$('#dialogAddBtn').on('click', function() {
		logoFileId = null;
		$("#enterpriseCustomizationForm").attr("action", webPath + "updateTenantCustomization.do");
		$("#enterpriseCustomizationForm").submit();
	});
});