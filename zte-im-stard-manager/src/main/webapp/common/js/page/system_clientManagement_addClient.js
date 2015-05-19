define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    var common = require('common');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
	var iframeId = parent.layer.getFrameIndex(window.name);
    window.parent.$("#iframeIdVal").val("xubox_iframe"+iframeId);
    
    $(".android").show();
	$(".ios").hide();
    $('#ctype').change(function(){
        var ctype = $(this).children('option:selected').val();
        if (ctype == 'Android应用') {
        	$(".android").show();
        	$(".ios").hide();
        	$("#cos").val('android');
        } else {
        	$(".android").hide();
        	$(".ios").show();
        	$("#cos").val('ios');
        }
    });
    
    
    //选择文件
    $('#photo').on('change', function(){
    	var fileObj = $("#photo");
    	var fileName = fileObj.val();
    	if (fileName != "") {
    		if (!common.isAllowedAttach(fileName, ' .apk .ipa ')) {
        		fileObj.val('');
    			//针对ie的clear实现
    			fileObj.replaceWith( fileObj = fileObj.clone( true ) );
    			top.layer.msg("文件格式不正确", 1, -1);
    			return;
    		}
        	$("#capk").val(fileName.split('\\').pop());
    	}
    });
    
    var isAddClient = true;
    //上传文件
    $('#upload').on('click', function(){
    	
    	var fileObj = $("#photo");
    	var fileName = fileObj.val();
    	if (fileName == "") {
    		top.layer.msg("请先选择应用文件", 1, -1);
			return;
    	} else if (!common.isAllowedAttach(fileName, ' .apk .ipa ')) {
    		fileObj.val('');
			//针对ie的clear实现
			fileObj.replaceWith( fileObj = fileObj.clone( true ) );
			top.layer.msg("文件格式不正确", 1, -1);
			return;
		} else {
			isAddClient = false;
			$("#addClientForm").attr("action", webPath + "uploadClient.do");
			$("#plist").rules("remove");
			$("#version").rules("remove");
			$("#capk").rules("remove");
			$("#cname").rules("remove");
			$("#addClientForm").submit();
		}
    });
    
    // 添加客户端
	$('#dialogAddBtn').on('click', function() {
		isAddClient = true;
		$("#addClientForm").attr("action", webPath + "addClient.do");
		$("#plist").rules("add", {
			required : true,
			messages : {
				required : "请输入plist文件地址"
			}
		});
		$("#version").rules("add", {
			required : true,
			messages : {
				required : "请输入版本"
			}
		});
		$("#capk").rules("add", {
			required : true,
			messages : {
				required : "请选择并上传应用文件"
			}
		});
		$("#cname").rules("add", {
			required : true,
			messages : {
				required : "请输入应用名称"
			}
		});
		$("#addClientForm").submit();
	});
    
    //添加操作
    //引入验证插件
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
    	require.async('jquery-validation-1.13.1/additional-methods',function(){
    		$("#addClientForm").validate({
                errorElement:'em',
                errorPlacement:function(error,element){
                    element.parent().parent().find("span").html(error);
                },
                submitHandler:function(form){
                	
                	if (isAddClient) {
                		if ($("#addUploading").css('display') == 'block') {
                			top.layer.msg("请等待文件处理成功", 1, -1);
                			return;
                		}
                	} else {
                		$("#addUploading").css('display', 'block');
                	}
                	
                    require.async('jquery.form.min',function(){
                        $(form).ajaxSubmit({
                            dataType:'json',
                            type: 'post',
                            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
                            success:function(data){
                                if(data.result){
                                	if (isAddClient) {
                                		window.parent.frames['mainiframe'].location.reload();
                                    	top.layer.closeAll();
									} else {
										$("#capk").val(data.data.capk);
	                					$("#capkUrl").val(data.fileUrl);
	                					$("#cname").val(data.data.cname);
	                					$("#cicon").attr('src', data.data.ciconUrl);
	                					$("#ciconUrl").val(data.data.ciconUrl);
	                					$("#version").val(data.data.version);
	                					$("#versionCode").val(data.data.versionCode);
	                					$("#cos").val(data.data.cos);
									}
                                } else{
                                	top.layer.msg(data.msg, 1, -1);
                                }
                            },
                            complete:function() {
                            	$("#addUploading").css('display', 'none');
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