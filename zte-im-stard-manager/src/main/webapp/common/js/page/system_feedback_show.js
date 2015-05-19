define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    //引入Base64库
    require('Base64');
    var common = require('common');
    var webPath=$("#webPath").val();
    var id = $("#id").val();
    
    showFeedback();

    function showFeedback(){
        $.ajax({
            url:webPath + "getFeedBack.do?id="+id+"&randomTime="+(new Date()).getTime(),
            type:"get",
            dataType:"json",
            success:function(data){
            	if(data.result){
            		$("#feedBackShow").html(common.base2Str(BASE64
							.decoder(data.data.content)));
                }else{
                	top.layer.msg(data.msg, 1, -1);
                }
            }
        });
    }

});