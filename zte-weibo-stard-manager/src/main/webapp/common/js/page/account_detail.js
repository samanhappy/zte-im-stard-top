define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var uid = $("#uid").val();
    var loadi;
    loadData();
    function loadData(){
	    $.ajax({
	    	url:webPath + "account/getByUid.do?uid="+uid+"&randomTime=" + (new Date()).getTime(),
	        dataType:"json",
	        type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	        beforeSend:function(data){
            	loadi = top.layer.load('数据加载中…');
            },
	        success:function(data){
	        	top.layer.close(loadi);
	        	$("#name").text(data.data.name);
	        	$("#state").text(data.data.state);
	        	$("#ycwbs").text(data.data.ycwbs);
	        	$("#zfwbs").text(data.data.zfwbs);
	        	$("#cjqzs").text(data.data.cjqzs);
	        	$("#cyqzs").text(data.data.cyqzs);
	        	if("" != data.data.minipicurl){
	        		$("#minipicurl").attr("src",data.data.minipicurl);
	        	}
	        	
	        }
	    })
    
    }
});