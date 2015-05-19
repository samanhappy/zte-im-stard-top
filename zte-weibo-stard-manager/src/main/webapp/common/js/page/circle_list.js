define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    //默认加载
//    var load = "?name="+$("#name").val()+"&circleType="+$("#circleType a.selected").data("val")+"&circleState="+$("#circleState a.selected").data("val");
    circleList();

    //启用
    $('#openBtn').on('click', function(){
    	var checkedEle = $("#circleList input[type='checkbox']:checked");
    	if (checkedEle.length == 0) {
    		top.$.layer({
	            type: 1,
	            title: false,
	            area: ['120px', '72px'],
	            closeBtn: [0],
	            border: [1, 1, '#c2c2c2'],
	            shade: [0.1, '#000'],
	            shadeClose: false,
	            fadeIn: 300,
	            fix: true,
	            time:1,
	            page: {
	            	html: '<div class="dialogTips"><p>必须选择一个圈子</p></div>'
	            }
	        });
		} else {
			var groupId = '&groupId=';
			for(var i = 0 ;i<checkedEle.length;i++){
				groupId +=$(checkedEle[i]).val()+",";
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
	            iframe: {src: 'module/circle/open.jsp?num='+checkedEle.length+groupId}
	        });
		}
    });
    
    //停用
    $('#stopBtn').on('click', function(){
    	var checkedEle = $("#circleList input[type='checkbox']:checked");
    	if (checkedEle.length == 0) {
    		top.$.layer({
	            type: 1,
	            title: false,
	            area: ['120px', '72px'],
	            closeBtn: [0],
	            border: [1, 1, '#c2c2c2'],
	            shade: [0.1, '#000'],
	            shadeClose: false,
	            fadeIn: 300,
	            fix: true,
	            time:1,
	            page: {
	            	html: '<div class="dialogTips"><p>必须选择一个圈子</p></div>'
	            }
	        });
		} else {
			var groupId = '&groupId=';
			for(var i = 0 ;i<checkedEle.length;i++){
				groupId +=$(checkedEle[i]).val()+",";
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
	            iframe: {src: 'module/circle/stop.jsp?num='+checkedEle.length+groupId}
	        });
		}
    });
    
    //导出
    $('#exportBtn').on('click', function(){
    	var checkedEle = $("#circleList input[type='checkbox']:checked");
    	var groupId = '&groupId=';
		for(var i = 0 ;i<checkedEle.length;i++){
			groupId +=$(checkedEle[i]).val()+",";
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
            iframe: {src: 'module/circle/export.jsp?num='+checkedEle.length+groupId}
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
    function searchFun(){
    	//var param = "?name="+$("#name").val()+"&circleType="+$("#circleType a.selected").data("val")+"&circleState="+$("#circleState a.selected").data("val");
    	$("#cPage").val("1");
    	circleList();
    }
    
    //账号列表
    var loadi;
    function circleList(){
	    $.ajax({
	        url:webPath + "circle/list.do?randomTime=" + (new Date()).getTime(),
	        dataType:"json",
	        type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            data:{
            	name:$("#name").val(),
            	circleType:$("#circleType a.selected").data("val"),
            	circleState:$("#circleState a.selected").data("val"),
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
	        	top.layer.close(loadi);
	            require.async('handlebars-v2.0.0/handlebars-v2.0.0',function(){
	                require.async('handlebars-v2.0.0/transFormatJson',function(){
	                    var tpl = require('../../../common/tpl/circleList.tpl');//载入tpl模板
	                    var template = Handlebars.compile(tpl);
	                    var html = template(data);
	                    $("#circleList").html(html);
	                    //偶数行增加背景颜色
	                    $("#circleList tr:odd").addClass("itemBg");
	                })
	            });
	            //分页插件
	           if(data.page.countPage > 0){
	                require.async(['pager/pager.css','pager/pager'],function(){
	                    kkpager.generPageHtml({
	                            pno : data.page.currentPage,//当前页码
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
	                                    url:webPath + "circle/list.do?randomTime=" + (new Date()).getTime(),
	                                    dataType:"json",//这个必不可少，如果缺少，导致传回来的不是json格式
	                                    type: 'post',
	                                    contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	                                    data:{
	                                    	name:$("#name").val(),
	                                    	circleType:$("#circleType a.selected").data("val"),
	                                    	circleState:$("#circleState a.selected").data("val"),
	                                    	cPage:n
	                                    },
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
	                                            var tpl = require('../../../common/tpl/circleList.tpl');//载入tpl模板
	                                            var template = Handlebars.compile(tpl);
	                                            var html    = template(json);
	                                            $('#circleList').html(html);
	                                            //偶数行增加背景颜色
	                                            $("#circleList tr:odd").addClass("itemBg");
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
//	                kkpager.selectPage($("#cPage").val());
	            }else{
	                $("#kkpager").html('暂无数据');
	            }
	        }
	    })
    }
    

    //全选反选操作
    $("#checkAll").live("click",function(){
        if ($("#checkAll").is(':checked')) {
            $("#circleList input[type='checkbox']").each(function() {
                this.checked = true;
            });
        } else {
            $("#circleList input[type='checkbox']").each(function() {
                this.checked = false;
            });
        }
    })
    
    //圈子名
    $("#circleType a").click(function(){
		$(this).addClass("selected").siblings().removeClass("selected");
//		var param = "?name="+$("#name").val()+"&circleType="+$(this).data("val")+"&circleState="+$("#circleState a.selected").data("val");
		circleList();
	});
    
    //详情显示
    $("#showDetail").live("click",function(){
    	var groupId = $(this).attr("data-val");
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
            iframe: {src: 'module/circle/detail.jsp?groupId='+groupId}
        });
    })
    
    //状态
    $("#circleState a").click(function(){
		$(this).addClass("selected").siblings().removeClass("selected");
//		var param = "?name="+$("#name").val()+"&circleType="+$("#circleType a.selected").data("val")+"&circleState="+$(this).data("val");
		circleList();
	});
    
    exports.search = circleList;
    
});