package com.zte.controller;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mongodb.DBObject;
import com.zte.databinder.FeedbackBinder;
import com.zte.im.bean.Feedback;
import com.zte.im.mybatis.bean.page.FeedbackPageModel;
import com.zte.im.protocol.Page;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.IFeebackService;
import com.zte.im.service.impl.FeedbackServiceImpl;
import com.zte.im.util.StringUtil;

@Controller
public class FeedBackController extends ResBase {

	IFeebackService fbService = FeedbackServiceImpl.getInstance();

	private static Logger logger = LoggerFactory
			.getLogger(FeedBackController.class);

	@RequestMapping(value = "listFeedback")
	@ResponseBody
	public String listFeedback(FeedbackBinder binder) {
		ResponseMain main = new ResponseMain();
		FeedbackPageModel fpm = fbService.list(binder);
		if (fpm != null && fpm.getFeedbackList() != null) {
			for (DBObject obj : fpm.getFeedbackList()) {
				if (obj.containsField("content")) {
					try {
						obj.put("content", Base64
								.encodeBase64String(StringUtil.subStrBySize(
										obj.get("content").toString(), 40)
										.getBytes("utf-8")));
					} catch (UnsupportedEncodingException e) {
						logger.error("", e);
					}
				}
			}
		}

		main.setFeedbacks(fpm);
		main.setNewsList(fpm.getFeedbackList());
		
		Page page = new Page();
		page.setCurrentPage(fpm.getCurrentPage());
		page.setPages(fpm.getTotalPage());
		page.setPernum(fpm.getPageSize());
		page.setStart(fpm.getFirst());
		page.setEnd(fpm.getLast());
		page.setTotal(fpm.getTotalRecord());
		main.setPage(page);
		String resultJson = JSON.toJSONString(main, SerializerFeature.DisableCircularReferenceDetect);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "getFeedBack")
	@ResponseBody
	public String getFeedBack(@RequestParam("id") Long id) {
		ResponseMain main = new ResponseMain();
		Feedback fb = fbService.getFeedback(id);
		if (fb != null && fb.getContent() != null) {
			try {
				fb.setContent(Base64.encodeBase64String(fb.getContent()
						.toString().getBytes("utf-8")));
			} catch (UnsupportedEncodingException e) {
				logger.error("", e);
			}
		}
		main.setData(fb);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}
}
