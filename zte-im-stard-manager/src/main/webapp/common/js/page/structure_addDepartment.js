define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取默认焦点
    $("#gname").focus();

    var iframeId = parent.layer.getFrameIndex(window.name);
    window.parent.$("#iframeIdVal").val("xubox_iframe"+iframeId);
    
    //上级部门
    $('#openDept').on('click', function(){
    	$('#deptName').blur();
        parent.$.layer({
            type: 2,
            title: "选择部门",
            area: ['720px', '404px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.1, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/structure/selectDepartment.jsp'}
        });
    });
    
    //添加操作
    //引入验证插件
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
    	require.async('jquery-validation-1.13.1/additional-methods',function(){
    		$("#addDepartmentForm").validate({
                rules:{
                	gname:{
                        required: true
                    },
                    deptName:{
                        required: true
                    },
                    sequ:{
                        required: true
                    }
                },
                messages:{
                	gname:{
                        required: "请输入部门名称"
                    },
                    deptName:{
                        required: "请选择上级部门"
                    },
                    sequ:{
                        required: "请输入排序号"
                    }
                },
                errorElement:'em',
                errorPlacement:function(error,element){
                    element.parent().siblings("span").html(error);
                },
                submitHandler:function(form){
                    require.async('jquery.form.min',function(){
                        $(form).ajaxSubmit({
                            dataType:'json',
                            type: 'post',
                            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
                            success:function(data){
                                if(data.result){
                                	parent.location.reload();
                                }else{
                                	top.layer.msg(data.msg, 1, -1);
                                }
                            }
                        });
                    });
                 } 
            });
    	})
    })

    //取消操作
    $('#dialogCancelDept').on('click', function(){
        top.layer.closeAll();
    });

});