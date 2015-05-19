define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取默认焦点
    $("#name").focus();
    
    //引入验证插件
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
        require.async('jquery-validation-1.13.1/additional-methods',function(){
            $("#login").validate({
                rules:{
                    name:{
                        required: true
                    },
                    email:{
                        required: true,
                        email:true
                    }
                },
                messages:{
                    name:{
                        required: "请输入账号"
                    },
                    email:{
                        required: "请输入邮箱",
                        email:"请输入正确的邮箱地址"
                    }
                },
                 errorElement:'em',
                 showErrors:function(errorMap, errorList){
                     this.defaultShowErrors();
                     if(errorMap.name){
                        $(".loginTipMsg").addClass("errMsg").html(errorMap.name);
                     }else if(errorMap.password){
                        $(".loginTipMsg").addClass("errMsg").html(errorMap.password);
                     }else{
                        $(".loginTips").html("");
                     }
                 },
                 errorPlacement:function(error,element){
                 },
                 submitHandler:function(form){
                    require.async('jquery.form.min',function(){
                        $(form).ajaxSubmit({
                            dataType:'json',
                            success:function(data){
                                if(data.result){
                                    $(".loginTipMsg").removeClass("errMsg").addClass("correctMsg").html(data.msg);
                                }else{
                                    $(".loginTipMsg").addClass("errMsg").html(data.msg);
                                }
                            }
                        });
                    });
                 } 
            });
        });
    });
});