define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
   
    //获取网站根路径
    var webPath=$("#webPath").val();
    var roleId=$("#roleId").val();
    
    //选择人员
    $('#selectPerson').on('click', function(){
        top.$.layer({
            type: 2,
            title: "选择人员",
            area: ['720px', '404px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.1, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/system/role/seletPerson.jsp'}
        });
    });
    
    //保存
    //引入验证插件
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
    	require.async('jquery-validation-1.13.1/additional-methods',function(){
    		$("#addRoleForm").validate({
                rules:{
                	roleName:{
                        required: true
                    }
                },
                messages:{
                	roleName:{
                        required: "请输入角色名称"
                    }
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
    	});
    });
    
    //删除角色
    $("#delRoleBtn").click(function(){
    	top.$.layer({
            type: 2,
            title: "提醒",
            area: ['332px', '200px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/system/role/delTips.jsp'}
        });
    });
    
    // 取消
    $("#cancelBtn").click(function(){
    	window.location.href = "roleShow.jsp?roleId="+roleId;
    });
});