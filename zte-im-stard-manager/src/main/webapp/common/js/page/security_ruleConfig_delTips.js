define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    var webPath=$("#webPath").val();
    
    //确定操作
    $('#dialogOkBtn').on('click', function(){
    	$.ajax({
    		url:webPath + "json/login.json",
            dataType:"json",
            type: 'post',
            success:function(data){
            	if(data.result){
            		top.layer.closeAll();
                }else{
                	top.layer.msg(data.msg, 1, -1);
                }
            }
    	});
    });
    
    //取消操作
    $('#dialogCancelBtn').on('click', function(){
        top.layer.closeAll();
    });
});