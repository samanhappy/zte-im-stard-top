define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    //设置选中的用户个数
    var num = $("#num").val();
    $("#numVal").val(num);
    
    var loadi;
    //确认操作
    $('#dialogOkBtn').on('click', function(){
    	var accountSettingWrap = $(".accountSettingWrap input[type='checkbox']:checked");
    	if(accountSettingWrap.length < 1){
    		alert("必须选择一个权限");
    	}else{
    		var uid = $("#uid").val();
    		var uids = uid.split(",");
    		var uidUrl = "";
    		for(var i = 0 ; i < uids.length-1;i++){
    			uidUrl += "&uid="+uids[i];
    		}
    		$.ajax({
    			url:webPath + "account/setPermission.do?randomTime=" + (new Date()).getTime() + uidUrl,
    			dataType:"json",//这个必不可少，如果缺少，导致传回来的不是json格式
    			type: 'post',
    			//async:false,
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
                		top.layer.msg('操作成功', 1, -1);
                	}
                	//刷新列表页面并关闭窗口
                	setTimeout(function(){
                		//刷新列表页面并关闭窗口
                    	//top.frames[0].location.reload(); 
                    	top.layer.closeAll();
                	}, 500);
    			}
    		});
    	}
    });
    
    //取消操作
    $('#dialogCancelBtn').on('click', function(){
        top.layer.closeAll();
    });
});