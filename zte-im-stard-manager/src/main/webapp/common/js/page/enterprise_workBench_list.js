define(function(require, exports, module) {
	// 引入jquery库
	require('jquery-1.8.3.min');

	// 引入jquery UI库
	require('jquery-ui-1.10.4.min');

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
						var tpl = require('../../tpl/workBenchList.tpl');// 载入tpl模板
						var template = Handlebars.compile(tpl);
						var html = template(data);
						$("#workBenchList").html(html);
					});
				});
			}
		});
	}

	// 保存
});