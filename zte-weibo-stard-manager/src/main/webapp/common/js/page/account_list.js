define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    //默认加载
//    var load = "?name="+$("#name").val()+"&state="+$("#state a.selected").data("val");
    accountList();
    
    //权限设置
    $('#setBtn').on('click', function(){
    	var checkedEle = $("#accountList input[type='checkbox']:checked");
    	if (checkedEle.length == 0) {
    		_tipSelectOne();
		} else {
			var uid = '&uid=';
			for(var i = 0 ;i<checkedEle.length;i++){
				uid +=$(checkedEle[i]).val()+",";
			}
			top.$.layer({
	            type: 2,
	            title: "&nbsp;&nbsp;&nbsp;&nbsp;提醒",
	            area: ['260px', '146px'],
	            closeBtn: [0, true],
	            border: [1, 1, '#c2c2c2'],
	            shade: [0.1, '#000'],
	            shadeClose: false,
	            fadeIn: 300,
	            move: false,
	            fix: true,
	            iframe: {src: 'module/account/setAllAttention.jsp?num='+checkedEle.length+uid}
	        });
		}
    });
    //取消默认全员关注
    $('#cancleSetBtn').on('click', function(){
    	var checkedEle = $("#accountList input[type='checkbox']:checked");
    	if (checkedEle.length == 0) {
    		_tipSelectOne();
    	} else {
    		var uid = '&uid=';
    		for(var i = 0 ;i<checkedEle.length;i++){
    			uid +=$(checkedEle[i]).val()+",";
    		}
    		top.$.layer({
    			type: 2,
    			title: "&nbsp;&nbsp;&nbsp;&nbsp;提醒",
    			area: ['260px', '146px'],
    			closeBtn: [0, true],
    			border: [1, 1, '#c2c2c2'],
    			shade: [0.1, '#000'],
    			shadeClose: false,
    			fadeIn: 300,
    			move: false,
    			fix: true,
    			iframe: {src: 'module/account/delAllAttention.jsp?num='+checkedEle.length+uid}
    		});
    	}
    });
    //设置同部门人员相互关注
    $('#setDeptBtn').on('click', function(){
		top.$.layer({
			type: 2,
			title: "&nbsp;&nbsp;&nbsp;&nbsp;提醒",
			area: ['260px', '120px'],
			closeBtn: [0, true],
			border: [1, 1, '#c2c2c2'],
			shade: [0.1, '#000'],
			shadeClose: false,
			fadeIn: 300,
			move: false,
			fix: true,
			iframe: {src: 'module/account/setAttentionDept.jsp'}
		});
    });
    //取消同部门人员相互关注
    $('#cancleDeptBtn').on('click', function(){
    	top.$.layer({
    		type: 2,
    		title: "&nbsp;&nbsp;&nbsp;&nbsp;提醒",
    		area: ['260px', '120px'],
    		closeBtn: [0, true],
    		border: [1, 1, '#c2c2c2'],
    		shade: [0.1, '#000'],
    		shadeClose: false,
    		fadeIn: 300,
    		move: false,
    		fix: true,
    		iframe: {src: 'module/account/delAttentionDept.jsp'}
    	});
    });

    //启用
    $('#openBtn').on('click', function(){
    	var checkedEle = $("#accountList input[type='checkbox']:checked");
    	if (checkedEle.length == 0) {
    		_tipSelectOne();
		} else {
			var uid = '&uid=';
			for(var i = 0 ;i<checkedEle.length;i++){
				uid +=$(checkedEle[i]).val()+",";
			}
			top.$.layer({
	            type: 2,
	            title: "&nbsp;&nbsp;&nbsp;&nbsp;提醒",
	            area: ['260px', '120px'],
	            closeBtn: [0, true],
	            border: [1, 1, '#c2c2c2'],
	            shade: [0.1, '#000'],
	            shadeClose: false,
	            fadeIn: 300,
	            move: false,
	            fix: true,
	            iframe: {src: 'module/account/open.jsp?num='+checkedEle.length+uid}
	        });
		}
    });
    
    //停用
    $('#stopBtn').on('click', function(){
    	var checkedEle = $("#accountList input[type='checkbox']:checked");
    	if (checkedEle.length == 0) {
    		_tipSelectOne();
		} else {
			var uid = '&uid=';
			for(var i = 0 ;i<checkedEle.length;i++){
				uid +=$(checkedEle[i]).val()+",";
			}
			top.$.layer({
	            type: 2,
	            title: "&nbsp;&nbsp;&nbsp;&nbsp;提醒",
	            area: ['260px', '208px'],
	            closeBtn: [0, true],
	            border: [1, 1, '#c2c2c2'],
	            shade: [0.1, '#000'],
	            shadeClose: false,
	            fadeIn: 300,
	            move: false,
	            fix: true,
	            iframe: {src: 'module/account/stop.jsp?num='+checkedEle.length+uid}
	        });
		}
    });
    
    //导入
    $('#importBtn').on('click', function(){
    	top.$.layer({
            type: 2,
            title: "数据导入",
            area: ['450px', '268px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/account/import.jsp'}
        });
    });
    	
    //导出
    $('#exportBtn').on('click', function(){
    	var checkedEle = $("#accountList input[type='checkbox']:checked");
    	var uid = '&uid=';
		for(var i = 0 ;i<checkedEle.length;i++){
			uid +=$(checkedEle[i]).val()+",";
		}
    	top.$.layer({
            type: 2,
            title: "&nbsp;&nbsp;&nbsp;&nbsp;提醒",
            area: ['260px', '120px'],
            closeBtn: [0, true],
            border: [1, 1, '#c2c2c2'],
            shade: [0.1, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/account/export.jsp?num='+checkedEle.length+uid}
        });
    });
    
    $("#initDataBtn").on("click",function(){
    	top.$.layer({
            type: 2,
            title: "&nbsp;&nbsp;&nbsp;&nbsp;提醒",
            area: ['265px', '146px'],
            closeBtn: [0, true],
            border: [1, 1, '#c2c2c2'],
            shade: [0.1, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/account/initData.jsp'}
        });
    });
    
    //搜索
    $("#name").live('keypress', function(event){
    	if(event.keyCode==13){
    		searchFun();
    	}
    });
    $(".searchBtn").on('click', function(){
    	searchFun();
    })
   
    
    //账号列表
    var loadi;
    function accountList(){
	    $.ajax({
	    	url:webPath + "account/list.do?randomTime=" + (new Date()).getTime(),
	        dataType:"json",
	        type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            data:{
            	name:$("#name").val(),
            	state:$("#state a.selected").data("val"),
            	cPage:$("#cPage").val()
            },
	        beforeSend:function(data){
            	loadi = top.layer.load('列表加载中…');
            },
            error:function(data){
            	top.layer.close(loadi);
            	top.layer.msg("加载数据发生错误！",1,-1);
            },
	        success:function(data){
	        	//debugger;
	        	top.layer.close(loadi);
	            require.async('handlebars-v2.0.0/handlebars-v2.0.0',function(){
	                require.async('handlebars-v2.0.0/transFormatJson',function(){
	                    var tpl = require('../../../common/tpl/accountList.tpl');//载入tpl模板
	                    var template = Handlebars.compile(tpl);
	                    var html = template(data);
	                    $("#accountList").html(html);
	                    //偶数行增加背景颜色
	                    $("#accountList tr:odd").addClass("itemBg");
	                })
	            });
	            //分页插件
	           if(data.page.countPage > 0){
	                require.async(['pager/pager.css','pager/pager'],function(){
	                    kkpager.generPageHtml({
	                            pno : data.page.currentpage,//当前页码
	                            total : data.page.countPage,//总页码
	                            totalRecords : data.page.countdate,//总数据条数
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
	                                    url:webPath + "account/list.do?&randomTime=" + (new Date()).getTime(),
	                                    dataType:"json",//这个必不可少，如果缺少，导致传回来的不是json格式
	                                    type: 'post',
	                                    contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	                                    data:{cPage:n,name:$("#name").val(),state:$("#state a.selected").data("val")},
	                                    beforeSend:function(data){
                                        	loadi = top.layer.load('列表加载中…');
                                        },
                                        error:function(data){
                                        	top.layer.close(loadi);
                                        	top.layer.msg("加载数据发生错误！",1,-1);
                                        },
	                                    success:function(json){
	                                    	top.layer.close(loadi);
	                                        require.async(['handlebars-v2.0.0/handlebars-v2.0.0','handlebars-v2.0.0/transFormatJson'],function(){
	                                            var tpl = require('../../../common/tpl/accountList.tpl');//载入tpl模板
	                                            var template = Handlebars.compile(tpl);
	                                            var html    = template(json);
	                                            $('#accountList').html(html);
	                                            //偶数行增加背景颜色
	                                            $("#accountList tr:odd").addClass("itemBg");
	                                        });
	                                    }
	                                });
	                                $("#cPage").val(n);
	                                this.selectPage(n); //处理完后可以手动条用selectPage进行页码选中切换
	                            }
	                    });
	                });
	                var cPage = $("#cPage").val();
	                if(cPage > data.page.countPage){
	                	cPage = data.page.countPage;
	                	kkpager.click(cPage,data.page.countPage,data.page.countdate);
	                }
	                $("#cPage").val(cPage);
//	                kkpager.selectPage(cPage);
	            }else{
	                $("#kkpager").html('暂无数据');
	            }
	        }
	    })
    }
    

    //全选反选操作
    $("#checkAll").live("click",function(){
        if ($("#checkAll").is(':checked')) {
            $("#accountList input[type='checkbox']").each(function() {
                this.checked = true;
            });
        } else {
            $("#accountList input[type='checkbox']").each(function() {
                this.checked = false;
            });
        }
    })
    
    //详情显示
    $("#showDetail").live("click",function(){
    	var uid = $(this).attr("data-val");
    	top.$.layer({
            type: 2,
            title: "详情展示",
            area: ['260px', '208px'],
            closeBtn: [0, true],
            border: [1, 1, '#c2c2c2'],
            shade: [0.1, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/account/detail.jsp?uid='+uid}
        });
    })
    
    //状态
    $("#state a").click(function(){
		$(this).addClass("selected").siblings().removeClass("selected");
//		var param = "?name="+$("#name").val()+"&state="+$(this).data("val");
		accountList();
	});
    
    function searchFun(){
//    	var param = "?name="+$("#name").val()+"&state="+$("#state a.selected").data("val");
    	$("#cPage").val("1");
        accountList();
    }
    
    function _tipSelectOne(){
    	top.$.layer({
            type: 1,
            closeBtn:false,
            title:false,
            area: ['120px', '72px'],
            shade: [0.1, '#000'],
            border: [1, 1, '#c2c2c2'],
            time:1,
            page: {
            	html: '<div class="dialogTips"><p>必须选择一名用户</p></div>'
            }
        });
    }
    
    exports.search = accountList;
});

