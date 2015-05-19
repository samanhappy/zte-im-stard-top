define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    
	var iframeId = parent.layer.getFrameIndex(window.name);
    window.parent.$("#iframeIdVal").val("xubox_iframe"+iframeId);
    
    var curUid = $("#id").val();
    var curUname = $("#name").val();

	//获取职位数据
    var posData;
    loadPosData();
    function loadPosData(){
    	$.ajax({
            url:webPath + "posList.do",
            type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            dataType:"json",
            success:function(data){
            	if(data.result){
            		posData = data.item;
            		 //加载保护设置
            	    listUserPerm();
                }else{
                	top.layer.msg(data.msg, 1, -1);
                }
            }
        });
    }
    
    function contain(item, permId) {
		if (item && permId && item.length && item.length > 0) {
			for ( var i = 0; i < item.length; i++) {
				if (item[i].permId == permId) {
					return true;
				}
			}
		}
		return false;
	}
    
    function setPermByType(item, type) {
		var perms = '';
		var divPerms = '';
		if (item && type && item.length && item.length > 0) {
			for ( var i = 0; i < item.length; i++) {
				if (item[i].permType == type) {
					if (type == 3) {
						perms += item[i].permName + '；';
					} else {
						perms += item[i].permName + '；';
					}
					divPerms += "<input id='userPermDiv" + item[i].permId + "' type='hidden' name='" + item[i].permName + "' value='" + item[i].permId + "' />";
				}
			}
		}
		if (perms.length > 0) {
			perms = perms.substring(0, perms.length - 1);
		}
		if (type == 2) {
			$("#visibleDept").html(perms);
			$("#deptPermDiv").html(divPerms);
		} else if (type == 3) {
			$("#visiblePersonnel").html(perms);
			$("#userPermDiv").html(divPerms);
		}
	}
    
    //加载保护设置
    function listUserPerm(){
    	$.ajax({
            url:webPath + "listUserPerm.do?uid=" + $("#id").val(),
            type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            dataType:"json",
            success:function(data){
            	if(data.result){
            		//是否开启保护设置
            		$("#openSitting").attr("checked",data.protected);
            		if (data.protected){
        				$("#visibleRank").show();
        				$("#visibleDept").show();
        				$("#visiblePersonnel").show();
        				$("#selectDept").show();
        				$("#selectPersonnel").show();
        				$("#noSettingBtn").hide();
        				$("#SettingBtn").show();
        			}else{
        				$("#visibleRank").hide();
        				$("#visibleDept").hide();
        				$("#visiblePersonnel").hide();
        				$("#selectDept").hide();
        				$("#selectPersonnel").hide();
        				$("#noSettingBtn").show();
        				$("#SettingBtn").hide();
        			}
            		// 加载职级角色权限
					var posHtml = '';
					for ( var i = 0; i < posData.length; i++) {
						posHtml += "<label><input type='checkbox'";
						if (contain(data.item, posData[i].id)) {
							posHtml += "checked='checked'";
						}
						posHtml += " class='checkbox' name='"
								+ posData[i].name + "' value='"
								+ posData[i].id + "' />"
								+ posData[i].name + "</label>";
					}
					$("#visibleRank").html(posHtml);
					
					// 加载部门和用户权限
					setPermByType(data.item, 2);
					setPermByType(data.item, 3);
            		
                }else{
                	top.layer.msg(data.msg, 1, -1);
                }
            }
        });
    }
    
    
    $("#openSitting").live('click', function(){
		if($("#openSitting").attr("checked")){
			$("#visibleRank").show();
			$("#visibleDept").show();
			$("#visiblePersonnel").show();
			$("#selectDept").show();
			$("#selectPersonnel").show();
			$("#noSettingBtn").hide();
			$("#SettingBtn").show();
		}else{
			$("#openSitting").removeAttr("checked");
			$("#visibleRank").hide();
			$("#visibleDept").hide();
			$("#visiblePersonnel").hide();
			$("#selectDept").hide();
			$("#selectPersonnel").hide();
			$("#noSettingBtn").show();
			$("#SettingBtn").hide();
		}
	});
    
    //可见职级
    
    //可见部门
    $('#selectDept').live('click', function(){
    	top.$.layer({
            type: 2,
            title: "选择部门",
            area: ['720px', '404px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.1, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/structure/selectDept.jsp'}
        });
    });
    //可见人员
    $('#selectPersonnel').live('click', function(){
    	top.$.layer({
            type: 2,
            title: "选择人员",
            area: ['720px', '404px'],
            closeBtn: [0, true],
            border: [0],
            shade: [0.1, '#000'],
            shadeClose: false,
            fadeIn: 300,
            move: false,
            fix: true,
            iframe: {src: 'module/structure/selectPersonnel.jsp'}
        });
    });

    //清空权限
    $('#dialogCleanBtn').live('click', function(){
        $("#visibleRank input").removeAttr("checked");
        $("#visibleDept").html("");
        $("#deptPermDiv").html("");
        $("#visiblePersonnel").html("");
        $("#userPermDiv").html("");
    });

    //确定操作
    $('#dialogOkBtn').live('click', function(){
    	var permList = '';
		var index = 0;
		var permPosList = $("#visibleRank input:checked");
		if (permPosList.length > 0) {
			for ( var i = 0; i < permPosList.length; i++) {
				permList += '&permList[' + (index + i) + '].memberId=' + curUid;
				permList += '&permList[' + (index + i) + '].permType=1';
				permList += '&permList[' + (index + i) + '].permId='
						+ permPosList[i].value;
				permList += '&permList[' + (index + i) + '].permName='
						+ permPosList[i].name;
			}
			index += permPosList.length;
		}

		var permDeptList = $("#deptPermDiv input");
		if (permDeptList.length > 0) {
			for ( var i = 0; i < permDeptList.length; i++) {
				permList += '&permList[' + (index + i) + '].memberId=' + curUid;
				permList += '&permList[' + (index + i) + '].permType=2';
				permList += '&permList[' + (index + i) + '].permId='
						+ permDeptList[i].value;
				permList += '&permList[' + (index + i) + '].permName='
						+ permDeptList[i].name;
			}
			index += permDeptList.length;
		}

		var permUserList = $("#userPermDiv input");
		if (permUserList.length > 0) {
			for ( var i = 0; i < permUserList.length; i++) {
				permList += '&permList[' + (index + i) + '].memberId=' + curUid;
				permList += '&permList[' + (index + i) + '].permType=3';
				permList += '&permList[' + (index + i) + '].permId='
						+ permUserList[i].value;
				permList += '&permList[' + (index + i) + '].permName='
						+ permUserList[i].name;
			}
			index += permUserList.length;
		}
		var isProtected = $("#openSitting").attr("checked") ? true : false;
    	$.ajax({
    		url:webPath + "updateUserPerm.do",
    		data : 'uid=' + curUid + '&uname=' + curUname + '&isProtected=' + isProtected + permList,
    		type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            dataType:"json",
            success:function(data){
            	if(data.result){
            		top.layer.closeAll();
                }else{
                	top.layer.msg(data.msg, 1, -1);
                }
            }
    	});
    });
    
    
	//取消操作
    $('#dialogCancelBtn').live('click', function(){
        top.layer.closeAll();
    });
});