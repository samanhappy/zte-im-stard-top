define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    var twitterId = $("#twitterId").val();
    //删除分享
    $('#delBtn').on('click', function(){
    	//var twitterId= $(this).attr("data-val");
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
            iframe: {src: 'module/microblog/delMicroblogTips.jsp?twitterId='+twitterId}
        });
    });
    

    //分享评论列表
    microblogCommentList();
    var loadi;
    function microblogCommentList(){
	    $.ajax({
	        url:webPath + "microblog/getComments.do?randomTime=" + (new Date()).getTime(),
	        dataType:"json",
	        type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            data:{
            	twitterId:twitterId,
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
	                    var tpl = require('../../../common/tpl/microblogCommentList.tpl');//载入tpl模板
	                    var template = Handlebars.compile(tpl);
	                    var html = template(data);
	                    $("#commentList").html(html);
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
	                                    url:webPath + "microblog/getComments.do?randomTime=" + (new Date()).getTime()+"&cPage="+n,
	                                    dataType:"json",//这个必不可少，如果缺少，导致传回来的不是json格式
	                                    type: 'post',
	                                    contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	                                    data:{twitterId:twitterId},
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
	                                            var tpl = require('../../../common/tpl/microblogCommentList.tpl');//载入tpl模板
	                                            var template = Handlebars.compile(tpl);
	                                            var html    = template(json);
	                                            $('#commentList').html(html);
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
    

    //删除评论
    $('.commentDelBtn').live('click', function(){
    	var commentId= $(this).attr("data-val");
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
            iframe: {src: 'module/microblog/delCommentTips.jsp?id='+commentId}
        });
    });
    
    exports.search = microblogCommentList;
});