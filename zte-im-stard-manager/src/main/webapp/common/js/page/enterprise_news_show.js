define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    //引入Base64库
    require('Base64');
    var common = require('common');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var news_id=$("#news_id").val();
    
	// 获取新闻
	getNewsInfo();
    function getNewsInfo(){
    	$.ajax({
    		url:webPath + "editNews.do?id="+ news_id,
            type:"get",
            dataType:"json",
            success:function(data){
            	if(data.result){
        			$('#news_title').html(data.data.title);
        			$('#author').html(data.data.author);
        			var date = new Date(data.data.date);
        			$('#date').html(date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate() );
        			$('#content').html(common.base2Str(BASE64
        					.decoder(data.data.content)));
                }else{
                	top.layer.msg(data.msg, 1, -1);
                }
            }
    	});
    }
    
    
    // 发布新闻
    $('#release').on('click', function(){
        $("#newsForm").submit();
    });
    
    
    //添加操作
    //引入验证插件
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
    	require.async('jquery-validation-1.13.1/additional-methods',function(){
    		$("#newsForm").validate({
                rules:{
                	news_title:{
                        required: true
                    },
                    news_type:{
                        required: true
                    },
                    news_con:{
                        required: true
                    }
                },
                messages:{
                	news_title:{
                        required: "请输入标题"
                    },
                    news_type:{
                        required: "请选择类型"
                    },
                    news_con:{
                        required: "请输入内容"
                    }
                },
                submitHandler:function(form){
                	editor1.sync();
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