define(function(require, exports, module) {
	// 引入jquery库
	require('jquery-1.8.3.min');
	require('date');
	// 引入Base64库
	require('Base64');
	// 日期库
	require('datepicker/WdatePicker');

	// 开始时间
	$('#startTime').on('click', function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss',
			maxDate : '%y-%M-%d'
		});
	});
	// 结束时间
	$('#endTime').on('click', function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss',
			maxDate : '%y-%M-%d'
		});
	});

	// 获取网站根路径
	var webPath = $("#webPath").val();

	// 默认加载
	var load = "?type=" + $(".recordSelectBox").val();
	recordList(load);

	// 搜索
	$("#keyword").live('keypress', function() {
		if (event.keyCode == 13) {
			searchFun();
		}
	});

	$(".sBtn").on('click', function() {
		searchFun();
	});

	function searchFun() {
		var sBtnVal = $(".recordSelectBox").val();
		var param = "?type=" + sBtnVal + "&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val();
		recordList(param);
	}
	// 列表
	var loadi;
	function recordList(param) {
		$.ajax({
			url : webPath + "listChat.do" + param,
			type : 'post',
			data : "keyword=" + $("#keyword").val(),
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			dataType : "json",
			beforeSend : function(data) {
				loadi = top.layer.load('列表加载中…');
			},
			success : function(data) {
				top.layer.close(loadi);
				require.async('handlebars-v2.0.0/handlebars-v2.0.0', function() {
					require.async('handlebars-v2.0.0/transFormatJson', function() {
						var tpl = require('../../../common/tpl/recordList.tpl');// 载入tpl模板
						var template = Handlebars.compile(tpl);
						var html = template(data);
						$("#recordList").html(html);
						// 偶数行增加背景颜色
						$("#recordList tr:odd").addClass("itemBg");
					});
				});
				// 分页插件
				if (data.page.pages > 0) {
					require.async([ 'pager/pager.css', 'pager/pager' ], function() {
						kkpager.generPageHtml({
							pno : data.page.currentPage,// 当前页码
							total : data.page.pages,// 总页码
							totalRecords : data.page.total,// 总数据条数
							isShowFirstPageBtn : false,
							isShowLastPageBtn : false,
							isShowTotalPage : false,
							isShowTotalRecords : false,
							isGoPage : false,
							lang : {
								prePageText : '<i class="iconfont">&#xe60f;</i>',
								nextPageText : '<i class="iconfont">&#xe610;</i>'
							},
							mode : 'click',// click模式匹配getHref 和 click
							click : function(n, total, totalRecords) {
								$.ajax({
									url : webPath + "listChat.do" + "?type=" + $(".recordSelectBox").val()
											+ "&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val()
											+ "&cPage=" + n + "&randomTime=" + (new Date()).getTime(),
									type : 'post',
									data : "keyword=" + $("#keyword").val(),
									contentType : "application/x-www-form-urlencoded; charset=utf-8",
									dataType : "json",
									dataType : "json",// 这个必不可少，如果缺少，导致传回来的不是json格式
									beforeSend : function(data) {
										loadi = top.layer.load('列表加载中…');
									},
									success : function(json) {
										top.layer.close(loadi);
										require.async([ 'handlebars-v2.0.0/handlebars-v2.0.0',
												'handlebars-v2.0.0/transFormatJson' ], function() {
											var tpl = require('../../../common/tpl/recordList.tpl');// 载入tpl模板
											var template = Handlebars.compile(tpl);
											var html = template(json);
											$('#recordList').html(html);
											// 偶数行增加背景颜色
											$("#recordList tr:odd").addClass("itemBg");
										});
									}
								});
								this.selectPage(n); // 处理完后可以手动条用selectPage进行页码选中切换
							}
						});
					});
				} else {
					$("#kkpager").html('暂无数据');
				}
			}
		});
	}

	// 数据导出
	$('#exportRecordBtn').on('click', function() {
		
		var checkedEle = $("#recordList input[type='checkbox']:checked");
    	var ids = "";
    	if (checkedEle.length > 0) {
			for ( var i = 0; i < checkedEle.length; i++) {
				ids += checkedEle[i].value;
				if (i < checkedEle.length - 1){
					ids += ',';	
				}
			}
    		desc = "确认导出选中"+checkedEle.length+"条记录吗";
    	} else {
    		top.layer.msg("至少选择一条记录", 1, -1);
    	}
		
		top.$.layer({
			type : 2,
			title : "提醒",
			area : [ '332px', '200px' ],
			closeBtn : [ 0, true ],
			border : [ 0 ],
			shade : [ 0.7, '#000' ],
			shadeClose : false,
			fadeIn : 300,
			move : false,
			fix : true,
			iframe : {
				src : 'module/security/record/exportRecord.jsp?ids=' + ids + "&desc=" + desc
			}
		});
	});
	
	//全选反选操作
    $("#checkAll").live("click",function(){
        if ($("#checkAll").is(':checked')) {
            $("#recordList input[type='checkbox']").each(function() {
                this.checked = true;
            });
        } else {
            $("#recordList input[type='checkbox']").each(function() {
                this.checked = false;
            });
        }
    });
});