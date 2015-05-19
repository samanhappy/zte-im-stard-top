define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var uid = $("#uid").val();
    var uids = uid.split(",");
    var uidUrl = "";
    for(var i = 0 ; i < uids.length-1;i++){
    	uidUrl += "&uid="+uids[i];
    }
    
    // 设置是否是全部导出
    if(uids.length > 1){
    	$("#notAll").css("display","");
    	$("#all").css("display","none");
    }else{
    	$("#all").css("display","");
    	$("#notAll").css("display","none");
    }
    
    var loadi;
    //确认操作
    $('#dialogOkBtn').on('click', function(){
//    	top.layer.closeAll();
    	$.ajax({
            url:webPath + "account/export.do?randomTime=" + (new Date()).getTime() + uidUrl,
            type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            dataType:"json",
            //async:false,
            beforeSend:function(data){
            	loadi = top.layer.load('数据导出中…');
            },
            success:function(data){
            	top.layer.close(loadi);
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