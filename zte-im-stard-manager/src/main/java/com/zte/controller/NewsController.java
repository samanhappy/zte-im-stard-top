package com.zte.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.sample.mqttv3app.MqttPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zte.databinder.NewsBinder;
import com.zte.im.bean.News;
import com.zte.im.bean.PushBean;
import com.zte.im.protocol.Page;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.INewsService;
import com.zte.im.service.impl.NewsServiceImpl;
import com.zte.util.system.FileSaver;

@Controller
public class NewsController {

	private INewsService newsService = NewsServiceImpl.getInstance();

	private static Logger logger = LoggerFactory.getLogger(NewsController.class);

	@RequestMapping(value = "editNews")
	@ResponseBody
	public String editNews(@RequestParam("id") String id) {
		News news = newsService.getNewsById(id);
		try {
			if (StringUtils.isNotEmpty(news.getContent())) {
				news.setContent(Base64.encodeBase64String(news.getContent().getBytes("utf-8")));
			}
			if (StringUtils.isNotEmpty(news.getContent4Text())) {
				news.setContent4Text(Base64.encodeBase64String(news.getContent4Text().getBytes("utf-8")));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}
		ResponseMain main = new ResponseMain();
		main.setData(news);
		String resultJson = JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd");
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "delNews")
	@ResponseBody
	public String delNews(@ModelAttribute("model") NewsBinder binder) {
		newsService.delNewsObject(binder.getNews_id());
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd");
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "getNewsList")
	@ResponseBody
	public String getNewsList(@RequestParam("days") String days) {
		List<News> newsList = newsService.queryNewsByParams(Integer.valueOf(days), null, null);

		if (newsList != null && newsList.size() > 0) {
			for (News news : newsList) {
				try {
					if (StringUtils.isNotEmpty(news.getContent())) {
						news.setContent(Base64.encodeBase64String(news.getContent().getBytes("utf-8")));
					}
					if (StringUtils.isNotEmpty(news.getContent4Text())) {
						news.setContent4Text(Base64.encodeBase64String(news.getContent4Text().getBytes("utf-8")));
					}
				} catch (UnsupportedEncodingException e) {
					logger.error("", e);
				}
			}
		}

		ResponseMain main = new ResponseMain();

		Page page = new Page();
		page.setCurrentPage(1);
		page.setPages(1);
		page.setPernum(0);
		page.setStart(0);
		page.setEnd(0);
		page.setTotal(0);
		main.setPage(page);
		main.setItem(newsList);
		String resultJson = JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd");
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "saveNews")
	@ResponseBody
	public String saveNews(@ModelAttribute("model") NewsBinder binder, HttpServletRequest request) {
		try {
			News news = null;
			if (null != binder.getNews_id() && !"".equals(binder.getNews_id())) {
				// 更新
				news = newsService.getNewsById(binder.getNews_id());
			} else {
				// 新增
				news = new News();
				news.setId(UUID.randomUUID().toString());
			}
			news.setTitle(binder.getNews_title());
			news.setContent(binder.getNews_con());
			logger.info(binder.getNews_con_text());
			news.setContent4Text(binder.getNews_con_text());
			news.setDate(new Date().getTime());
			news.setFlag("1");
			news.setType(binder.getNews_type());
			news.setVideoUrl(binder.getNews_videoUrl());
			news.setAuthor(request.getSession().getAttribute("userid").toString());

			// 取第一张图片为imgUrl
			String content = news.getContent();
			if (content != null && content.indexOf("img src=") != -1) {
				news.setImgUrl(content.substring(content.indexOf("img src=") + 9, content.indexOf("\" alt=\"\" />")));
			}

			if (!com.mysql.jdbc.StringUtils.isNullOrEmpty(news.getHtmlFilePath())) {
				deleteFile(news.getHtmlFilePath());
			}
			String htmlFilePath = saveHtmlFile(binder.getNews_con(), request);
			news.setHtmlFilePath(htmlFilePath);

			if (null != binder.getNews_id() && !"".equals(binder.getNews_id())) {
				// 更新
				newsService.updateNewsObject(news);
			} else {
				logger.info(news.getContent4Text());
				// 新增
				newsService.saveNewsObject(news);

				// 推送
				PushBean bean = new PushBean();
				bean.setTitle(news.getTitle());
				bean.setPublishtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(news.getDate())));
				bean.setPublisher(news.getAuthor());
				bean.setImgurl(news.getImgUrl());
				bean.setContent(news.getContent4Text());
				bean.setNewsurl(news.getHtmlFilePath());
				bean.setContenttype("4");
				MqttPublisher.publish(bean);
			}

			ResponseMain main = new ResponseMain();
			String resultJson = JSON.toJSONString(main);
			return resultJson;
		} catch (Exception e) {
			return "error";
		}
	}

	private String saveHtmlFile(String content, HttpServletRequest request) {
		String fileSaverUrl = null;
		try {
			fileSaverUrl = FileSaver.save(content, new Date().getTime() + ".html");
		} catch (Exception e) {
			logger.error("", e);
		}
		return fileSaverUrl;
	}

	private void deleteFile(String fileName) {
		File file = new File(fileName);
		try {
			file.delete();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		String content = "<p>\n\twKgNyFSiXiKALwb-AAAJZkziLeo407.png\" alt=\"\" />&nbsp;这是新闻很厉害。怎么办\n</p>\n<p>\n\t<br />\n</p>\n<p>\n\t<br />\n</p>";
		if (content != null && content.indexOf("img src=") != -1) {
			System.out.println(content.substring(content.indexOf("img src=") + 9, content.indexOf("\" alt=\"\" />")));
		}
	}
}
