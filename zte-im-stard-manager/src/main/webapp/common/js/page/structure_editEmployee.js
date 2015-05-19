define(function(require,exports,module){
    //引入jquery库
    require('jquery-1.8.3.min');
    // 日期库
    require('datepicker/WdatePicker');
    var common = require('common');
    
    //获取网站根路径
    var webPath=$("#webPath").val();
    var id = $("#id").val();
    var deptId = '';
    
    //TAB切换
	require.async('common',function(){
		$(".employeeBd").tabChange({
			isClick:true,
            isHover:false,
			childLi:".employeeTab li",//tab选项卡
			childContent:".employeeContent",//tab内容
			hoverClassName:"active",//选中当前选项卡的样式
			callBack:false
		});
	});
    
	var iframeId = parent.layer.getFrameIndex(window.name);
    window.parent.$("#iframeIdVal").val("xubox_iframe"+iframeId);
    
    //载入职位列表
    getPosList();
    function getPosList(){
    	$.ajax({
            url:webPath + "posList.do",
            type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            dataType:"json",
            success:function(data){
            	if (data.result) {
            		 require.async('handlebars-v2.0.0/handlebars-v2.0.0',function(){
                         require.async('handlebars-v2.0.0/transFormatJson',function(){
                             var tpl = require('../../../common/tpl/posList.tpl');//载入tpl模板
                             var template = Handlebars.compile(tpl);
                             var html = template(data);
                             $("#pos").html(html);
                         });
                     });
            		 
            		//载入人员信息
            		getUserInfo();
            	} else {
            		top.layer.msg(data.msg, 1, -1);
            	}
            }
        });
    }
    
    //载入自定义参数
    getUserDefineParam();
    function getUserDefineParam(){
    	$.ajax({
            url:webPath + "loadUserDefineParam.do",
            type: 'post',
            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
            dataType:"json",
            success:function(data){
            	if (data.result) {
            		 require.async('handlebars-v2.0.0/handlebars-v2.0.0',function(){
                         require.async('handlebars-v2.0.0/transFormatJson',function(){
                             var tpl = require('../../../common/tpl/paramList.tpl');//载入tpl模板
                             var template = Handlebars.compile(tpl);
                             var html = template(data);
                             $("#userDefineParam").html(html);
                         });
                     });
            	} else {
            		top.layer.msg(data.msg, 1, -1);
            	}
            }
        });
    }
    
    var member = null;
    function getUserInfo(){
    	$.ajax({
    		url:webPath + "getUser.do?id="+ id + "&randomTime=" + (new Date()).getTime(),
            type:"get",
            dataType:"json",
            success:function(data){
            	if(data.result){
            		member = data.data;
            		
            		$("#roleRelationId").val(member.roleRelationId);
            		
            		$("#uid").val(member.uid);
            		$("#cn").val(member.cn);
            		$("#deptId").val(member.deptId);
            		deptId = member.deptId;
            		$("#deptName").val(member.deptName);
            		$("#deptRelationId").val(member.deptRelationId);
            		
            		if (member.sex) {
            			$("#sex").val(member.sex);
            		}
            		
            		$("#pos").val(member.roleId);
            		$("#roleRelationId").val(member.roleRelationId);
            		
            		$("#birthday").val(member.birthday);
            		$("#mobile").val(member.mobile);
            		$("#l").val(member.l);
            		$("#mail").val(member.mail);
            		$("#password").val(member.password);
            		
            		// 密码不可修改
            		$("#pwdDiv").css('background-color', 'rgb(235, 235, 228)');
            		$("#password").css('background-color', 'rgb(235, 235, 228)');
            		$("#password").attr('readonly', 'readonly');

            		$("#photoUrl").val(member.photo);
            		$("#photoImg").attr("src", member.photo);
            		$("#weixin").val(member.customField3);
            		$("#photoSign").val(member.photoSign);
            		$("#weibo").val(member.customField2);
            		$("#usable").val(member.usable);
            		$("#hobby").val(member.customField1);
            		$("#nickname").val(member.nickname);
            		$("#qq").val(member.qq);
            		$("#wwwhomepage").val(member.wwwhomepage);
            		$("#address").val(member.postaladdress);

            		$("#shortcode").val(member.shortcode);
            		$("#deviceId").val(member.deviceId);

            		$("#hometelephone").val(member.hometelephone);
            		$("#fax").val(member.fax);
            		$("#info").val(member.info);

                }else{
                	top.layer.msg(data.msg, 1, -1);
                }
            }
    	});
    }
    
	//部门
    $('#openDept').on('click', function(){
    	$('#deptName').blur();
        parent.$.layer({
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
            iframe: {src: 'module/structure/selectDepartment.jsp?id=' + deptId}
        });
    });
    
    //出生日期
    $('#birthday').on('click', function(){
    	WdatePicker({
    		dateFmt:'yyyy-MM-dd',
    		maxDate:'%y-%M-%d',
    		onpicked:function(){
    			$('#birthday').valid(); 
    		}	
    	});
    });
    
    //删除员工
    $('#dialogDelBtn').on('click', function(){
    	
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
            iframe: {src: 'module/structure/delEmployee.jsp?removeids=' + member.id + "&removelen=" + 1 + "&removenames=" + member.cn}
        });
    });
    
    var isAddUser = true;

	require.async('jquery-validation-1.13.1/jquery.validate.min', function() {
		require.async('jquery-validation-1.13.1/additional-methods', function() {
			$("#editEmployeeForm").validate({
				submitHandler : function(form) {
					// 设置职位和性别数据
					$("#posDisplay").val($("#pos").find("option:selected").text());
					$("#sexDisplay").val($("#sex").find("option:selected").text());
					require.async('jquery.form.min', function() {
						$(form).ajaxSubmit({
							dataType : 'json',
							type : 'post',
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							success : function(data) {
								if (data.result) {
									if (isAddUser) {
										parent.location.reload();
									} else {
										$("#photoImg").attr("src", data.fileUrl);
										$("#photoUrl").val(data.fileUrl);
									}
								} else {
									top.layer.msg(data.msg, 1, -1);
								}
							},
							complete : function() {
								var fileObj = $("#photo");
								$("#photo").val('');
								// 针对ie的clear实现
								fileObj.replaceWith(fileObj = fileObj.clone(true));
							}
						});
					});
				}
			});
		});
	});
    
    $('#photo').on('change', function(){
    	var photo = $("#photo").val();
		if (photo != "") {
			var fileObj = $("#photo");
			if (!common.isAllowedAttach(photo, ' .jpeg .gif .jpg .png ')) {
				$("#photo").val('');
				//针对ie的clear实现
				fileObj.replaceWith( fileObj = fileObj.clone( true ) );
				top.layer.msg("头像格式不正确", 1, -1);
				return false;
			}
			if (!common.checkfile()) {
				$("#photo").val('');
				//针对ie的clear实现
				fileObj.replaceWith( fileObj = fileObj.clone( true ) );
				return false;
			}
			
			isAddUser = false;
			$("#editEmployeeForm").attr("action", webPath + "uploadFile.do");
			$("#uid").rules("remove");
			$("#deptName").rules("remove");
			$("#mobile").rules("remove");
			$("#mail").rules("remove");
			$("#password").rules("remove");
			$("#editEmployeeForm").submit();
		}
    });
    
    // 添加员工
	$('#dialogAddBtn').on('click', function() {
		isAddUser = true;
		$("#editEmployeeForm").attr("action", webPath + "addUser.do");
		$("#uid").rules("add", {
			required : true,
			messages : {
				required : "请输入员工号"
			}
		});
		$("#cn").rules("add", {
			required : true,
			messages : {
				required : "请输入姓名"
			}
		});
		$("#deptName").rules("add", {
			required : true,
			messages : {
				required : "请选择部门"
			}
		});
		$("#mobile").rules("add", {
			required : true,
			isMobile : true,
			messages : {
				required : "请输入手机号码",
				isMobile : "手机号码格式错误"
			}
		});
		$("#mail").rules("add", {
			required : true,
			email : true,
			messages : {
				required : "请输入邮箱",
				email : "请输入正确的邮箱地址"
			}
		});
		$("#password").rules("add", {
			required : true,
			messages : {
				required : "请输入密码"
			}
		});
		$("#editEmployeeForm").submit();
	});
    
	//取消操作
    $('#dialogCancelBtn').on('click', function(){
        top.layer.closeAll();
    });
});