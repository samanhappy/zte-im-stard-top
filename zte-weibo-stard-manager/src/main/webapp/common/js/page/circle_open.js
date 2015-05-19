define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    var loadi;
    //确认操作
    $('#dialogOkBtn').on('click', function(){
    	var groupId = $("#groupId").val();
        var groupIds = groupId.split(",");
        var groupIdUrl = "";
        var stopDesc = $("#stopDesc").val();
        for(var i = 0 ; i < groupIds.length-1;i++){
        	groupIdUrl += "&groupId="+groupIds[i];
        }
    	$.ajax({
            url:webPath + "circle/modifyStatus.do?randomTime=" + (new Date()).getTime() + groupIdUrl,
            dataType:"json",//这个必不可少，如果缺少，导致传回来的不是json格式
            type: 'post',
//          async:false,
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            data:{status:'00',stopDesc:''},
            beforeSend:function(data){
            	loadi = top.layer.load('圈子启用中…');
            },
            success:function(data){
            	top.layer.close(loadi);
            	data = typeof data == 'object'?data: $.parseJSON(data);
            	if(!data.result){
            		top.layer.msg(data.msg, 1, -1);
            	}else{
            		top.layer.msg('操作成功', 1, -1);
            	}
            	//刷新列表页面并关闭窗口
            	setTimeout(function(){
            		//刷新列表页面并关闭窗口
//            		top.frames[0].location.reload();
                	top.frames[0].search(); 
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