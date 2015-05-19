define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    var loadi;
    //确认操作
    $('#dialogOkBtn').on('click', function(){
    	var setAttentionDept = $("#setAttentionDept:checked");
    	if(setAttentionDept.length > 0){
    		setAttentionDept = true;
    	}else{
    		setAttentionDept = false;
    	}
    	$.ajax({
			url:webPath + "account/initData.do?randomTime=" + (new Date()).getTime(),
	        dataType:"json",
	        type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            data:{
            	isSetAttentionDept:setAttentionDept
            },
	        beforeSend:function(data){
            	loadi = top.layer.load('数据处理中…');
            },
            error:function(data){
            	top.layer.alert("初始化发生错误！",-1);
            },
            success:function(data){
            	top.layer.close(loadi);
            	if(data.result){
        			top.layer.msg("初始化完成",1,-1);
            	}else{
            		top.layer.alert("初始化发生错误！\n"+data.msg,-1);
            	}
            	setTimeout(function(){
            		//刷新列表页面并关闭窗口
//                	top.frames[0].location.reload(); 
            		top.frames[0].location.reload();
                	top.layer.closeAll();
            	}, 500);
            }
		});
    });
    
    //取消操作
    $('#dialogCancelBtn').on('click', function(){
        top.layer.closeAll();
    });
    
    
});