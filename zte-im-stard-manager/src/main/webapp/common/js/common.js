define(function(require, exports, module) {
	// tab切换-----------------------------------------------------------------------
	(function($) {
		$.fn.extend({
			tabChange : function(options) {
				var defaults = {
					isClick : false,
					isHover : true,
					childLi : ".tab-ul ",// tab选项卡
					childContent : ".tab-contentbox",// tab内容
					hoverClassName : "hover",// 选中当前选项卡的样式
					callBack : false
				};
				var options = $.extend(defaults, options);
				this.each(function() {
					var o = options;
					var obj = $(this);
					var oLi = $(o.childLi, obj);
					var oDiv = $(o.childContent, obj);
					var Timer;
					var oLiClick = function() {
						oLi.eq(0).addClass(o.hoverClassName);
						oDiv.eq(0).show();
						addType(oLi.eq(0).find("span").attr("data-value"));
						oLi.click(function() {
							addType($(this).find("span").attr("data-value"));
							var index = oLi.index(this);
							$(oLi[index]).addClass(o.hoverClassName).siblings()
									.removeClass(o.hoverClassName);
							oDiv.hide();
							$(oDiv[index]).show();
						})
					};
					var oLiHover = function() {
						oLi.eq(0).addClass(o.hoverClassName);
						oDiv.eq(0).show();
						oLi.mouseenter(function() {
							addType($(this).find("span").attr("data-value"))
							var index = oLi.index(this);
							$(oLi[index]).addClass(o.hoverClassName).siblings()
									.removeClass(o.hoverClassName);
							oDiv.hide();
							$(oDiv[index]).show();
						});
					};

					if (o.isClick == true) {
						oLiClick();
					} else if (o.isHover == true) {
						oLiHover();
					}

					function addType(vals) {

					}

				});
			}
		});
	})(jQuery);

	exports.base2Str = function(unicode) {
		var str = '';
		for ( var i = 0, len = unicode.length; i < len; ++i) {
			str += String.fromCharCode(unicode[i]);
		}
		return str;
	};

	exports.isAllowedAttach = function(sFile, sUploadImagesExt) {
		var sExt = sFile.match(/\.[^\.]*$/);
		if (sExt) {
			sExt = sExt[0].toLowerCase();
		} else {
			return false;
		}
		if (sUploadImagesExt.indexOf(sExt + ' ') != -1) {
			return true;
		}
		return false;
	};
	
	var maxsize = 1 * 1024 * 1024;
	var errMsg = "上传的附件文件不能超过1M！！！";
	var browserCfg = {};
	var ua = window.navigator.userAgent;
	if (ua.indexOf("MSIE") >= 1) {
		browserCfg.ie = true;
	} else if (ua.indexOf("Firefox") >= 1) {
		browserCfg.firefox = true;
	} else if (ua.indexOf("Chrome") >= 1) {
		browserCfg.chrome = true;
	}
	
	exports.checkfile = function () {
		try {
			var obj_file = document.getElementById("photo");
			if (obj_file.value == "") {
				top.layer.msg("请先选择上传文件", 1, -1);
				return false;
			}
			var filesize = 0;
			if (browserCfg.firefox || browserCfg.chrome) {
				filesize = obj_file.files[0].size;
			} else if (browserCfg.ie) {
				var obj_img = document.getElementById('photo');
				obj_img.dynsrc = obj_file.value;
				if (obj_img.fileSize) {
					filesize = obj_img.fileSize;
				} else if (obj_img.files && obj_img.files[0]
						&& obj_img.files[0].size) {
					filesize = obj_img.files[0].size;
				}
			} else {
				return true;
			}
			if (!filesize || filesize == -1) {
				return true;
			} else if (filesize > maxsize) {
				top.layer.msg(errMsg, 1, -1);
				return false;
			} else {
				return true;
			}
		} catch (e) {
			alert(e);
		}
		return true;
	};
	
	exports.checkfileById = function (id) {
		try {
			var obj_file = document.getElementById(id);
			if (obj_file.value == "") {
				top.layer.msg("请先选择上传文件", 1, -1);
				return false;
			}
			var filesize = 0;
			if (browserCfg.firefox || browserCfg.chrome) {
				filesize = obj_file.files[0].size;
			} else if (browserCfg.ie) {
				var obj_img = document.getElementById(id);
				obj_img.dynsrc = obj_file.value;
				if (obj_img.fileSize) {
					filesize = obj_img.fileSize;
				} else if (obj_img.files && obj_img.files[0]
						&& obj_img.files[0].size) {
					filesize = obj_img.files[0].size;
				}
			} else {
				return true;
			}
			if (!filesize || filesize == -1) {
				return true;
			} else if (filesize > maxsize) {
				top.layer.msg(errMsg, 1, -1);
				return false;
			} else {
				return true;
			}
		} catch (e) {
			alert(e);
		}
		return true;
	};
	
	
});