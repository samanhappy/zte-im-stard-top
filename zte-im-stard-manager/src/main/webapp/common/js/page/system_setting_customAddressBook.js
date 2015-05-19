define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    var loadi;

    //载入字段
    $.ajax({
        url:webPath + "loadUserDefineParam.do",
        type:"get",
        dataType:"json",
        beforeSend:function(data){
        	loadi = top.layer.load('列表加载中…');
        },
        success:function(data){
        	top.layer.close(loadi);
            require.async('handlebars-v2.0.0/handlebars-v2.0.0',function(){
                require.async('handlebars-v2.0.0/transFormatJson',function(){
                    var tpl = require('../../../common/tpl/customAddressBookList.tpl');//载入tpl模板
                    var template = Handlebars.compile(tpl);
                    var html = template(data);
                    $("#customAddressBookList").html(html);
                    checkPrivileges(privileges, allPrivileges);
                });
            });
        }
    });
    
    
    //新增字段
    $('#addFieldBtn').on('click', function(){
    	top.$.layer({
            type: 2,
            title: "新增字段",
            area: ['600px', '356px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/system/setting/addField.jsp'}
        });
    });
    
    //编辑
    $('#editBtn').live('click', function(){
    	var editId = $(this).attr("data-val");
    	var paramName = $(this).attr("data-name");
    	var paramType = $(this).attr("data-type");
    	top.$.layer({
            type: 2,
            title: "编辑字段",
            area: ['600px', '356px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/system/setting/editField.jsp?id='+ editId + '&paramName=' + paramName + '&paramType=' + paramType}
        });
    });
    
    //删除
    $('#delBtn').live('click', function(){
    	var delId = $(this).attr("data-val");
    	var paramName = $(this).attr("data-name");
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
            iframe: {src: 'module/system/setting/delTips.jsp?id=' + delId + '&paramName=' + paramName}
        });
    });
    
	
	loadPrivileges();
	var privileges = null;
	var allPrivileges = ["defineParam"];
	function loadPrivileges() {
		$.ajax({
			type : "POST",
			url : webPath + 'getPrivileges.do?t=' + (new Date()).getTime(),
			dataType : "json",
			success : function(data) {
				privileges = JSON.stringify(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	
	function checkPrivileges(privileges, allPrivileges) {
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
	}
    
});