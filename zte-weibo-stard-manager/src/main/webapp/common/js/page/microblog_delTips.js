define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    var loadi;
    //确认操作
    $('#dialogOkBtn').on('click', function(){
    	var twitterId = $("#twitterId").val();
        var twitterIds = twitterId.split(",");
        var twitterIdUrl = "";
        for(var i = 0 ; i < twitterIds.length-1;i++){
        	twitterIdUrl += "&id="+twitterIds[i];
        }
    	$.ajax({
            url:webPath + "microblog/del.do?randomTime=" + (new Date()).getTime() + twitterIdUrl,
            dataType:"json",//这个必不可少，如果缺少，导致传回来的不是json格式
            type: 'post',
            async:true,
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            beforeSend:function(data){
            	loadi = top.layer.load('数据处理中…');
            },
            success:function(data){
            	top.layer.close(loadi);
            	data = typeof data == 'object'?data: $.parseJSON(data);
            	if(!data.result){
            		top.layer.msg(data.msg, 1, -1);
            	}else{
            		setTimeout(function(){
            			//刷新列表页面并关闭窗口
//                    	top.frames[0].location.reload(); 
            			top.frames[0].search();
            			top.layer.closeAll();
            		},500);
            	}
            }
        });
    	
    });
    
    //取消操作
    $('#dialogCancelBtn').on('click', function(){
        top.layer.closeAll();
    });
});