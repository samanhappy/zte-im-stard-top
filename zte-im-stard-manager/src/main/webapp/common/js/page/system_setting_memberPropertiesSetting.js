define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    //载入列表
    memberPropertiesSettingList();
    function memberPropertiesSettingList(){
        $.ajax({
            url:webPath + "json/memberPropertiesSettingList.json",
            type:"get",
            dataType:"json",
            beforeSend:function(data){
            	loadi = top.layer.load('列表加载中…');
            },
            success:function(data){
            	top.layer.close(loadi);
                require.async('handlebars-v2.0.0/handlebars-v2.0.0',function(){
                    require.async('handlebars-v2.0.0/transFormatJson',function(){
                        var tpl = require('../../../common/tpl/memberPropertiesSettingList.tpl');//载入tpl模板
                        var template = Handlebars.compile(tpl);
                        var html = template(data);
                        $("#memberPropertiesSettingList").html(html);
                        
                        // 默认选中结果
                        var protectedPropVals = $("#protectedPropVals").val();
                        if (protectedPropVals) {
                        	var ids = protectedPropVals.split(',');
                        	if (ids && ids.length > 0) {
                        		for (var i = 0; i < ids.length; i++) {
                        			$("input[value=" + ids[i] + "]").attr(
                    						"checked", 'checked');
                        		}
                        	}
                        }
                    });
                });
            }
        });
    }
    
    // 保存    
	$("#dialogOkBtn").on('click', function() {
		var propList = $("input:checked");
		var props = '';
		var names = '';
		if (propList.length > 0) {
			for ( var i = 0; i < propList.length; i++) {
				props += propList[i].value + ',';
				names += propList[i].name + '、';
			}
		}
		if (props.length > 0) {
			props = props.substring(0, props.length - 1);
		}
		if (names.length > 0) {
			names = names.substring(0, names.length - 1);
		}
		
		$(window.parent.frames['mainiframe'].document).find("#protectedPropSpan").html(names);
        $(window.parent.frames['mainiframe'].document).find("#protectedPropNames").val(names);
        $(window.parent.frames['mainiframe'].document).find("#protectedPropVals").val(props);
		top.layer.closeAll();
	});
    
    //取消操作
    $('#dialogCancelBtn').on('click', function(){
        top.layer.closeAll();
    });
});