define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    require('layer-v1.8.5/layer.min');
    require('layer-v1.8.5/skin/layer.css');
    
    //获取网站根路径
    var webPath=$("#webPath").val();

	$(".head").click(function(){
		$(".head").removeClass("selected");
		$(this).addClass("selected");
		$(".menu ul li a").removeClass("current");
		$(".menu ul").hide();
		$(this).next().slideDown().show();
	});

	$(".menu ul li a").click(function(){
		$(".menu ul li a").removeClass("current");
		$(this).addClass("current");
	});
	
	
	//左侧收缩效果
	initLayout();   
	$(window).resize(function(){   
		initLayout();   
	});
	function initLayout() {   
		var leftMenuHeight =$("#leftMenu").height();
		$(".leftOpt").css("top",leftMenuHeight/2+46);
	}
	$(".leftClose").live("click",function(){
		$("#leftMenu").hide();
		$("#main").css("left","0");
		$(this).removeClass("leftClose").addClass("leftOpen");
	});

	$(".leftOpen").live("click",function(){
		$("#leftMenu").show();
		$("#main").css("left","121px");
		$(this).removeClass("leftOpen").addClass("leftClose");
	});
	
	//加载企业元素
	$.ajax({
		type : "POST",
		url : webPath + 'getTenantCustomization.do',
		dataType : "json",
		success : function(data) {
			if (data.result) {
				if (data.data) {
					if (data.data.serverLogo && data.data.serverLogo != '') {
						$("#title").css('background',
								'url(' + data.data.serverLogo + ') no-repeat');
					}
					if (data.data.serverManagerName != '') {
						$("#title").html(data.data.serverManagerName);
					}
					if (data.data.serverCopyright != '') {
						$("#footer").html(data.data.serverCopyright);
					}
				}
			} else {
				top.layer.msg(data.msg, 1, -1);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.layer.msg("数据操作出错", 1, -1);
		}
	});
	
	
	// 加载角色权限
	loadPrivileges();
	var privileges = null;
	var allPrivileges = ["updateCompInfo","licenseManage","clientManage","roleManage"];
	function loadPrivileges() {
		$.ajax({
			type : "POST",
			url : webPath + 'getPrivileges.do?t=' + (new Date()).getTime(),
			dataType : "json",
			success : function(data) {
				privileges = JSON.stringify(data);
			},
			complete : function() {
				if (privileges == null) {
					for (var i in allPrivileges) {
						$("." + allPrivileges[i]).css('display', 'none');
					}
				} else if (privileges != '\"all\"') {
					for (var i in allPrivileges) {
						if (privileges.indexOf(allPrivileges[i]) == -1) {
							$("." + allPrivileges[i]).css('display', 'none');
						}
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	

});