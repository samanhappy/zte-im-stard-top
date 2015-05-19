/*
    通用文件上传类
    version 1.00
    code by chenfeng during 2013-03-12
    link 348018533@qq.com

    Demo：
    $("#filepost1").Uploader({////上传控件的ID
        action: "/ajax/uploaderajax.ashx",//上传到服务器的路径 默认：/ajax/uploaderajax.ashx
        params: "action=uploadpic",//特殊参数 随同文件一起提交到后台,以&号分隔 默认：action=uploadpic
        maxsize: 1024 * 500,//文件最大尺寸  默认：500K
        exts: ".jpg|.png|.gif|.jpeg",//文件格式限制 默认：.jpg|.png|.gif|.jpeg
        callback: function (file, urlpath) { //上传完成之后回调函数 返回 控件实例和文件服务器路径
        }
    });
 */
$(function() {
	var Uploader = function(file, options) {
		var defaults = {
			action : "/ajax/uploaderajax.ashx",
			params : "action=uploadpic",
			maxsize : 1024 * 500,
			exts : ".jpg|.png|.gif|.jpeg",
			callback : function(fileid, urlpath) {
			}
		};
		if (options == "remove") {
			Uploader.Remove(file);
			return;
		}
		var opts = $.extend(defaults, options);
		var params = opts.params.split("&");
		var form = $('<form action="' + opts.action
				+ '" enctype="multipart/form-data" method="post" target="ifr_'
				+ $(file).attr("id") + '" name="form_' + $(file).attr("id")
				+ '" id="form_' + $(file).attr("id")
				+ '" style="display: none;"/>');
		for ( var i = 0; i < params.length; i++) {
			var values = params[i].split("=");
			$(form).append(
					'<input type="hidden" name="' + values[0] + '" value="'
							+ values[1] + '"/>');
		}
		var iframe = $('<iframe name="ifr_' + $(file).attr("id") + '" id="ifr_'
				+ $(file).attr("id")
				+ '" style="position: absolute; top: -999px;"></iframe>');
		$(file).change(function(e) {
			if ($(this).val() != "" && $(this) != undefined) {
				if (!Uploader.BeforeCheck($(this), opts)) {
					return;
				}
				var pfile = $(this).parent();
				$(form).append($(this));
				$(form).submit();
				$(pfile).append($(this));
			}
		});
		$(iframe).load(function() {
			var contents = $(this).contents().get(0);
			var data = $(contents).find('body').text();
			if (data == "") {
				return;
			}
			data = window.eval('(' + data + ')');
			if (data.res.reCode != '1') {
				alert(data.res.resMessage);
				return;
			}
			var picUrl = data.fileUrl;
			opts.callback(file, picUrl);
		});
		$("body").append(form);
		$("body").append(iframe);

	}
	Uploader.BeforeCheck = function(file, opts) {
		var fileName = "";
		var f = null;
		if ($.browser.msie) {
			f = document.getElementById($(file).attr("id"));
		} else {
			f = document.getElementById($(file).attr("id")).files[0];
		}
		if (!$.browser.msie && f.size > opts.maxsize) {
			alert(Uploader.ErrorTip.MaxSize);
			return false;
		}
		fileName = $(file).val();
		var exts = opts.exts.split("|");
		var isExt = false;
		for ( var i = 0; i < exts.length; i++) {
			if (fileName.indexOf(exts[i]) >= 0) {
				isExt = true;
				break;
			}
		}
		if (!isExt) {
			alert(Uploader.ErrorTip.Ext);
			return false;
		}
		return true;
	}
	Uploader.ErrorTip = {
		MaxSize : "文件超过尺寸",
		Ext : "文件格式错误"
	}
	Uploader.Remove = function(file) {
		$(file).remove()
		$("#form_" + $(file).attr("id")).remove();
		$("#ifr_" + $(file).attr("id")).remove();
	}
	$.fn.Uploader = function(options) {
		return new Uploader(this, options);
	}
})