package com.zte.im.user.execute;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.News;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseListMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.INewsService;
import com.zte.im.service.impl.NewsServiceImpl;
import com.zte.im.util.Constant;

/**
 * 获取后台发布的新闻列表信息
 * @author samanhappy
 *
 */
public class ListNewsExecute extends AbstractValidatoExecute {

	private INewsService newsService = NewsServiceImpl.getInstance();

	@Override
	public String doProcess(JSONObject json) {
		List<News> newsList = newsService.queryNewsByParams(0, null, null);
		ResponseListMain main = new ResponseListMain();
		main.setItem(newsList);
		Res res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		main.setRes(res);
		return JSON.toJSONString(main);
	}

}
