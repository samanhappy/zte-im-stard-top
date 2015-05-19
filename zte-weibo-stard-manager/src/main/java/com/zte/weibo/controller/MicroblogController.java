package com.zte.weibo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zte.im.bean.TComment;
import com.zte.im.bean.TTwitter;
import com.zte.im.util.Page;
import com.zte.im.util.StringUtil;
import com.zte.weibo.bean.User;
import com.zte.weibo.common.constant.TwitterConstant;
import com.zte.weibo.databinder.MicroblogBinder;
import com.zte.weibo.protocol.ResBase;
import com.zte.weibo.protocol.ResponseMain;
import com.zte.weibo.service.AccountService;
import com.zte.weibo.service.CommentService;
import com.zte.weibo.service.MicroblogService;

@Controller
@RequestMapping(value="microblog")
public class MicroblogController extends ResBase {

	private static Logger logger = LoggerFactory
			.getLogger(MicroblogController.class);
	
	@Resource
	private MicroblogService microblogService;
	@Resource
	private AccountService accountService;
	@Resource
	private CommentService commentService;
	
	
	@RequestMapping(value="search",produces="application/json;charset=utf-8")
	@ResponseBody
	public String search(MicroblogBinder microblog){
		ResponseMain main = new ResponseMain();
		
		Page page = new Page(microblog.getpSize(), microblog.getcPage());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<TTwitter> list = null;
		try {
			
			list = microblogService.search(microblog,page);
			if(null != list && !list.isEmpty()){
				for (TTwitter temp : list) {
					if(null != temp){
						if(StringUtils.isNotEmpty(temp.getTwitterType())){
							temp.setTwitterType(TwitterConstant.weiboType.get(temp.getTwitterType()));
						}
						if(null != temp.getUserId()){
							User user = accountService.getByUid(temp.getUserId());
							if(null != user && StringUtils.isNotEmpty(user.getName())){
								temp.setUserName(user.getName());
							}else {
								temp.setUserName("用户已被删除");
							}
						}
						
						if(null != temp.getCreateTime()){
							temp.setCreateTimeStr(sdf.format(new Date(temp.getCreateTime())));
						}
						
						temp.setSearchContent(
								StringUtils.isNotEmpty(temp.getSearchContent()) 
								? temp.getSearchContent().trim()
								: ""
							);
						temp.setSearchContent(
								StringUtils.isNotEmpty(temp.getSearchContent())
								? StringUtil.subStrBySize(temp.getSearchContent(), 20)
								: StringUtils.isNotEmpty(temp.getImgSrc())
										? "分享图片"
										: "该分享无内容"
								);

//						temp.setTwitterContent(StringUtil.subStrBySize(temp.getTwitterContent(), 20));
					}
				}
			}
			main.setResult(true);
			
		} catch (Exception e) {
			logger.error("", e);
			main.setResult(false);
			main.setMsg(e.getMessage());
		}
		main.setItem(list);
		main.setPage(page);
		
		String result = JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd HH:mm:ss");
		logger.info(result);
		return result;
	}

