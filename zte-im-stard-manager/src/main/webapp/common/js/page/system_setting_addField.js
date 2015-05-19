define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //引入验证插件
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
        require.async('jquery-validation-1.13.1/additional-methods',function(){
            $("#addFieldForm").validate({
            	rules:{
            		paramName:{
                        required: true
                    },
                    paramType:{
                        required: true
                    }
                },
                messages:{
                	paramName:{
                        required: "请输入参数名称"
                    },
                    paramType:{
                        required: "请选择参数类型"
                    }
                },
                errorElement:'em',
                errorPlacement:function(error,element){
                    element.parent().parent().find("span").html(error);
                },
                submitHandler:function(form){
                    require.async('jquery.form.min',function(){
                        $(form).ajaxSubmit({
                        	dataType:'json',
                            type: 'post',
                            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
                            success:function(data){
                                if(data.result){
                                	window.parent.frames['mainiframe'].location.reload();
                                	top.layer.closeAll();
                                }else{
                                    top.layer.msg('添加失败', 2, -1);
                                }
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