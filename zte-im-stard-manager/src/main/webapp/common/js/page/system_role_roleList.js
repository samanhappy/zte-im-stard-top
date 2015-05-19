define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
    //显示角色列表
    $.ajax({
        url:webPath + "listRole.do" + "?t=" + (new Date()).getTime(),
        type:"get",
        dataType:"json",
        success:function(data){
        	var html="";
        	if (data.item) {
        		for(i=0;i< data.item.length;i++){
    				html+="<li data-id='"+data.item[i].roleId+"'><a href='javascript:;'>"+data.item[i].roleName+"</a></li>";
    			}
        	}
			$(".roleList").html(html);
			//默认加载选中的角色
			var roleId = $(".roleList li:eq(0)").attr("data-id");
			$(".roleList li:eq(0)").addClass("selected");
			$("iframe").attr("src",webPath + "module/system/role/roleShow.jsp?roleId="+roleId);
			//选择角色
		    $(".roleList li").click(function(){
		    	$(this).addClass("selected").siblings().removeClass("selected");
		    	var roleId = $(this).attr("data-id");
		    	$("iframe").attr("src",webPath + "module/system/role/roleShow.jsp?roleId="+roleId);
		    });
        }
    });


});