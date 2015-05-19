package com.zte.weibo.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.zte.im.bean.TComment;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.Page;
import com.zte.weibo.common.constant.CommentConstant;
import com.zte.weibo.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	@Override
	public void delByCommentId(Long commentId) throws Exception {
		if(null == commentId){
			logger.error("删除评论时，传入的ID为空");
			throw new RuntimeException("删除评论时，传入的ID为空");
		}
		BasicDBObject query = new BasicDBObject();
		query.put(CommentConstant.FILED_NAME_COMMENTID, commentId);
		
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put(CommentConstant.FILED_NAME_COMMENTSTATUS, CommentConstant.STATUS_OFF);
		MongoDBSupport.getInstance().updateCollection(Constant.TWITTER_COMMENT, query, new BasicDBObject("$set",dbObject));
		
	}

	@Override
	public List<TComment> getListByTwitterId(Long twitterId, Page page) throws Exception {
		List<TComment> supports = null;
		BasicDBObject param = new BasicDBObject();// 条件
		param.put(CommentConstant.FILED_NAME_COMMENTSTATUS, CommentConstant.STATUS_ON);
		param.put(CommentConstant.FILED_NAME_TWITTERID, twitterId);
		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put(CommentConstant.FILED_NAME_CREATETIME, -1);
		MongoDBSupport mongoDBSupport = MongoDBSupport.getInstance();
		page.setCountdate(mongoDBSupport.getCount(Constant.TWITTER_COMMENT, param).intValue());
		DBCursor cur = null;
		try {
			cur = MongoDBSupport.getInstance().queryCollectionByParam(
					Constant.TWITTER_COMMENT, param, sort);
			if (cur != null) {
				Type type = new TypeToken<List<TComment>>() {
				}.getType();
				supports = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
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
		return supports;
	}

}
