package com.zte.im.twitter.execute;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.util.Constant;

/**
 * dec 删除@到我
 * 
 * @author  
 * 
 */
public class CyDelTMention extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(CyDelTMention.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Map<String, Object> res = new HashMap<String, Object>();
		String resFlag = Constant.TRUE;
		String msg = "";
		try {
			Long mentionUserId = json.getLong("mentionUserId");
			BasicDBObject dbObject = new BasicDBObject();
//			dbObject.put("mentionUserId", mentionUserId);
//			MongoDBSupport.getInstance().removeDBObjects(Constant.TWITTER_MENTION,
//					dbObject);
	//		dbObject.put("becomentUserId", mentionUserId);
//			 	MongoDBSupport.getInstance().dropOneCollection(Constant.TWITTER_COMMENT );
			BasicDBObject updatedValue=new BasicDBObject();  
	        updatedValue.put("becomentUserId", 0); 
	        dbObject.put("becomentUserId", null);
	        dbObject.put("becomentUserId", null);
	        //ceshi
	     //   for(int i=0;i<380;i++){
	       //   MongoDBSupport.getInstance().upsertCollection(Constant.TWITTER_COMMENT, dbObject, updatedValue);
	         MongoDBSupport.getInstance().removeDBObjects(Constant.TWITTER_COMMENT,dbObject);
	      //  }
	        System.out.println(   MongoDBSupport.getInstance().getCount(Constant.TWITTER_COMMENT, dbObject));
		} catch (Exception e) {
			resFlag = Constant.ERROR_CODE;
			msg = e.getMessage();
			logger.error("", e);
		}
		res.put("msg", msg);
		res.put("resFlag", resFlag);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("mentionUserId", 1000037);

		json.put("token", "9661802_N1HLNMDN");
		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new CyDelTMention();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("delTwitter");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
