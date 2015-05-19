define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var id = $("#id").val();
    if (id == '' || id == 'null') {
    	id = $("#idInSession").val();
    }
    var deptName = $("#deptName").val();
    
    loadPrivileges();
	var privileges = null;
	var allPrivileges = ["deptManage","userManage","userImport","userExport","userPrivManage","userAbleManage"];
	function loadPrivileges() {
		$.ajax({
			type : "POST",
			url : webPath + 'getPrivileges.do?t=' + (new Date()).getTime(),
			dataType : "json",
			success : function(data) {
				privileges = JSON.stringify(data);
				employeeList(load);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
    
    //查询服务状态
    $.ajax({
    	url:webPath + "checkLisence.do",
    	type:"get",
        dataType:"json",
        success:function(data){
        	if(data.remainDays){
        		$(".startDate").html(data.startDate);
        		$(".endDate").html(data.endDate);
        		if(data.remainDays < 0){//已过期
                    $(".tipsBox").addClass("expired").show();
                    $("#overdue").hide();
        		}else if(data.remainDays < 30){//将要过期
        			$(".tipsBox").show();
                    $("#expired").hide();
        		}
        	}
        }
    });
    //默认加载
    var sortStr="&sortStr=uid asc";
    var load = sortStr;
    
    //排序
    //工号排序
    $("#numSort a").live("click",function(){
    	$("#nameSort a").removeClass("asc").addClass("normal");
		$("#nameSort a").removeClass("desc").addClass("normal");
		$("#deptSort a").removeClass("asc").addClass("normal");
		$("#deptSort a").removeClass("desc").addClass("normal");
    	if($(this).hasClass("asc")){
    		$(this).removeClass("asc").addClass("desc");
    		sortStr="&sortStr=uid desc";
    		param = sortStr;
    	    employeeList(param);
    	}else if($(this).hasClass("desc")){
    		$(this).removeClass("desc").addClass("asc");
    		sortStr="&sortStr=uid asc";
    		param = sortStr;
    		employeeList(param);
    	}else{
    		$(this).removeClass("normal").addClass("asc");
    		sortStr="&sortStr=uid asc";
    		param = sortStr;
    		employeeList(param);
    	}
    });
    //姓名排序
    $("#nameSort a").live("click",function(){
    	$("#numSort a").removeClass("asc").addClass("normal");
		$("#numSort a").removeClass("desc").addClass("normal");
		$("#deptSort a").removeClass("asc").addClass("normal");
		$("#deptSort a").removeClass("desc").addClass("normal");
    	if($(this).hasClass("asc")){
    		$(this).removeClass("asc").addClass("desc");
    		sortStr="&sortStr=t9_pinyin desc";
    		param = sortStr;
    	    employeeList(param);
    	}else if($(this).hasClass("desc")){
    		$(this).removeClass("desc").addClass("asc");
    		sortStr="&sortStr=t9_pinyin asc";
    		param = sortStr;
    	    employeeList(param);
    	}else{
    		$(this).removeClass("normal").addClass("asc");
    		sortStr="&sortStr=t9_pinyin asc";
    		param = sortStr;
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
    		sortStr="&sortStr=ug.sequ desc";
    		param = sortStr;
    	    employeeList(param);
    	}else if($(this).hasClass("desc")){
    		$(this).removeClass("desc").addClass("asc");
    		sortStr="&sortStr=ug.sequ asc";
    		param = sortStr;
    	    employeeList(param);
    	}else{
    		$(this).removeClass("normal").addClass("asc");
    		sortStr="&sortStr=ug.sequ asc";
    		param = sortStr;
    	    employeeList(param);
    	}
    });
    
    //搜索
    $('#keyword').live('keypress', function(){
    	if(event.keyCode==13){
    		employeeList(sortStr);
    	}
    });
    
    //员工列表
    var loadi;
    function employeeList(param){
    	$.ajax({
            url:webPath + "groupMember.do?groupId=" + id + param + "&randomTime=" + (new Date()).getTime(),
            data:"keyword="+$("#keyword").val(),
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
                        var tpl = require('../../../common/tpl/structureList.tpl');//载入tpl模板
                        var template = Handlebars.compile(tpl);
                        var html = template(data);
                        $("#structureList").html(html);
                        //偶数行增加背景颜色
                        $("#structureList tr:odd").addClass("itemBg");
                        checkPrivileges(privileges, allPrivileges);
                    });
                });
                //分页插件
               if(data.page.pages > 0){
                    require.async(['pager/pager.css','pager/pager'],function(){
                        kkpager.generPageHtml({
                                pno : data.page.currentPage,//当前页码
                                total : data.page.pages,//总页码
                                totalRecords : data.page.total,//总数据条数
                                isShowFirstPageBtn  : false, 
                                isShowLastPageBtn   : false, 
                                isShowTotalPage     : false, 
                                isShowTotalRecords  : false, 
                                isGoPage            : false,
                                lang:{
                                    prePageText: '<i class="iconfont">&#xe60f;</i>',
                                    nextPageText: '<i class="iconfont">&#xe610;</i>'
                                },
                                mode:'click',//click模式匹配getHref 和 click
                                click:function(n,total,totalRecords){
                                    $.ajax({
                                        type:"post",
                                        data:"keyword="+$("#keyword").val(),
                                        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
                                        url:webPath + "groupMember.do?groupId=" + id + param + "&randomTime=" + (new Date()).getTime()+"&cPage="+n,
                                        dataType:"json",//这个必不可少，如果缺少，导致传回来的不是json格式
                                        beforeSend:function(data){
                                        	loadi = top.layer.load('列表加载中…');
                                        },
                                        success:function(json){
                                        	top.layer.close(loadi);
                                            require.async(['handlebars-v2.0.0/handlebars-v2.0.0','handlebars-v2.0.0/transFormatJson'],function(){
                                                var tpl = require('../../../common/tpl/structureList.tpl');//载入tpl模板
                                                var template = Handlebars.compile(tpl);
                                                var html    = template(json);
                                                $('#structureList').html(html);
                                                //偶数行增加背景颜色
                                                $("#structureList tr:odd").addClass("itemBg");
                                                checkPrivileges(privileges, allPrivileges);
                                            });
                                        }
                                    });
                                    this.selectPage(n); //处理完后可以手动条用selectPage进行页码选中切换
                                }
                        });
                    });
                }else{
                    $("#kkpager").html('暂无数据');
                }
            }
        });
    }
    
    var protectedPropNames = "";
    loadContactParam();
    function loadContactParam(){
    	$.ajax({
            url:webPath + "getContactParam.do",
            type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            dataType:"json",
            success:function(data){
            	if(data.result){
            		protectedPropNames = data.data.protectedPropNames;
                }else{
                	top.layer.msg(data.msg, 1, -1);
                }
            }
        });
    }
    
    
    //保护设置
    $('.userPrivManage').live('click', function(){
    	top.$.layer({
            type: 2,
            title: "保护设置<span>（当前受保护的成员属性：" + (protectedPropNames=="" ? "无" : protectedPropNames) + "，可至“系统管理-参数设置”处修改）</span>",
            area: ['720px', '404px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/structure/employeeSetting.jsp?id='+$(this).attr("data-id") + "&name="+$(this).attr("data-name") + "&post="+$(this).attr("data-post")}
        });
    });

    //重置密码
    $('#resetPwdBtn').live('click', function(){
        top.$.layer({
            type: 2,
            title: "重置密码",
            area: ['332px', '200px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/structure/resetPwd.jsp?id='+$(this).attr("data-id")+'&name='+$(this).attr("data-name")}
        });
    });
    
    //状态更新
    $('.userAbleManage').live('click', function(){
    	$.ajax({
    		url:webPath + "updateUser.do?id="+$(this).attr("data-id") + "&usable="+$(this).attr("data-usable") + "&t=" + (new Date()).getTime(),
            type:"get",
            dataType:"json",
            success:function(data){
            	if(data.result){
            		employeeList(load);
                }else{
                	top.layer.msg(data.msg, 1, -1);
                }
            }
    	});
    });
    
    //全选反选操作
    $("#checkAll").live("click",function(){
        if ($("#checkAll").is(':checked')) {
            $("#structureList input[type='checkbox']").each(function() {
                this.checked = true;
            });
        } else {
            $("#structureList input[type='checkbox']").each(function() {
                this.checked = false;
            });
        }
    });
    
    //添加员工
    $('#addEmployeesBtn').on('click', function(){
    	top.$.layer({
            type: 2,
            title: "添加员工",
            area: ['720px', '404px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/structure/addEmployee.jsp'}
        });
    });
    
    //编辑员工    
    $('#editEmployeesBtn').on('click', function(){
    	var checkedEle = $("#structureList input[type='checkbox']:checked");
    	if (checkedEle.length == 0) {
			top.layer.msg("必须选择一名员工", 1, -1);
		}  else if (checkedEle.length > 1) {
			top.layer.msg("只能选择一名员工", 1, -1);
		} else {
			top.$.layer({
	            type: 2,
	            title: "编辑员工",
	            area: ['720px', '404px'],
	            closeBtn: [0, true],
	            border: [0],
	            shade: [0.7, '#000'],
	            shadeClose: false,
	            fadeIn: 300,
	            move: false,
	            fix: true,
	            iframe: {src: 'module/structure/editEmployee.jsp?id=' + checkedEle[0].value}
	        });
		}
    });
    
    //删除员工
    $('#delEmployeesBtn').on('click', function(){
    	
    	var checkedEle = $("#structureList input[type='checkbox']:checked");
    	if (checkedEle.length == 0) {
			top.layer.msg("必须选择一名员工", 1, -1);
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
	            iframe: {src: 'module/structure/delEmployee.jsp?removeids=' + removeids + "&removelen=" + checkedEle.length + "&removenames=" + removenames}
	        });
		}
    });
    
    //数据导入
    $('#importEmployeesBtn').on('click', function(){
    	top.$.layer({
            type: 2,
            title: "数据导入",
            area: ['600px', '356px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/structure/importEmployee.jsp'}
        });
    });
    
    //数据导出
    $('#exportEmployeesBtn').on('click', function(){
    	var checkedEle = $("#structureList input[type='checkbox']:checked");
    	var uids = "";
    	var desc = "确认导出公司全部用户吗";
    	if (checkedEle.length > 0) {
			for ( var i = 0; i < checkedEle.length; i++) {
				uids += checkedEle[i].value;
				if (i < checkedEle.length - 1){
					uids += ',';	
				}
			}
    		desc = "确认导出选中"+checkedEle.length+"条用户吗";
    	} else if (id != null && id != '' && id != 'null') {
    		desc = "确认导出"+deptName+"全部用户吗";
    	} else {
    		id = "";
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
            iframe: {src: 'module/structure/exportEmployee.jsp?deptId=' + id + "&checkedLen=" + checkedEle.length + "&desc=" + desc + "&uids=" + uids}
        });
    });
    
	
	function checkPrivileges(privileges, allPrivileges) {
		if (privileges == null) {
			for (var i in allPrivileges) {
				$("." + allPrivileges[i]).unbind('click');
				$("." + allPrivileges[i]).die('click');
				$("." + allPrivileges[i]).attr('href','javascript:;');
				$("." + allPrivileges[i]).css('color','gray');
				$("." + allPrivileges[i]).css('cursor','text');
			}
		} else if (privileges != '\"all\"') {
			for (var i in allPrivileges) {
				if (privileges.indexOf(allPrivileges[i]) == -1) {
					$("." + allPrivileges[i]).unbind('click');
					$("." + allPrivileges[i]).die('click');
					$("." + allPrivileges[i]).attr('href','javascript:;');
					$("." + allPrivileges[i]).css('color','gray');
					$("." + allPrivileges[i]).css('cursor','text');
				}
			}
		}
	}
});