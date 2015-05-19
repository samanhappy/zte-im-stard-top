define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var id=$("#id").val();
    
	var iframeId = parent.layer.getFrameIndex(window.name);
    window.parent.$("#iframeIdVal").val("xubox_iframe"+iframeId);
    
    //确定操作
    $('#dialogOkBtn').on('click', function(){
    	$.ajax({
    		url:webPath + "resetUserPwd.do?id=" + id,
            type:"get",
            dataType:"json",
            success:function(data){
            	if(data.result){
            		top.layer.closeAll();
                }else{
                	top.layer.msg(data.msg, 1, -1);
                }
            }
    	})
    });
    
	//取消操作
    $('#dialogCancelBtn').on('click', function(){
        top.layer.closeAll();
    });
});