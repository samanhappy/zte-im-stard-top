define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var deptId=$("#deptId").val();
    var ids=$("#ids").val();
   
	var iframeId = parent.layer.getFrameIndex(window.name);
    window.parent.$("#iframeIdVal").val("xubox_iframe"+iframeId);
    
    
    //确定操作
    $('#dialogOkBtn').on('click', function(){
    	$.ajax({
    		url:webPath + "exportChat.do?ids=" + ids,
    		type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            dataType:"json",
            success:function(data){
            	if(data.result){
            		window.location.href = data.fileUrl;
            		setTimeout(function(){
            			 top.layer.closeAll();
            		},500);
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