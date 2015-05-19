define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    var common = require('common');
    
    // 上传文件
    $('#uploadFileA').on('click', function(){
        $("#photo").click();
    });
    
    $('#photo').on('change', function(){
    	var photo = $("#photo").val();
		if (photo != "") {
			var fileObj = $("#photo");
			if (!common.isAllowedAttach(photo, ' .xlsx ')) {
				$("#photo").val('');
				//针对ie的clear实现
				fileObj.replaceWith( fileObj = fileObj.clone( true ) );
				top.layer.msg("文件格式不正确，请选择xlsx文件", 1, -1);
				return false;
			}
			
			$("#uploadFileForm").submit();
		}
    });
    
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
    	require.async('jquery-validation-1.13.1/additional-methods',function(){
    		$("#uploadFileForm").validate({
                submitHandler:function(form){
                    require.async('jquery.form.min',function(){
                        $(form).ajaxSubmit({
                            dataType:'json',
                            type: 'post',
                            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
                            success:function(data){
                            	if (data.result) {
                            		$("#fileUrl").val(data.fileUrl);
            						$("#uploadTip").css("display", "none");
            						$("#templateTd").css("display", "none");
            						$("#uploadedFile").css("display", "block");
            						$("#fileSpan").html("&nbsp;&nbsp;" + $("#photo").val().split("\\").pop());
            					} else {
            						top.layer.msg(data.msg, 1, -1);
            					}
                            },
                            complete:function() {
                            	var fileObj = $("#photo");
                            	$("#photo").val('');
                				//针对ie的clear实现
                            	fileObj.replaceWith( fileObj = fileObj.clone( true ) );
                            }
                        });
                    });
                 } 
            });
    	});
    });
    
    
    //导入操作
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
    	require.async('jquery-validation-1.13.1/additional-methods',function(){
    		$("#importUserForm").validate({
                submitHandler:function(form){
                	var fileUrl = $("#fileUrl").val();
            		if (fileUrl == "") {
            			top.layer.msg('请先上传文件', 1, -1);
            			return;
            		}
                    require.async('jquery.form.min',function(){
                        $(form).ajaxSubmit({
                            dataType:'json',
                            type: 'post',
                            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
                            success:function(data){
                            	if (data.result) {
                            		parent.location.reload();
            					} else {
            						top.layer.msg(data.msg, 1, -1);
            					}
                            },
                            complete:function() {
                            	var fileObj = $("#photo");
                            	$("#photo").val('');
                				//针对ie的clear实现
                            	fileObj.replaceWith( fileObj = fileObj.clone( true ) );
                            }
                        });
                    });
                 } 
            });
    	});
    });
    
    
    //取消操作
    $('#dialogCancelBtn').on('click', function(){
        top.layer.closeAll();
    });
});