	@RequestMapping(value="del",produces="application/json;charset=utf-8")
	@ResponseBody
	public String delMicroblog(Long[] id){
		ResponseMain main = new ResponseMain();

		try {
			microblogService.delByIds(id);
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
	
	@RequestMapping(value="export",produces="application/json;charset=utf-8")
	@ResponseBody
	public String export(Long[] id){
		ResponseMain main = new ResponseMain();
		
		String fileUrl=null;
		try {
			fileUrl = microblogService.export(id);
		} catch (Exception e) {
			logger.error("", e);
			main.setResult(false);
			main.setMsg("导出分享出错！");
		}
		if (StringUtils.isEmpty(fileUrl)) {
			main.setResult(false);
			main.setMsg("导出分享文件路径为空！");
		}else{
			main.setResult(true);
			main.setFileUrl(fileUrl);
		}
		
		String result = JSON.toJSONString(main);
		logger.info(result);
		return result;
	}
	
	@RequestMapping(value="getDetail",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getDetail(Long id){
		ResponseMain main = new ResponseMain();
		try {
			TTwitter twitter = microblogService.getDetailByTwitterId(id);
			if(null != twitter && null != twitter.getSourceId()){
				//查询源分享信息
				TTwitter sourceTwitter = microblogService.getDetailByTwitterId(twitter.getSourceId());
				twitter.setSourceTTwitter(sourceTwitter);
				//查询评论信息
//				List<TComment> comments = TwitterServiceImpl.getInstance().GetCommentsList(twitter.getTwitterId());
//				main.setItem(comments);
			}
			main.setData(twitter);
			main.setResult(true);
		} catch (Exception e) {
			logger.error("", e);
			logger.error(e.getMessage());
			main.setResult(false);
			main.setMsg(e.getMessage());
		}
		String result = JSON.toJSONString(main);
		logger.info(result);
		return result;
	}
	
	@RequestMapping("showDetail")
	public String showDetail(Long twitterId,HttpServletRequest request){
		try {
			TTwitter twitter = microblogService.getDetailByTwitterId(twitterId);
			if(null != twitter){
				//如果是转发分享，查询源分享信息
				if(StringUtils.isNotEmpty(twitter.getTwitterType()) && twitter.getTwitterType().equals(TwitterConstant.TWITTER_TYPE_ZF) && null != twitter.getSourceId()){
					TTwitter sourceTwitter = microblogService.getDetailByTwitterId(twitter.getSourceId());
					twitter.setSourceTTwitter(sourceTwitter);
					//设置页面显示的时间
					setShowTime(sourceTwitter);
				}
				//设置页面显示的可见范围
				twitter.setVisibleRange(TwitterConstant.visibleRange.get(twitter.getVisibleRange()));
				//设置页面显示的时间
				setShowTime(twitter);
			}
			request.setAttribute("twitter", twitter);
		} catch (Exception e) {
			logger.error("", e);
			logger.error(e.getMessage());
		}
		return "../module/microblog/detail.jsp";
	}
	/*
	 * 判断时间在1小时之内时，显示多少分钟之前;3小时之内1小时之外显示多少小时之前；否则显示时间(MM月dd日 HH:mm)
	 */
	private void setShowTime(TTwitter twitter){
		if(null != twitter && null != twitter.getCreateTime()){
			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
			Date date = new Date(twitter.getCreateTime());
			
			Date now = new Date();
			long seconds = (now.getTime() - date.getTime())/1000;
			if(seconds < 60 * 60){
				twitter.setCreateTimeStr(String.format("%d分钟之前", seconds/60));
			}else if(seconds < 3*60*60) {
				twitter.setCreateTimeStr(String.format("%d小时之前", seconds/(60*60)));
			}else {
				twitter.setCreateTimeStr(sdf.format(date));
			}
		}
	}
	
	@RequestMapping(value="getComments",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getComments(Long twitterId,MicroblogBinder microblogBinder){
		ResponseMain main = new ResponseMain();
		if(null == twitterId){
			return getErrorRes("传入的分享ID为空！");
		}
		Page page = new Page(microblogBinder.getpSize(), microblogBinder.getcPage());
		try {
			// 查询评论信息
			List<TComment> comments = commentService.getListByTwitterId(twitterId, page);
			//查询评论的用户信息
			if(null != comments && !comments.isEmpty()){
				List<Long> userIds = new ArrayList<Long>();
				for (TComment comment : comments) {
					if(null != comment && null != comment.getUserId()){
						userIds.add(comment.getUserId());
					}
				}
				//查询涉及到user到map 中
				List<User> users = accountService.getByUid(userIds.toArray(new Long[]{}));
				Map<Long, com.zte.im.bean.User> userMap = new HashMap<Long, com.zte.im.bean.User>();
				if(null != users && !users.isEmpty()){
					//将用户封装在Map中
					for (User user : users) {
						if(null != user && null != user.getUid()){
							com.zte.im.bean.User u1 = new com.zte.im.bean.User();
							//设置用户姓名
							u1.setName(StringUtils.isNotEmpty(user.getName())? user.getName():"");
							//设置用户头像
							u1.setMinipicurl(StringUtils.isNotEmpty(user.getMinipicurl())?user.getMinipicurl():"../../common/images/avatar.png");
							userMap.put(user.getUid(), u1);
						}else {
							
						}
					}
				}
				
				for (TComment comment : comments) {
					if(null != comment){
						if(null != userMap.get(comment.getUserId())){
							comment.setUser(userMap.get(comment.getUserId()));
						}else {
							//在评论中设置用户对象
							com.zte.im.bean.User delUser = new com.zte.im.bean.User();
							//设置用户姓名
							delUser.setName("用户已被删除");
							//设置用户头像
							delUser.setMinipicurl("../common/images/avatar.png");
							comment.setUser(delUser);
						}
					}
				}
				
			}
			main.setItem(comments);
			main.setResult(true);
		} catch (Exception e) {
			logger.error("", e);
			logger.error(e.getMessage());
			main.setResult(false);
			main.setMsg(e.getMessage());
		}
		main.setPage(page);
		String result = JSON.toJSONString(main);
		logger.info(result);
		return result;
	}
	
	
	@RequestMapping(value="delComment",produces="application/json;charset=utf-8")
	@ResponseBody
	public String delComment(Long id){
		ResponseMain main = new ResponseMain();
		try {
			commentService.delByCommentId(id);
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
}
