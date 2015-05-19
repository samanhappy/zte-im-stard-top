define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    require('layer-v1.8.5/layer.min');
    require('layer-v1.8.5/skin/layer.css');

    //左侧导航
	$(".normal").click(function(){
		$(".normal").removeClass("selected");
		$(this).addClass("selected");
	})

	//左侧收缩效果
	initLayout();   
	$(window).resize(function(){   
		initLayout();   
	});
	function initLayout() {   
		var leftMenuHeight =$("#leftMenu").height();
		$(".leftOpt").css("top",leftMenuHeight/2+46);
	}
	//收缩
	$(".leftClose").live("click",function(){
		$("#leftMenu").hide();
		$("#leftMinMenu").show();
		$("#main").css("left","37px");
		$(this).removeClass("leftClose").addClass("leftOpen");
	});
	//展开
	$(".leftOpen").live("click",function(){
		$("#leftMenu").show();
		$("#leftMinMenu").hide();
		$("#main").css("left","121px");
		$(this).removeClass("leftOpen").addClass("leftClose");
	});

});