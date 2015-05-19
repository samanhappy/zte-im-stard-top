package com.zte.weibo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zte.im.bean.TGroup;
import com.zte.im.util.Page;
import com.zte.weibo.common.constant.GroupConstant;
import com.zte.weibo.databinder.CircleBinder;
import com.zte.weibo.protocol.ResponseMain;
import com.zte.weibo.service.CircleService;

@Controller
@RequestMapping("circle")
public class CircleController {

	private static Logger logger = LoggerFactory
			.getLogger(CircleController.class);
	
	@Resource
	private CircleService circleService;
	
	@RequestMapping(value="list",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getList(@ModelAttribute(value="circle") CircleBinder circle){
		
		ResponseMain main = new ResponseMain();
		
		Page page = new Page(circle.getpSize(),circle.getcPage());
		
		List<TGroup> tGroups = circleService.getList(circle, page);
		
		if(null != tGroups && !tGroups.isEmpty()){
			for (TGroup tGroup : tGroups) {
				if(null != tGroup && StringUtils.isEmpty(tGroup.getGroupStatus())){
					tGroup.setGroupStatus(GroupConstant.STATUS_ON);//设置默认展示的 状态为启用
				}
			}
		}
		
		main.setItem(tGroups);
		main.setPage(page);
		String resultJson = JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd");
		
		logger.info(resultJson);
		return resultJson;
	}
	
	@RequestMapping(value="modifyStatus",produces="application/json;charset=utf-8")
	@ResponseBody
	public String modifyStatus(Long[] groupId,String status,String stopDesc){
		ResponseMain main = new ResponseMain();
		try {
			circleService.modifyGroupStatus(groupId, status,stopDesc);
			main.setResult(true);
		} catch (Exception e) {
			main.setResult(false);
			main.setMsg(e.getMessage());
			logger.error("", e);
		}
		String result = JSON.toJSONString(main);
		logger.info(result);
		return result;
	}
	
	@RequestMapping(value="export",produces="application/json;charset=utf-8")
	@ResponseBody
	public String export(Long[] groupId){
		ResponseMain main = new ResponseMain();
		
		String fileUrl=null;
		try {
			fileUrl = circleService.export(groupId);
		} catch (Exception e) {
			logger.error("", e);
			main.setResult(false);
			main.setMsg("导出圈子出错！");
		}
		if (StringUtils.isEmpty(fileUrl)) {
			main.setResult(false);
			main.setMsg("导出圈子文件路径为空！");
		}else{
			main.setResult(true);
			main.setFileUrl(fileUrl);
		}
		String result = JSON.toJSONString(main);
		logger.info(result);
		return result;
	}
	
	@RequestMapping(value="getByGroupId",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getByGroupId(Long groupId){
		ResponseMain main = new ResponseMain();
		try {
			TGroup group = circleService.getByGroupId(groupId);
			if(null != group ){
				group.setQzcys(circleService.getQZCYS(groupId));
				group.setQzwbs(circleService.getQZWBS(groupId));
			}
			main.setData(group);
			main.setResult(true);
		} catch (Exception e) {
			logger.error("", e);
			main.setResult(false);
			main.setMsg(e.getMessage());
		}
		String result = JSON.toJSONString(main);
		return result;
	}
}
