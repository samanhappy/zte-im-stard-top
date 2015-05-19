package com.mongo.test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.im.bean.News;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.INewsService;
import com.zte.im.service.impl.NewsServiceImpl;
import com.zte.im.util.Constant;

public class TestNews {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestNews.class);

	private INewsService service = NewsServiceImpl.getInstance();
	private MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();

	@Before
	public void init() {
		mongoDBSupport.dropOneCollection(Constant.NEWS);
		for (int i = 0; i < 20; i++) {
			News news = new News();
			news.setId(UUID.randomUUID().toString());
			news.setTitle("北京三老外推自行车闯地铁" + "_test" + i);
			news.setContent("2014年10月29日，北京，安检处被拦阻后，老外调转车头，冲向地铁出站闸机，欲将自行车抬入出站口闸门。女乘务员多次劝阻未果，怒斥他们“别给你们国家丢脸”！ ");
			news.setAuthor("admin");
			news.setDate(new Date().getTime());
			news.setType("0");
			news.setFlag("1");
			service.saveNewsObject(news);
		}

	}

	@Test
	public void createTest() {
		List<News> list = service.queryNewsByParams(0, null, null);
		for (News n : list) {
			logger.info(n.getTitle());
		}
	}
}
