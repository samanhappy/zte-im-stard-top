define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
   
   	//获取网站根路径
    var webPath=$("#webPath").val();
    var roleId=$("#roleId").val();
    
    if (roleId != 'undefined') {
    	//加载角色
        $.ajax({
    		url:webPath + "getRole.do?id="+ roleId + "&t=" + (new Date()).getTime(),
            type:"get",
            dataType:"json",
            success:function(data){
            	if(data.result){
            		$("#roleName").html(data.data.roleName);
            		$("#desc").html(data.data.desc);
            		if (data.data.privileges && data.data.privileges.length > 0) {
            			for (var i = 0; i < data.data.privileges.length; i++) {
            				$("#" + data.data.privileges[i]).addClass("display-block");
            			}
            		}
            		if ($("#strutureManage .display-block").length > 0) {
            			$("#strutureManage").addClass("display-block");
            		}
            		if ($("#orgManage .display-block").length > 0) {
            			$("#orgManage").addClass("display-block");
            		}
            		if ($("#systemManage .display-block").length > 0) {
            			$("#systemManage").addClass("display-block");
            		}
            		
            		var name = '';
            		if (data.data.userList && data.data.userList.length > 0) {
            			for (var i = 0; i < data.data.userList.length; i++) {
            				name += data.data.userList[i].name;
            				if (i < data.data.userList.length - 1) {
            					name += '；';
            				}
            			}
            		}
            		$("#roleUsers").html(name);
                }else{
                	top.layer.msg(data.msg, 1, -1);
                }
            }
    	});
        
        //编辑角色
        $("#editRoleBtn").click(function(){
        	self.location.href = 'roleEdit.jsp?roleId=' + roleId;
        });
        
        //删除角色
        $("#delRoleBtn").click(function(){
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
                iframe: {src: 'module/system/role/delTips.jsp?roleId=' + roleId + "&roleName=" + $("#roleName").html()}
            });
        });
    }
    
    
    
});