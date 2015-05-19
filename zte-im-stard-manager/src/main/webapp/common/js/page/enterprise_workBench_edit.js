define(function(require, exports, module) {
	// 引入jquery库
	require('jquery-1.8.3.min');

	// 引入jquery UI库
	require('jquery-ui-1.10.4.min');
	var common = require('common');

	var webPath = $("#webPath").val();

	// 加载列表
	workBenchList();
	var loadi;
	function workBenchList() {
		$.ajax({
			url : webPath + "listWorkImg.do?randomTime=" + (new Date()).getTime(),
			type : "get",
			dataType : "json",
			beforeSend : function(data) {
				loadi = top.layer.load('列表加载中…');
			},
			success : function(data) {
				top.layer.close(loadi);
				require.async('handlebars-v2.0.0/handlebars-v2.0.0', function() {
					require.async('handlebars-v2.0.0/transFormatJson', function() {
						var tpl = require('../../tpl/workBenchEditList.tpl');// 载入tpl模板
						var template = Handlebars.compile(tpl);
						var html = template(data);
						$("#workBenchList").html(html);

						var MaxLength = 4;
						var itemLength = $("#workBenchList li.dropItem").length;

						if (itemLength == 1) {
							$(".delBtn").remove();
						}

						if (itemLength < MaxLength) {
							for ( var i = itemLength; i < MaxLength; i++) {
								$("#workBenchList").append(
										'<li id="li' + i + '" index="' + i + '" class="addPicBox"><form action="' + webPath
												+ 'uploadFile.do" id="form' + i + '"><input index="' + i
												+ '" type="file" name="photo" class="fileInput"></form></li>');
								bindForm(i);
							}
							bindChange();
						}
					});
				});
			}
		});
	}

	function bindForm(index) {
		require.async('jquery-validation-1.13.1/jquery.validate.min', function() {
			require.async('jquery-validation-1.13.1/additional-methods', function() {
				$("#form" + index).validate({
					submitHandler : function(form) {
						require.async('jquery.form.min', function() {
							$(form).ajaxSubmit({
								dataType : 'json',
								type : 'post',
								contentType : "application/x-www-form-urlencoded; charset=utf-8",
								success : function(data) {
									if (data.result) {
										$("#li" + index).html('<img width="180px" height="100px" src="'+data.sourceFileUrl+'"/><i class="delBtn" index="'+index+'"></i><input type="hidden" name="workImg" value="'+data.sourceFileUrl+'">');
										$("#li" + index).removeClass('addPicBox');
										$("#li" + index).addClass('dropItem');
									} else {
										top.layer.msg(data.msg, 1, -1);
									}
								},
								complete : function() {
									var fileObj = $("#form" + index + " input");
									$("#form" + index + " input").val('');
									// 针对ie的clear实现
									fileObj.replaceWith(fileObj = fileObj.clone(true));
								}
							});
						});
					}
				});
			});
		});
	}

	function bindChange() {

		// 上传图片
		$('.fileInput').each(function() {
			$(this).on('change', function() {
				var photo = $(this).val();
				if (photo != "") {
					var fileObj = $(this);
					if (!common.isAllowedAttach(photo, ' .jpeg .gif .jpg .png ')) {
						$(this).val('');
						// 针对ie的clear实现
						fileObj.replaceWith(fileObj = fileObj.clone(true));
						top.layer.msg("图片格式不正确", 1, -1);
						return false;
					}
				}

				$("#form" + $(this).attr("index")).submit();
			});
		});
	}

	// 拖动效果
	$("#workBenchList").sortable({
		placeholder : "drapPlaceholder",
		cancel : "workBenchUpoadBox",
		opacity : 0.8
	});
	$("#sortable").disableSelection();

	// 删除
	$(".delBtn").live("click", function() {
		var itemLength = $("#workBenchList li.dropItem").length;
		if (itemLength == 1) {
			top.layer.msg("工作台图片不得少于1张", 1, -1);
		} else {
			var i = $(this).attr("index");
			$("#li" + i).html('<form action="' + webPath
						+ 'uploadFile.do" id="form' + i + '"><input index="' + i
						+ '" type="file" name="photo" class="fileInput"></form>');
			$("#li" + i).removeClass('dropItem');
			$("#li" + i).addClass('addPicBox');
			bindForm(i);
			bindChange();
		}
	});
	
	// 保存
	$("#saveBtn").live("click", function() {
		var imgList = '';
		for (var i = 0; i < $("input[name=workImg]").length; i++) {
			imgList += $("input[name=workImg]")[i].value;
			if (i < $("input[name=workImg]").length - 1) {
				imgList += ',';
			}
		}
		$("#imgList").val(imgList);
		$("#workBenchForm").submit();
	});
	

	// 保存
	require.async('jquery-validation-1.13.1/jquery.validate.min', function() {
		require.async('jquery-validation-1.13.1/additional-methods', function() {
			$("#workBenchForm").validate({
				submitHandler : function(form) {
					require.async('jquery.form.min', function() {
						$(form).ajaxSubmit({
							dataType : 'json',
							type : 'post',
							success : function(data) {
								if (data.result) {
									window.location.href = "list.jsp";
								} else {
									top.layer.msg("保存失败", 1, -1);
								}
							}
						});
					});
				}
			});
		});
	});
});