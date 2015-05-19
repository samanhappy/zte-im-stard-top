//时间转换  格式:2014-08-15 00:00:00
Handlebars.registerHelper("transFormatDate",function(value){
	function formatDate(now) { 
		var year=now.getFullYear();
		var month=now.getMonth()+1; 
		var date=now.getDate(); 
		var hour=now.getHours(); 
		var minute=now.getMinutes(); 
		var second=now.getSeconds(); 
		if(second < 10)
		{
			second = '0' + second;
		}
		if(minute < 10)
		{
			minute = '0' + minute;	
		}
		return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
	} 
	if(value==null ||value==''){
		return '';
	}
	var d = new Date(value);
	return formatDate(d);
});

//时间转换  格式:2014-08-15 00:00
Handlebars.registerHelper("transFormatShortDate",function(value){
	function formatDate(now) { 
		var year=now.getFullYear();
		var month=now.getMonth()+1; 
		var date=now.getDate(); 
		var hour=now.getHours(); 
		var minute=now.getMinutes(); 
		var second=now.getSeconds(); 
		if(second < 10)
		{
			second = '0' + second;
		}
		if(minute < 10)
		{
			minute = '0' + minute;	
		}
		return year+"-"+month+"-"+date+" "+hour+":"+minute; 
	} 
	if(value==null ||value==''){
		return '';
	}
	var d = new Date(value);
	return formatDate(d);
});

//账号管理列表序号
Handlebars.registerHelper("inc", function(value, options){
	return parseInt(value) + 1;
});

//账号管理列表状态显示
Handlebars.registerHelper("accountStateFun",function(value){
	if(value=="99"){
        return "停用";
    }else if(value=="00"){
        return "启用";
    }
});

//圈子管理列表状态显示
Handlebars.registerHelper("circleStateFun",function(value){
	if(value=="99"){
        return "停用";
    }else if(value=="00"){
        return "启用";
    }
});
