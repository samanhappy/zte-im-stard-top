define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    require('jquery-validation-1.13.1/jquery.validate.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var id = $("#id").val();
    var keyword = $("#keyword").val();
    
    var index = parent.layer.getFrameIndex(window.name);
    //获取iframeID
    var iframeId = window.parent.$("#iframeIdVal").val();
    
    $(window.parent.frames['mainiframe'].document);
    
    //默认加载
    var sortStr="?sortStr=um.t9_pinyin asc";
    var load = sortStr+"&keyword="+keyword;
    employeeList(load);
    
    //排序
    //姓名排序
    $("#nameSort a").live("click",function(){
    	$("#numSort a").removeClass("asc").addClass("normal");
		$("#numSort a").removeClass("desc").addClass("normal");
		$("#deptSort a").removeClass("asc").addClass("normal");
		$("#deptSort a").removeClass("desc").addClass("normal");
    	if($(this).hasClass("asc")){
    		$(this).removeClass("asc").addClass("desc");
    		keyword = $("#keyword").val();
    		sortStr="?sortStr=um.t9_pinyin desc";
    		param = sortStr+"&keyword="+keyword;
    	    employeeList(param);
    	}else if($(this).hasClass("desc")){
    		$(this).removeClass("desc").addClass("asc");
    		keyword = $("#keyword").val();
    		sortStr="?sortStr=um.t9_pinyin asc";
    		param = sortStr+"&keyword="+keyword;
    	    employeeList(param);
    	}else{
    		$(this).removeClass("normal").addClass("asc");
    		keyword = $("#keyword").val();
    		sortStr="?sortStr=um.t9_pinyin asc";
    		param = sortStr+"&keyword="+keyword;
    	    employeeList(param);
    	}
    });
    
    //工号排序
    $("#numSort a").live("click",function(){
    	$("#nameSort a").removeClass("asc").addClass("normal");
		$("#nameSort a").removeClass("desc").addClass("normal");
		$("#deptSort a").removeClass("asc").addClass("normal");
		$("#deptSort a").removeClass("desc").addClass("normal");
    	if($(this).hasClass("asc")){
    		$(this).removeClass("asc").addClass("desc");
    		keyword = $("#keyword").val();
    		sortStr="?sortStr=um.uid desc";
    		param = sortStr+"&keyword="+keyword;
    	    employeeList(param);
    	}else if($(this).hasClass("desc")){
    		$(this).removeClass("desc").addClass("asc");
    		keyword = $("#keyword").val();
    		sortStr="?sortStr=um.uid asc";
    		param = sortStr+"&keyword="+keyword;
    		employeeList(param);
    	}else{
    		$(this).removeClass("normal").addClass("asc");
    		keyword = $("#keyword").val();
    		sortStr="?sortStr=um.uid asc";
    		param = sortStr+"&keyword="+keyword;
    		employeeList(param);
    	}
    });
    
    //部门排序
    $("#deptSort a").live("click",function(){
    	$("#numSort a").removeClass("asc").addClass("normal");
		$("#numSort a").removeClass("desc").addClass("normal");
		$("#nameSort a").removeClass("asc").addClass("normal");
		$("#nameSort a").removeClass("desc").addClass("normal");
    	if($(this).hasClass("asc")){
    		$(this).removeClass("asc").addClass("desc");
    		keyword = $("#keyword").val();
    		sortStr="?sortStr=ug.sequ desc";
    		param = sortStr+"&keyword="+keyword;
    	    employeeList(param);
    	}else if($(this).hasClass("desc")){
    		$(this).removeClass("desc").addClass("asc");
    		keyword = $("#keyword").val();
    		sortStr="?sortStr=ug.sequ asc";
    		param = sortStr+"&keyword="+keyword;
    	    employeeList(param);
    	}else{
    		$(this).removeClass("normal").addClass("asc");
    		keyword = $("#keyword").val();
    		sortStr="?sortStr=ug.sequ asc";
    		param = sortStr+"&keyword="+keyword;
    	    employeeList(param);
    	}
    });
    
    //搜索
    $("#keyword").bind('input propertychange', function() {
		var key = $.trim($("#keyword").val());
		$("#structureSelectPersonnelList input").each(function() {
			if (key == '' || this.name.indexOf(key) != -1) {
				$(this).parent().parent().css("display", "");
			} else {
				$(this).parent().parent().css("display", "none");
			}
		});
	});
    
    //员工列表
    var loadi;
    function employeeList(param){
    	$.ajax({
            url:webPath + "listUser.do" + param,
            type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            dataType:"json",
            beforeSend:function(data){
            	loadi = top.layer.load('列表加载中…');
            },
            success:function(data){
            	top.layer.close(loadi);
                require.async('handlebars-v2.0.0/handlebars-v2.0.0',function(){
                    require.async('handlebars-v2.0.0/transFormatJson',function(){
                        var tpl = require('../../../common/tpl/structureSelectPersonnel.tpl');//载入tpl模板
                        var template = Handlebars.compile(tpl);
                        var html = template(data);
                        $("#structureSelectPersonnelList").html(html);
                        //偶数行增加背景颜色
                        $("#structureSelectPersonnelList tr:odd").addClass("itemBg");
                    });
                });
            }
        });
    }

    //确定
    $('#dialogDeptConfirm').on('click', function(){
    	var inputList = $("#structureSelectPersonnelList input:checked");
		var perms = '';
		var divPerms = '';
		if (inputList.length > 0) {
			for ( var i = 0; i < inputList.length; i++) {
				perms += inputList[i].name + '；';
				divPerms += "<input type='hidden' name='userList[" + i + "].id'" + "' value='" + inputList[i].value + "' />";
				divPerms += "<input type='hidden' name='userList[" + i + "].name'" + "' value='" + inputList[i].name + "' />";
			}
		}
		if (perms.length > 0) {
			perms = perms.substring(0, perms.length - 1);
		}
		$(window.parent.frames['mainiframe'].frames['pageFrame'].document).find("#roleUsers").val(perms);
		$(window.parent.frames['mainiframe'].frames['pageFrame'].document).find("#roleUsersDiv").html(divPerms);
        top.layer.close(index);
    }); 
    
    //取消操作
    $('#dialogDeptCancel').on('click', function(){
        parent.layer.close(index);
    });

});