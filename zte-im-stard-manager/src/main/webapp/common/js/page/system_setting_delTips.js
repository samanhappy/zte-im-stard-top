define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    var webPath=$("#webPath").val();
    
    var id = $("#id").val();
    var paramName = $("#paramName").val();
    
    //确定操作
    $('#dialogOkBtn').on('click', function(){
    	$.ajax({
    		url:webPath + "removeParam.do?id=" + id + "&paramName=" + paramName,
            dataType:"json",
            type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            success:function(data){
            	if(data.result){
            		window.parent.frames['mainiframe'].location.reload();
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