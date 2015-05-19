package com.zte.weibo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.zte.im.bean.TTwitter;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.Page;
import com.zte.weibo.bean.User;
import com.zte.weibo.common.constant.TwitterConstant;
import com.zte.weibo.common.util.excel.ExcelUtil;
import com.zte.weibo.databinder.MicroblogBinder;
import com.zte.weibo.service.AccountService;
import com.zte.weibo.service.MicroblogService;

@Service
public class MicroblogServiceImpl implements MicroblogService {

	
	private static Logger logger = LoggerFactory.getLogger(MicroblogServiceImpl.class);
	@Resource
	private AccountService accountService;
	
	@Override
	public String export(Long[] ids) throws Exception {
		// 序号、分享内容、发布账号、分享类型、发布时间、转发数、赞数、评论数。
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Workbook workbook = null;
		workbook = new XSSFWorkbook(new FileInputStream(new File(this
				.getClass().getResource("/").getPath()
				+ "microblog template.xlsx")));
		Sheet sheet = workbook.getSheetAt(0);

		int startRow = 1;// 从第二行开始写入数据

		BasicDBObject query = new BasicDBObject();
		if (null != ids && ids.length > 0) {
			query.put(TwitterConstant.FILED_NAME_ID,
					new BasicDBObject("$in", Arrays.asList(ids)));
		}
		List<String> queryArray = new ArrayList<String>();
		queryArray.add(TwitterConstant.DEL_CODE);
		query.put(TwitterConstant.FILED_NAME_STATUS, new BasicDBObject("$nin",queryArray));//过滤掉已经删除的分享

		List<TTwitter> list = null;
		DBCursor cur = null;
		try {
			cur = MongoDBSupport.getInstance().queryCollectionByParam(
					Constant.TWITTER_TWITTER, query, new BasicDBObject(TwitterConstant.FILED_NAME_CREATETIME, -1));//按照创建时间降序排列
			if (cur != null) {
				Type type = new TypeToken<List<TTwitter>>() {
				}.getType();
				list = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
			}
		} catch (Exception e) {
			logger.error("", e);
			logger.error(e.getMessage());
		}finally{
			if(null != cur){
				cur.close();
			}
		}
		
		handlerBlogContent(list);
		
		if (null != list && !list.isEmpty()) {
			for (TTwitter temp : list) {
				String tempValue = null;
				int startCell = 0; // 从第一个单元格开始写入
				// 序号、分享内容、发布账号、分享类型、发布时间、转发数、赞数、评论数。
				if(null != temp){
					Row row = sheet.createRow((short) startRow++);
					row.createCell(startCell++).setCellValue(startRow-1);
					tempValue = StringUtils.isNotEmpty(temp.getSearchContent())
							? temp.getSearchContent().trim()
							: "";
					tempValue = StringUtils.isNotEmpty(tempValue)
							? tempValue
							: StringUtils.isNotEmpty(temp.getImgSrc())
								? "分享图片"
								:"该分享无内容"
							;		
					
					row.createCell(startCell++).setCellValue(tempValue);
					User sendUser = accountService.getByUid(temp.getUserId());
					if(null != sendUser && StringUtils.isNotEmpty(sendUser.getName())){
						tempValue = sendUser.getName();
					}else {
						tempValue = "用户已经删除";
					}
					row.createCell(startCell++).setCellValue(tempValue);
					tempValue = StringUtils.isNotEmpty(temp.getTwitterType())?TwitterConstant.weiboType.get(temp.getTwitterType()):"";
					row.createCell(startCell++).setCellValue(tempValue);
					row.createCell(startCell++).setCellValue(null != temp.getCreateTime()?sdf.format(new Date(temp.getCreateTime())):"");
					row.createCell(startCell++).setCellValue(null != temp.getForwardNum()?temp.getForwardNum():0);
					row.createCell(startCell++).setCellValue(null != temp.getSupportNum() ? temp.getSupportNum() : 0);
					row.createCell(startCell++).setCellValue(null != temp.getCommentNum() ? temp.getCommentNum() :0);
				}
				
			}
		}
		return ExcelUtil.saveExcel(workbook, "分享导出");
	}

