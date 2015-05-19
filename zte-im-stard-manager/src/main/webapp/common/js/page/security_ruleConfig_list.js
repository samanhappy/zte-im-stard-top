define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    var loadi;
    //设置可发送的敏感字
    canSendList();
    function canSendList(){
        $.ajax({
            url:webPath + "json/canSendList.json",
            type:"get",
            dataType:"json",
            beforeSend:function(data){
            	loadi = top.layer.load('列表加载中…');
            },
            success:function(data){
            	top.layer.close(loadi);
                require.async('handlebars-v2.0.0/handlebars-v2.0.0',function(){
                    require.async('handlebars-v2.0.0/transFormatJson',function(){
                        var tpl = require('../../../common/tpl/canSendList.tpl');//载入tpl模板
                        var template = Handlebars.compile(tpl);
                        var html = template(data);
                        $("#canSendList").html(html);
                    })
                });
            }
        })
    }
    
    //设定禁止发送的敏感字
    prohibitSendList();
    function prohibitSendList(){
        $.ajax({
            url:webPath + "json/canSendList.json",
            type:"get",
            dataType:"json",
            beforeSend:function(data){
            	loadi = top.layer.load('列表加载中…');
            },
            success:function(data){
            	top.layer.close(loadi);
                require.async('handlebars-v2.0.0/handlebars-v2.0.0',function(){
                    require.async('handlebars-v2.0.0/transFormatJson',function(){
                        var tpl = require('../../../common/tpl/canSendList.tpl');//载入tpl模板
                        var template = Handlebars.compile(tpl);
                        var html = template(data);
                        $("#prohibitSendList").html(html);
                    })
                });
            }
        })
    }
    
    //设置可发送的敏感字-设置敏感词
    $('#addSendBtn').on('click', function(){
    	top.$.layer({
            type: 2,
            title: "设置敏感词",
            area: ['332px', '200px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/security/ruleConfig/addCanSend.jsp'}
        });
    });
    
    //设定禁止发送的敏感字-设置敏感词
    $('#addRuleBtn').on('click', function(){
    	top.$.layer({
            type: 2,
            title: "设置敏感词",
            area: ['332px', '200px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.7, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/security/ruleConfig/addProhibitSend.jsp'}
        });
    });
    
    //删除
    $('#delBtn').live('click', function(){
    	var delId = $(this).attr("data-val");
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
            iframe: {src: 'module/security/ruleConfig/delTips.jsp?id=' + delId}
        });
    })
    
});