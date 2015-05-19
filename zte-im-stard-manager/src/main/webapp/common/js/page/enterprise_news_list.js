define(function(require, exports, module) {
	// 引入jquery库
	require('jquery-1.8.3.min');

	// 获取网站根路径
	var webPath = $("#webPath").val();
	
	loadPrivileges();
	var privileges = null;
	var allPrivileges = ["publishNews","editNews","removeNews"];
	function loadPrivileges() {
		$.ajax({
			type : "POST",
			url : webPath + 'getPrivileges.do?t=' + (new Date()).getTime(),
			dataType : "json",
			success : function(data) {
				privileges = JSON.stringify(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}

	newsList();
	var loadi;
	// 新闻列表
	function newsList() {
		$.ajax({
			url : webPath + "getNewsList.do?days=" + $("#days").val(),
			type : "get",
			dataType : "json",
			beforeSend : function(data) {
				loadi = top.layer.load('列表加载中…');
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			},
			complete : function() {
				top.layer.close(loadi);
			},
			success : function(data) {
				require.async('handlebars-v2.0.0/handlebars-v2.0.0', function() {
					require.async('handlebars-v2.0.0/transFormatJson', function() {
						var tpl = require('../../../common/tpl/newsList.tpl');// 载入tpl模板
						var template = Handlebars.compile(tpl);
						var html = template(data);
						$("#newsList").html(html);
						checkPrivileges(privileges, allPrivileges);
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
									type : "get",
									url : webPath + "getNewsList.do?days=" + $("#days").val() + "randomTime="
											+ (new Date()).getTime() + "&page=" + n,
									dataType : "json",// 这个必不可少，如果缺少，导致传回来的不是json格式
									beforeSend : function(data) {
										loadi = top.layer.load('列表加载中…');
									},
									success : function(json) {
										top.layer.close(loadi);
										require.async([ 'handlebars-v2.0.0/handlebars-v2.0.0',
												'handlebars-v2.0.0/transFormatJson' ], function() {
											var tpl = require('../../../common/tpl/newsList.tpl');
											var template = Handlebars.compile(tpl);
											var html = template(json);
											$('#newsList').html(html);
											checkPrivileges(privileges, allPrivileges);
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

	// 删除新闻
	$(".removeNews").live("click", function() {
		$.ajax({
			url : webPath + "delNews.do?news_id=" + $(this).attr("date-val"),
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.result) {
					top.layer.msg('删除成功', 2, -1);
					newsList();
				} else {
					top.layer.msg('删除失败', 2, -1);
				}
			}
		});
	});
	
	function checkPrivileges(privileges, allPrivileges) {
		if (privileges == null) {
			for (var i in allPrivileges) {
				$("." + allPrivileges[i]).unbind('click');
				$("." + allPrivileges[i]).die('click');
				$("." + allPrivileges[i]).attr('href','javascript:;');
				$("." + allPrivileges[i]).css('color','gray');
				$("." + allPrivileges[i]).css('cursor','text');
			}
		} else if (privileges != '\"all\"') {
			for (var i in allPrivileges) {
				if (privileges.indexOf(allPrivileges[i]) == -1) {
					$("." + allPrivileges[i]).unbind('click');
					$("." + allPrivileges[i]).die('click');
					$("." + allPrivileges[i]).attr('href','javascript:;');
					$("." + allPrivileges[i]).css('color','gray');
					$("." + allPrivileges[i]).css('cursor','text');
				}
			}
		}
	}
	

});