	@Override
	public void delByIds(Long[] ids) throws Exception {
		if(null == ids || ids.length<1){
			logger.error("删除分享时，传入的id数组为空！");
			throw new RuntimeException("删除分享时，传入的id数组为空！");
		}
		
		BasicDBObject query = new BasicDBObject();
		query.put(TwitterConstant.FILED_NAME_ID, new BasicDBObject("$in", Arrays.asList(ids)));
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(TwitterConstant.FILED_NAME_STATUS, TwitterConstant.DEL_CODE);
		MongoDBSupport.getInstance().updateCollection(Constant.TWITTER_TWITTER, query, new BasicDBObject("$set",dbObject));
		
	}

	@Override
	public List<TTwitter> search(MicroblogBinder microblogBinder,Page page)throws Exception {
		List<TTwitter> list = null;
		microblogBinder = null == microblogBinder?new MicroblogBinder():microblogBinder;
		
		BasicDBObject query = new BasicDBObject();
		List<String> queryArray = new ArrayList<String>();
		queryArray.add(TwitterConstant.DEL_CODE);
		query.put(TwitterConstant.FILED_NAME_STATUS, new BasicDBObject("$nin",queryArray));//过滤掉已经删除的分享

		if(StringUtils.isEmpty(microblogBinder.getType())){
			microblogBinder.setType("0");//默认查全部
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		List<BasicDBObject> timeQuery = new ArrayList<BasicDBObject>();
		if(StringUtils.isNotEmpty(microblogBinder.getTimeStart())){
			Date start = sdf.parse(microblogBinder.getTimeStart());
			timeQuery.add(new BasicDBObject(TwitterConstant.FILED_NAME_CREATETIME, new BasicDBObject("$gte",start.getTime())));
		}
		if(StringUtils.isNotEmpty(microblogBinder.getTimeEnd())){
			Date end = sdf.parse(microblogBinder.getTimeEnd());
			timeQuery.add(new BasicDBObject(TwitterConstant.FILED_NAME_CREATETIME, new BasicDBObject("$lte",end.getTime())));
		}
		if(null != timeQuery && !timeQuery.isEmpty()){
			query.put("$and", timeQuery);
		}
		
		/********************拼接不同查询类型的过滤条件 start******************************/
		if(microblogBinder.getType().equals("0")){//搜全部
			if(StringUtils.isNotEmpty(microblogBinder.getKeyword())){
				Pattern pattern = Pattern.compile("^.*" + microblogBinder.getKeyword()+ ".*$", Pattern.CASE_INSENSITIVE); 
				BasicDBObject [] orquery = new BasicDBObject[2];
				
				orquery[0] = new BasicDBObject();
				orquery[0].put(TwitterConstant.FILED_NAME_SEARCHCONTENT, pattern);
				
				//通过关键字查询用户ID
				User queryUser = new User();
				queryUser.setName(microblogBinder.getKeyword());
				Page userPage = new Page(Integer.MAX_VALUE, 1);
				List<User> users = accountService.getList(queryUser, userPage);
				List<Long> userids = new ArrayList<Long>();
				if(null != users && !users.isEmpty()){
					for (User user : users) {
						if(null != user.getUid()){
							userids.add(user.getUid());
						}
					}
				}
				//将查询的用户ID拼入到查询条件中
				orquery[1] = new BasicDBObject();
				orquery[1].put(TwitterConstant.FILED_NAME_USERID, new BasicDBObject("$in",userids));
				
				query.put("$or",Arrays.asList(orquery) );//通过分享内容和发布用户联合查询
			}
		}else if(microblogBinder.getType().equals("1")){//搜分享
			if(StringUtils.isNotEmpty(microblogBinder.getKeyword())){
				Pattern pattern = Pattern.compile("^.*" + microblogBinder.getKeyword()+ ".*$", Pattern.CASE_INSENSITIVE); 
				query.put(TwitterConstant.FILED_NAME_SEARCHCONTENT, pattern);
			}
		}else if (microblogBinder.getType().equals("2")){//搜用户
			//通过关键字查询用户ID
			User queryUser = new User();
			queryUser.setName(microblogBinder.getKeyword());
			Page userPage = new Page(Integer.MAX_VALUE, 1);
			List<User> users = accountService.getList(queryUser, userPage);
			List<Long> userids = new ArrayList<Long>();
			if(null != users && !users.isEmpty()){
				for (User user : users) {
					if(null != user.getUid()){
						userids.add(user.getUid());
					}
				}
			}
			//将查询的用户ID拼入到查询条件中
			query.put(TwitterConstant.FILED_NAME_USERID, new BasicDBObject("$in",userids));
		}else {
			
		}
		/********************拼接不同查询类型的过滤条件 end******************************/
		
		logger.info(query.toString());
		
		page.setCountdate(MongoDBSupport.getInstance().getCount(Constant.TWITTER_TWITTER, query).intValue());
		//DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(Constant.TWITTER_TWITTER, page, query, null);
		DBCursor cur = null;
		try {
			cur = MongoDBSupport.getInstance().queryCollectionByParam(
					Constant.TWITTER_TWITTER, page, query,
					new BasicDBObject(TwitterConstant.FILED_NAME_CREATETIME, -1));//发布时间降序
			if (cur != null) {
				Type type = new TypeToken<List<TTwitter>>() {
				}.getType();
				list = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
			}
		} catch (Exception e) {
			logger.error("", e);
			logger.error(e.getMessage());
			throw e;
		}finally{
			if(null != cur){
				cur.close();
			}
		}
		
//		handlerBlogContent(list);
		return list;
	}

	private void handlerBlogContent(List<TTwitter> list){
		if(null != list && !list.isEmpty()){
			for (TTwitter temp : list) {
				handlerBlogContent(temp);
			}
		}
	}
	private void handlerBlogContent(TTwitter twitter){
		String pattern_user = "\u0020mentionUserId=[\\S&&[^\u0020]]+";
		String pattern_poundid = "\u0020poundid=[\\S&&[^\u0020]]+";
		if(null != twitter && StringUtils.isNotEmpty(twitter.getTwitterContent())){
			twitter.setTwitterContent(twitter.getTwitterContent().replaceAll(pattern_user, "").replaceAll(pattern_poundid, ""));
//			twitter.setTwitterContent(StringUtil.subStrBySize(twitter.getTwitterContent(), 20));
		}
	}

	@Override
	public TTwitter getDetailByTwitterId(Long twitterId) throws Exception {
		TTwitter twitter = null;
		if(null == twitterId){
			logger.error("查看分享详情时，传入的ID为空");
			throw new RuntimeException("查看分享详情时，传入的ID为空");
		}
		TwitterServiceImpl twitterServiceImpl = TwitterServiceImpl.getInstance();
		twitter = twitterServiceImpl.findTwitter(twitterId);
		if(null != twitter && null != twitter.getTwitterId()){
			//获取分享用户信息
			if(null != twitter.getUserId()){
				User user = accountService.getByUid(twitter.getUserId());
				if (user == null) {
					twitter.setUserName("用户已被删除！");
					twitter.setBigpicurl("");
					twitter.setMinipicurl("");
				} else {
					twitter.setUserName(user.getName());
					twitter.setBigpicurl(user.getBigpicurl());
					twitter.setMinipicurl(user.getMinipicurl());
				}
			}
			handlerBlogContent(twitter);
		}else {
			logger.error(String.format("未找到%s对应的分享信息", twitterId));
			throw new RuntimeException(String.format("未找到%s对应的分享信息", twitterId));
		}
		return twitter;
	}
	
}
