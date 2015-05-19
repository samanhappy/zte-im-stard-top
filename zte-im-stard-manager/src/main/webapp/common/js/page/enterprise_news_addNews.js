define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    require('kindeditor-4.1.10/kindeditor');
    require('kindeditor-4.1.10/plugins/code/prettify');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    //新闻编辑插件
    KindEditor.ready(function(K) {
		window.editor1 = K.create('textarea[name="news_con"]', {
			cssPath : webPath + 'js/kindeditor-4.1.10/plugins/code/prettify.css',
			uploadJson : webPath + 'js/kindeditor-4.1.10/jsp/upload_json.jsp',
			fileManagerJson : webPath + 'js/kindeditor-4.1.10/jsp/file_manager_json.jsp',
			allowFileManager : true,
		});
		prettyPrint();
	});
    
    // 发布新闻
    $('#release').on('click', function(){
    	editor1.sync();
    	if ($("#news_title").val() == '') {
    		top.layer.msg("请输入标题", 1, -1);
    		return;
    	}
    	if ($("#news_type").val() == '') {
    		top.layer.msg("请选择类型", 1, -1);
    		return;
    	}
    	if ($("#news_con").val() == '') {
    		top.layer.msg("请输入内容", 1, -1);
    		return;
    	}
    	$("#news_con_text").val(editor1.text());
        $("#newsForm").submit();
    });
    
    // 预览发布新闻
    $('#pre_release').on('click', function(){
        $("#newsForm").submit();
    });
    
    // 预览新闻
    $('#preview').on('click', function(){
    	editor1.sync();
    	if ($("#news_title").val() == '') {
    		top.layer.msg("请输入标题", 1, -1);
    		return;
    	}
    	if ($("#news_type").val() == '') {
    		top.layer.msg("请选择类型", 1, -1);
    		return;
    	}
    	if ($("#news_con").val() == '') {
    		top.layer.msg("请输入内容", 1, -1);
    		return;
    	}
    	$('#pre_title').html($("#news_title").val());
		$('#pre_author').html("system");
		var date = new Date();
		$('#pre_date').html(date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate() );
		$('#pre_content').html($("#news_con").val());
		$(".add").css('display','none');
		$(".pre").css('display','block');
    });
    
    
    // 预览新闻返回
    $('#pre_return').on('click', function(){
		$(".add").css('display','block');
		$(".pre").css('display','none');
    });
    
    //添加操作
    //引入验证插件
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
    	require.async('jquery-validation-1.13.1/additional-methods',function(){
    		$("#newsForm").validate({
                submitHandler:function(form){
                    require.async('jquery.form.min',function(){
                        $(form).ajaxSubmit({
                            dataType:'json',
                            type: 'post',
                            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
                            success:function(data){
                                if(data.result){
                                	window.location.href = 'list.jsp';
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
    
});