define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
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
                    password:{
                        required: true
                    }
                },
                messages:{
                    name:{
                        required: "请输入账号"
                    },
                    password:{
                        required: "请输入密码"
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
                                    window.location.href = webPath + "index.jsp";
                                }else{
                                    $(".loginTipMsg").addClass("errMsg").html(data.msg);
                                    $("#password").val("");
                                }
                            }
                        });
                    });
                 } 
            });
        });
    });
});