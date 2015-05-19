define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    clientList();
    
    function clientList(){
        $.ajax({
            url:webPath + "clientList.do?randomTime="+(new Date()).getTime(),
            type:"get",
            dataType:"json",
            success:function(data){
                require.async('handlebars-v2.0.0/handlebars-v2.0.0',function(){
                    require.async('handlebars-v2.0.0/transFormatJson',function(){
                        var tpl = require('../../../common/tpl/clientList.tpl');//载入tpl模板
                        var template = Handlebars.compile(tpl);
                        var html = template(data);
                        $("#clientList").html(html);
                        //偶数行增加背景颜色
                        $("#clientList tr:odd").addClass("itemBg");
                    });
                });
            }
        });
    }
    
    //激活暂停
    $("#clientId").live("click",function(){
        var idVal = $(this).attr("data-id");
        var isActive = $(this).attr("data-isActive");
        $.ajax({
            url:webPath + "setClientActive.do?id="+idVal + "&isActive=" + isActive,
            type:"get",
            dataType:"json",
            success:function(data){
                if(data.result){
                    clientList();
                }else{
                    top.layer.msg(data.msg);
                }
            }
        });
        
    });
    
    //新增
    $('#addClientBtn').on('click', function(){
    	top.$.layer({
            type: 2,
            title: "新增",
            area: ['720px', '404px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/system/clientManagement/addClient.jsp'}
        });
    });
    
    //修改客户端
    $('#editClientBtn').on('click', function(){
    	var checkedEle = $("#clientList input:checked");
    	if (checkedEle.length == 0) {
			top.layer.msg("必须选择一个客户端", 1, -1);
		} else if (checkedEle.length > 1) {
			top.layer.msg("只能选择一个客户端", 1, -1);
		} else {
			var id = checkedEle[0].value;
			top.$.layer({
	            type: 2,
	            title: "编辑",
	            area: ['720px', '404px'],
	            closeBtn: [0, true],
	            border: [0],
	            shade: [0.7, '#000'],
	            shadeClose: false,
	            fadeIn: 300,
	            move: false,
	            fix: true,
	            iframe: {src: 'module/system/clientManagement/editClient.jsp?id=' + id}
	        });
		}
    });
    
    //删除客户端
    $('#delClientBtn').on('click', function(){
    	var checkedEle = $("input:checked");
    	if (checkedEle.length == 0) {
			top.layer.msg("至少选择一个客户端", 1, -1);
		} else {
			var removeids = "";
			var removenames = "";
			for ( var i = 0; i < checkedEle.length; i++) {
				removeids += checkedEle[i].value;
				removenames += checkedEle[i].name;
				if (i < checkedEle.length - 1){
					removeids += ',';	
					removenames += ',';	
				}
			}
			top.$.layer({
	            type: 2,
	            title: "提醒",
	            area: ['332px', '200px'],
	            closeBtn: [0, true],
	            border: [0],
	            shade: [0.7, '#000'],
	            shadeClose: false,
	            fadeIn: 300,
	            move: false,
	            fix: true,
	            iframe: {src: 'module/system/clientManagement/delTips.jsp?removeids=' + removeids + "&removenames=" + removenames}
	        });
		}
    });
    
    //全选反选操作
    $("#checkAll").live("click",function(){
        if ($("#checkAll").is(':checked')) {
            $("#clientList input[type='checkbox']").each(function() {
                this.checked = true;
            });
        } else {
            $("#clientList input[type='checkbox']").each(function() {
                this.checked = false;
            });
        }
    });

});