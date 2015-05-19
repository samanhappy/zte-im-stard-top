package com.zte.controller;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mongodb.DBObject;
import com.zte.databinder.SysLogBinder;
import com.zte.im.mybatis.bean.page.SysLogPageModel;
import com.zte.im.protocol.Page;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.ISysLogService;
import com.zte.im.service.impl.SysLogServiceImpl;

@Controller
public class SysLogController extends ResBase {

	ISysLogService service = SysLogServiceImpl.getInstance();
	
	private final static Logger logger = LoggerFactory
			.getLogger(SysLogController.class);

	@RequestMapping(value = "queryLog")
	@ResponseBody
	public String queryLog(SysLogBinder binder) {
		if (binder.getType() == null || "".equals(binder.getType())) {
			return getErrorRes("type不能为空");
		}
		ResponseMain main = new ResponseMain();
		SysLogPageModel fpm = service.queryLog(binder);

		if (fpm != null && fpm.getLogList() != null
				&& fpm.getLogList().size() > 0) {
			for (DBObject obj : fpm.getLogList()) {
				try {
					if (obj.containsField("content")) {
						obj.put("content",
								Base64.encodeBase64String(obj.get("content")
										.toString().getBytes("utf-8")));
					}
				} catch (UnsupportedEncodingException e) {
					logger.error("", e);
				}
			}

		}

		main.setSyslog(fpm);
		main.setItem(fpm.getLogList());

		Page page = new Page();
		page.setCurrentPage(fpm.getCurrentPage());
		page.setPages(fpm.getTotalPage());
		page.setPernum(fpm.getPageSize());
		page.setStart(fpm.getFirst());
		page.setEnd(fpm.getLast());
		page.setTotal(fpm.getTotalRecord());
		main.setPage(page);

		String resultJson = JSON.toJSONString(main,
				SerializerFeature.DisableCircularReferenceDetect);
		return resultJson;
	}

}
