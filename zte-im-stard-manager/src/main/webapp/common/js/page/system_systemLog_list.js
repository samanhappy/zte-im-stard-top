define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    //引入Base64库
    require('Base64');
    
    var webPath=$("#webPath").val();
    //默认加载类型
    showList($("#type a").attr("data-val"));
    
    //类型筛选
    $("#type a").click(function(){
        $(this).addClass("selected").siblings().removeClass("selected");
        var param = $(this).attr("data-val");
        showList(param);
    });

    function showList(param){
        $.ajax({
            url:webPath + "queryLog.do?type="+param+"&randomTime="+(new Date()).getTime(),
            type:"get",
            dataType:"json",
            success:function(data){
                require.async('handlebars-v2.0.0/handlebars-v2.0.0',function(){
                    require.async('handlebars-v2.0.0/transFormatJson',function(){
                        var tpl = require('../../tpl/logList.tpl');//载入tpl模板
                        var template = Handlebars.compile(tpl);
                        var html = template(data);
                        $("#logList").html(html);
                        //偶数行增加背景颜色
                        $("#logList tr:odd").addClass("itemBg");
                    })
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
                                        type:"get",
                                        url:webPath + "queryLog.do?type="+param+"&randomTime="+ (new Date()).getTime()+"&cPage="+n,
                                        dataType:"json",//这个必不可少，如果缺少，导致传回来的不是json格式
                                        success:function(json){
                                            require.async(['handlebars-v2.0.0/handlebars-v2.0.0','handlebars-v2.0.0/transFormatJson'],function(){
                                                var tpl = require('../../tpl/logList.tpl');
                                                var template = Handlebars.compile(tpl);
                                                var html    = template(json);
                                                $('#logList').html(html);
                                                //偶数行增加背景颜色
                                                $("#logList tr:odd").addClass("itemBg");
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
        })
    }
    //信息反馈列表
    
    
    //查看反馈内容
    $("#feedBackShow").live("click",function(){
        var idVal = $(this).attr("data-id");
        top.$.layer({
            type: 2,
            title: "反馈内容",
            area: ['700px', '400px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: '/module/system/feedBack/show.jsp?id='+idVal}
        });
    })

});