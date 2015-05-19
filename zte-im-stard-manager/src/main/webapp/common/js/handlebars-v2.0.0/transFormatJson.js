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

// 格式:2014-08-15
Handlebars.registerHelper("dateFormat",function(value){
	function formatDate(now) { 
		var year=now.getFullYear();
		var month=now.getMonth()+1; 
		var date=now.getDate(); 
		return year+"-"+month+"-"+date; 
	} 
	if(value==null ||value==''){
		return '';
	}
	var d = new Date(value);
	return formatDate(d);
});

//员工列表操作
Handlebars.registerHelper("listOptFun",function(id,usable,cn,roleName){
	if(usable == 'true'){
		return new Handlebars.SafeString('<div class="optionBtn"><i class="iconfont">&#xe618;</i><ul><em></em><li><a href="javascript:;" id="stateBtn" data-usable="false" class="userAbleManage" data-id="'+id+'" >停用</a></li><li><a href="javascript:;" id="resetPwdBtn" data-usable="false" data-id="'+id+'" data-name="'+cn+'" >重置密码</a></li><li><a href="javascript:;" id="settingBtn" class="userPrivManage" data-id="'+id+'" data-name="'+cn+'" data-post="'+roleName+'" >保护设置</a></li></ul></div>');
	}else{
		return new Handlebars.SafeString('<div class="optionBtn"><i class="iconfont">&#xe618;</i><ul><em></em><li><a href="javascript:;" id="stateBtn" data-usable="true" class="userAbleManage" data-id="'+id+'" >启用</a></li><li><a href="javascript:;" id="resetPwdBtn" data-usable="false" data-id="'+id+'" data-name="'+cn+'" >重置密码</a></li><li><a href="javascript:;" id="settingBtn" class="userPrivManage" data-id="'+id+'"  data-name="'+cn+'" data-post="'+roleName+'" >保护设置</a></li></ul></div>');
	}
});

//判断是否相等
Handlebars.registerHelper('ifCond', function(v1, v2, options) {
	  if(v1 === v2) {
	    return options.fn(this);
	  }
	  return options.inverse(this);
});

Handlebars.registerHelper("inc", function(value, options)
{
	return parseInt(value) + 1;
});

Handlebars.registerHelper("base2Str", function(content)
{
	return new Handlebars.SafeString(base2Str(BASE64.decoder(content)));
});

function base2Str(unicode) {
	var str = '';
	for ( var i = 0, len = unicode.length; i < len; ++i) {
		str += String.fromCharCode(unicode[i]);
	}
	return str;
}

//信息反馈内容截取
Handlebars.registerHelper("hideFeedBackContent",function(id,content){
	content = base2Str(BASE64.decoder(content));
	if(content.length > 30){
		return new Handlebars.SafeString('<a href="javascript:;" title="查看" id="feedBackShow" data-id="'+id+'" >'+content.substr(0,30)+'…</a>');
	}else{
		return new Handlebars.SafeString('<a href="javascript:;" title="查看" id="feedBackShow" data-id="'+id+'" >'+content+'</a>');
	}
});

/*
 *企业新闻
 *2014-12-23
*/
//新闻标题
Handlebars.registerHelper("hideNewsTitle",function(title){
	if(title.length > 30){
		return title.substr(0,30)+'...';
	}else{
		return title;
	}
});

//新闻操作
Handlebars.registerHelper("newsOptFun",function(id){
    return new Handlebars.SafeString('<a href="editNews.jsp?id='+id+'" title="编辑" class="editNews">编辑</a><a href="javascript:;" id="delNews" class="removeNews" date-val="'+id+'" title="删除">删除</a>');
});
/*
 *客户端
 *2014-12-22
*/
//客户端状态显示
Handlebars.registerHelper("clientActiveFun",function(value){
	if(value=="off"){
        return "暂停";
    }else if(value=="on"){
        return "激活";
    }
});

//强制更新显示
Handlebars.registerHelper("clientUpdateFun",function(value){
	if(value=="1"){
        return "是";
    }else{
        return "否";
    }
});

//客户端状态连接
Handlebars.registerHelper("clientActiveLinkFun",function(id,isActive){
	if(isActive=="off"){
        return new Handlebars.SafeString('<a href="javascript:;" title="激活" id="clientId" data-id="'+id+'" data-isActive="on" >激活</a>');
    }else if(isActive=="on"){
        return new Handlebars.SafeString('<a href="javascript:;" title="暂停" id="clientId" data-id="'+id+'" data-isActive="off" >暂停</a>');
    }
});