;(function(window, $){


	/**
	*	@param {Number} thHeight   表头高度
	*	@param {Number} tdHeight   表格每行的高度
	*/
	function pageFull(thHeight, tdHeight){
		thHeight = thHeight || 50;
		tdHeight = tdHeight || 50;
		var h = $(window).height() * 90 / 100 - 54 - 23 - 19 - 51 -28 -30;
		var hs = 0,
		rest = h - hs;
		
		lines = Math.floor((rest - thHeight) / tdHeight);
		return lines;
	}
	
	window.pageFull = pageFull;
})(window, jQuery);

;(function(window, $){


	/**
	*	@param {Number} thHeight   表头高度
	*	@param {Number} tdHeight   表格每行的高度
	*/
	function pageFull1(thHeight, tdHeight){
		thHeight = thHeight || 50;
		tdHeight = tdHeight || 50;
		var h = $(window).height() - 54 - 60 - 100 - 46 - 25 - 45;
		var hs = 0,
		rest = h - hs;
		
		lines = Math.floor((rest - thHeight) / tdHeight);
		return lines;
	}
	
	window.pageFull1 = pageFull1;
})(window, jQuery);