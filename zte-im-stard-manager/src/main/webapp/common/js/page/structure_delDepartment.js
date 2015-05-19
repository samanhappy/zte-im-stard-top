define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    var webPath=$("#webPath").val();
    
    //确定操作
    $('#dialogOkBtn').on('click', function(){
    	$.ajax({
    		url:webPath + "delDept.do",
    		data:encodeURI("id="+ $("#id").val() + "&gname=" + $("#name").val()),
            type:"post",
            dataType:"json",
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            success:function(data){
            	if(data.result){
            		parent.location.reload();
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