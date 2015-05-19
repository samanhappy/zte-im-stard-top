define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var groupId = $("#groupId").val();
    var loadi;
    loadData();
    function loadData(){
	    $.ajax({
	    	url:webPath + "circle/getByGroupId.do?groupId="+groupId+"&randomTime=" + (new Date()).getTime(),
	        dataType:"json",
	        type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	        beforeSend:function(data){
            	loadi = top.layer.load('数据加载中…');
            },
	        success:function(data){
	        	top.layer.close(loadi);
	        	$("#name").text(data.data.groupName);
	        	$("#state").text(data.data.state);
	        	$("#qzcys").text(data.data.qzcys);
	        	$("#qzwbs").text(data.data.qzwbs);
	        	$("#groupIntroduction").text(data.data.groupIntroduction);
	        	if("" != data.data.minipicurl){
	        		$("#minipicurl").attr("src",data.data.imgUrl);
	        	}
	        	if(data.data.groupStatus == 1){
	        		$("#state").text("已停用");
	        	}else{
	        		$("#state").text("已启用");
	        	}
	        	
	        }
	    })
    
    }
});