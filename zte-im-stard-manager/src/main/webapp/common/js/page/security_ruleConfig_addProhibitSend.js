define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取默认焦点
    $("#word").focus();
    
    //引入验证插件
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
        require.async('jquery-validation-1.13.1/additional-methods',function(){
            $("#addProhibitSendForm").validate({
                rules:{
                	words:{
                        required: true
                    }
                },
                messages:{
                	words:{
                        required: "请输入敏感词"
                    }
                },
                errorElement:'em',
                 submitHandler:function(form){
                    require.async('jquery.form.min',function(){
                        $(form).ajaxSubmit({
                            dataType:'json',
                            success:function(data){
                                if(data.result){
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