define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
   
   	//license信息
    $("#infoBtn").click(function(){
    	$(".newsBar a").removeClass("selected");
    	$(this).addClass("selected");
    	$(".licenseInfo").show();
    	$(".licenseUpdate").hide();
    })

    //更新license
    $("#updateBtn").click(function(){
    	$(".newsBar a").removeClass("selected");
    	$(this).addClass("selected");
    	$(".licenseInfo").hide();
    	$(".licenseUpdate").show();
    })
});