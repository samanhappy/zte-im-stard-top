package com.zte.weibo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zte.im.util.Page;
import com.zte.weibo.bean.User;
import com.zte.weibo.common.constant.UserConstant;
import com.zte.weibo.databinder.AccountBinder;
import com.zte.weibo.job.UserSyncJob;
import com.zte.weibo.protocol.ResBase;
import com.zte.weibo.protocol.ResponseMain;
import com.zte.weibo.service.AccountService;

@Controller
@RequestMapping(value="account")
public class AccountController extends ResBase  {

	private static Logger logger = LoggerFactory
			.getLogger(AccountController.class);
	
	private static long index = 1;//供测试用
	
	@Resource
	private AccountService accountService ;
	
	
	/**
	 * 查询账号列表
	 * @author syq	2015年1月15日
	 * @param account
	 * @return
	 */
	@RequestMapping(value="list", produces="application/json;charset=utf-8")
	@ResponseBody
	public String getList(@ModelAttribute(value="account") AccountBinder account,HttpServletResponse response){
//		RedisSupport.getInstance().del(Constant.T_DEPT_COLLECTION_NAME);
//		userSyncJob.execute();
		ResponseMain main = new ResponseMain();
		
		Page page = new Page(account.getpSize(),account.getcPage());
		
		User user = new User();
		user.setName(account.getName());
		user.setState(account.getState());
		List<User> accountList = accountService.getList(user, page);
		
		main.setItem(accountList);
		main.setPage(page);
		String resultJson = JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd");
		
		logger.info(resultJson);
		return resultJson;
	}
	
	@RequestMapping(value="getByUid",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getByUId(Long uid){
		ResponseMain main = new ResponseMain();
		try {
			User user = accountService.getByUid(uid);
			if(null != user && null != user.getUid()){
				user.setYcwbs(accountService.getYCWBS(user.getUid()));
				user.setZfwbs(accountService.getZFWFB(user.getUid()));
				user.setCjqzs(accountService.getCJQZS(user.getUid()));
				user.setCyqzs(accountService.getCYQZS(user.getUid()));
				if(null != user.getState()){
					user.setState(UserConstant.stateMap.get(user.getState()));
				}else {
					user.setState(UserConstant.stateMap.get(UserConstant.STATE_ON));
				}
			}
			main.setData(user);
			main.setResult(true);
		} catch (Exception e) {
			logger.error("", e);
			main.setResult(false);
			main.setMsg("查询用户详情时出错！");
		}
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}
	
	@RequestMapping(value="modifyUserState",produces="application/json;charset=utf-8")
	@ResponseBody
	public String modifyUserState(Long[] uid,String state,String stopDesc){
		ResponseMain main = accountService.modifyUserState(uid, state, stopDesc,null);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}
	
	@RequestMapping(value ="export",produces="application/json;charset=utf-8")
	@ResponseBody
	public String Export(Long[] uid){
		logger.info("账号导出开始...");
		ResponseMain main = new ResponseMain();
		String fileUrl=null;
		try {
			fileUrl = accountService.export(uid);
		} catch (Exception e) {
			logger.error("", e);
			main.setResult(false);
			main.setMsg("导出用户出错！");
		}
		if (StringUtils.isEmpty(fileUrl)) {
			main.setResult(false);
			main.setMsg("导出用户文件路径为空！");
		}else{
			main.setResult(true);
			main.setFileUrl(fileUrl);
		}
		
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}
	
	@RequestMapping(value ="setAllAttention",produces="application/json;charset=utf-8")
	@ResponseBody
	public String setAllAttention(Long[] uid){
		ResponseMain main = new ResponseMain();
		try {
			accountService.setAllAttention(uid);
			main.setResult(true);
		} catch (Exception e) {
			logger.error("", e);
			main.setMsg(e.getMessage());
			main.setResult(false);
		}
		String result = JSON.toJSONString(main);
		logger.info(result);
		return result;
	}
	@RequestMapping(value ="delAllAttention",produces="application/json;charset=utf-8")
	@ResponseBody
	public String delAllAttention(Long[] uid){
		ResponseMain main = new ResponseMain();
		try {
			accountService.delAllAttention(uid);
			main.setResult(true);
		} catch (Exception e) {
			logger.error("", e);
			main.setMsg(e.getMessage());
			main.setResult(false);
		}
		String result = JSON.toJSONString(main);
		logger.info(result);
		return result;
	}
	
	@RequestMapping(value ="setAttentionDept",produces="application/json;charset=utf-8")
	@ResponseBody
	public String setAttentionDept(){
		ResponseMain main = new ResponseMain();
		try {
			accountService.setAttentionDept();
			main.setResult(true);
		} catch (Exception e) {
			logger.error("", e);
			main.setMsg(e.getMessage());
			main.setResult(false);
		}
		String result = JSON.toJSONString(main);
		logger.info(result);
		return result;
	}
	
	@RequestMapping(value ="delAttentionDept",produces="application/json;charset=utf-8")
	@ResponseBody
	public String delAttentionDept(){
		ResponseMain main = new ResponseMain();
		try {
			accountService.delAttentionDept();
			main.setResult(true);
		} catch (Exception e) {
			logger.error("", e);
			main.setMsg(e.getMessage());
			main.setResult(false);
		}
		String result = JSON.toJSONString(main);
		logger.info(result);
		return result;
	}
	@RequestMapping(value = "import", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String importUser(@RequestParam("fileUrl") String fileUrl, HttpServletRequest request) {
		ResponseMain main = new ResponseMain();
		if (fileUrl == null) {
			return getErrorRes("fileUrl can not be null");
		}

		try {
			String error = accountService.importUserData(fileUrl);
			if (error != null) {
				return getErrorRes(error);
			}
			main.setResult(true);
		} catch (Exception e) {
			logger.error("", e);
			return getErrorRes(e.getMessage());
		}

		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}
	
	@RequestMapping(value="initData",produces="text/html;charset=utf-8")
	@ResponseBody
	public String initData(boolean isSetAttentionDept){
		ResponseMain main = new ResponseMain();
		try {
			accountService.initData();
			if (isSetAttentionDept) {
				accountService.setAttentionDept();
			}
			main.setResult(true);
			
		} catch (Exception e) {
			logger.error("", e);
			main.setResult(false);
			main.setMsg(e.getMessage());
		}
		String result = JSON.toJSONString(main);
		logger.info(result);
		return result;
	}
	
}
