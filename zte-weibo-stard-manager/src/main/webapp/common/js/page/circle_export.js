define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var groupId = $("#groupId").val();
    var groupIds = groupId.split(",");
    var groupUrl = "";
    for(var i = 0 ; i < groupIds.length-1;i++){
    	groupUrl += "&groupId="+groupIds[i];
    }
    
    // 设置是否是全部导出
    if(groupIds.length > 1){
    	$("#notAll").css("display","");
    	$("#all").css("display","none");
    }else{
    	$("#all").css("display","");
    	$("#notAll").css("display","none");
    }
    
    var loadi;
    //确认操作
    $('#dialogOkBtn').on('click', function(){
    	
    	$.ajax({
            url:webPath + "circle/export.do?randomTime=" + (new Date()).getTime() + groupUrl,
            dataType:"json",//这个必不可少，如果缺少，导致传回来的不是json格式
            type: 'post',
//          async:false,
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            beforeSend:function(data){
            	loadi = top.layer.load('数据导出中…');
            },
            success:function(data){
            	top.layer.close(loadi);
            	data = typeof data == 'object'?data: $.parseJSON(data);
            	if(!data.result){
            		top.layer.msg(data.msg, 1, -1);
            	}else{
            		window.location.href = data.fileUrl;
            		setTimeout(function(){
            			 top.layer.closeAll();
            		},500);
            	}
            },
            error:function(data){
            	top.layer.msg("导出失败！", 1, -1);
            }
        });
    });
    
    //取消操作
    $('#dialogCancelBtn').on('click', function(){
        top.layer.closeAll();
    });
});