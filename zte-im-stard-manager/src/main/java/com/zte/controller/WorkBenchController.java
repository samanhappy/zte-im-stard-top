package com.zte.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mongodb.DBObject;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.IWorkImgService;
import com.zte.im.service.impl.WorkImgServiceImpl;

@Controller
public class WorkBenchController extends ResBase {

	private final static Logger logger = LoggerFactory.getLogger(WorkBenchController.class);

	@RequestMapping(value = "listWorkImg")
	@ResponseBody
	public String listWorkImg() {
		IWorkImgService service = WorkImgServiceImpl.getInstance();
		List<DBObject> list = service.listWorkImg();
		ResponseMain main = new ResponseMain();
		main.setItem(list);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "saveWorkImg")
	@ResponseBody
	public String saveWorkImg(@RequestParam("imgList") String imgList) {
		IWorkImgService service = WorkImgServiceImpl.getInstance();
		service.saveWorkImg(imgList);
		return getSuccessRes();
	}

}
