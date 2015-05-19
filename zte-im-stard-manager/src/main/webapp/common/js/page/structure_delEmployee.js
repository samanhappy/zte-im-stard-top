define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var removeids=$("#removeids").val();
    var removenames=$("#removenames").val();
    
	var iframeId = parent.layer.getFrameIndex(window.name);
    window.parent.$("#iframeIdVal").val("xubox_iframe"+iframeId);
    
    //确定操作
    $('#dialogOkBtn').live('click', function(){
    	$.ajax({
    		url:webPath + "removeUser.do",
    		data:encodeURI("uids=" + removeids + "&unames=" + removenames),
            dataType:"json",
            type: 'post',
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