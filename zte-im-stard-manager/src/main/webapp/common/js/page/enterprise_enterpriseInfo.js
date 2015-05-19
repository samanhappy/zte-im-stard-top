define(function(require, exports, module) {
	// 引入jquery库
	require('jquery-1.8.3.min');

	// 获取网站根路径
	var webPath = $("#webPath").val();

	//加载企业信息
	$.ajax({
		type : "POST",
		url : webPath + 'selectTenant.do',
		dataType : "json",
		success : function(data) {
			if (data.result) {
				$("#id").val(data.data.id);
				$("#platformId").val(data.data.platformId);
				$("#platformIdDiv").html(data.data.platformId);
				$("#name").val(data.data.name);
				$("#linkman").val(data.data.linkman);
				$("#mobile").val(data.data.mobile);
				$("#mail").val(data.data.mail);
				$("#tel").val(data.data.tel);
				$("#guimo").val(data.data.guimo);
				$("#prov").html("<option value='"+data.data.prov+"' class='province'>"+data.data.prov+"</option>" );
				$("select#city").html("<option value='"+data.data.city+"' class='cityOpt'>"+data.data.city+"</option>");
				$("#address").val(data.data.address);
				
				// 所在地区
				require.async('jquery-citySelect/jquery.cityselect', function() {
					var prov = $("#prov").val();
					var city = $("#city").val();
					$("#city").citySelect({
						url : webPath + "common/js/jquery-citySelect/city.json",
						prov : prov, // 省份
						city : city, // 城市
						required : true,
						nodata : "none" // 当子集无数据时，隐藏select
					});
				});
			} else {
				top.layer.msg(data.msg, 1, -1);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			top.layer.msg("数据操作出错", 1, -1);
		}
	});
	
	//更新企业信息
    require.async('jquery-validation-1.13.1/jquery.validate.min',function(){
    	require.async('jquery-validation-1.13.1/additional-methods',function(){
    		$("#enterpriseInfoForm").validate({
                rules:{
                	name:{
                        required: true
                    },
                    linkman:{
                        required: true
                    },
                    mobile:{
                        required: true
                    },
                    mail:{
                        required: true
                    }
                },
                messages:{
                	name:{
                        required: "请输入企业名称"
                    },
                    linkman:{
                        required: "请输入企业联系人"
                    },
                    mobile:{
                        required: "请输入联系电话"
                    },
                    mail:{
                        required: "请输入电子邮箱"
                    }
                },
                submitHandler:function(form){
                    require.async('jquery.form.min',function(){
                        $(form).ajaxSubmit({
                            dataType:'json',
                            type: 'post',
                            contentType: "application/x-www-form-urlencoded; charset=utf-8", 
                            success:function(data){
                                if(data.result){
                                	window.location.reload();
                                }else{
                                	top.layer.msg(data.msg, 1, -1);
                                }
                            }
                        });
                    });
                 } 
            });
    	});
    });
	

});