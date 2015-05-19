package com.zte.im.twitter.execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.TTwitter;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;

/**
 * 通过ID 获取微博详细信息
 * 
 * @author Administrator
 * 
 */
public class GetTwitterById extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(GetTwitterById.class);
	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "";

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Long cid = json.getLong("id");
		 	Long uid = super.getContext().getUser().getUid();
			 
			// Long gid = super.getContext().getUser().getGid();
			TwitterServiceImpl twitterService = TwitterServiceImpl
					.getInstance();
			TTwitter obj = twitterService.findTwitterDetail(cid, uid);
			if (obj != null) {
				Map<String, Object> resComment = new HashMap<String, Object>();
				dealResData(resComment, obj);
				list.add(resComment);
			} else {
				msg = Constant.CODE_6;
				resFlag = Constant.CODE_6;
			}
		} catch (Exception e) {
			msg = e.getMessage();
			resFlag = Constant.ERROR_CODE;
		}
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> res2 = new HashMap<String, Object>();
		res2.put("resMessage", ""+msg);
		res2.put("resCode", resFlag);
		res2.put("vs", Constant.TRUE);
		res.put("res", res2);
		res.put("resList", list);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	private void dealResData(Map<String, Object> resComment, TTwitter twitter) {
		resComment.put("twitterId", getResObject(twitter.getTwitterId()));
		resComment.put("sourceId", getResObject(twitter.getSourceId()));
		resComment.put("tuserId", getResObject(twitter.getUserId()));
		resComment.put("twitterContent", getResObject(twitter.getTwitterContent()));
		resComment.put("twitterType", getResObject(twitter.getTwitterType()));
		resComment.put("visibleRange", getResObject(twitter.getVisibleRange()));
		resComment.put("createTime", getResObject(twitter.getCreateTime()));
		resComment.put("country", getResObject(twitter.getCountry()));
		resComment.put("province", getResObject(twitter.getProvince()));
		resComment.put("city", getResObject(twitter.getCity()));
		resComment.put("county", getResObject(twitter.getCounty()));
		resComment.put("street", getResObject(twitter.getStreet()));
		resComment.put("address", getResObject(twitter.getAddress()));
		resComment.put("forwardNum", getResObject(twitter.getForwardNum()));
		resComment.put("collectionNum",
				getResObject(twitter.getCollectionNum()));
		resComment.put("commentNum", getResObject(twitter.getCommentNum()));
		resComment.put("supportNum", getResObject(twitter.getSupportNum()));
		resComment.put("imgSrc", getResObject(twitter.getImgSrc()));
		resComment.put("twitterStatus",
				getResObject(twitter.getTwitterStatus()));
		resComment.put("userName", getResObject(twitter.getUserName()));
		resComment.put("hreflag", getResObject(twitter.getHreflag()));
		resComment.put("bigpicurl", getResObject(twitter.getBigpicurl()));
		resComment.put("minipicurl", getResObject(twitter.getMinipicurl()));
		resComment.put("isCollect", getResObject(twitter.getIsCollect()));
		
		resComment.put("isSupport", getResObject(twitter.getIsSupport()));
		resComment.put("sourceUserId", getResObject(twitter.getSourceUserId()));
		resComment.put("sourceUserName", getResObject(twitter.getSourceUserName()));
		resComment.put("sourceMinipicurl", getResObject(twitter.getSourceMinipicurl()));
		resComment.put("sourceBigpicurl", getResObject(twitter.getSourceBigpicurl()));
		resComment.put("sourceContent", getResObject(twitter.getSourceContent()));
		resComment.put("sourceCreateTime", getResObject(twitter.getSourceCreateTime()));
		resComment.put("sourceImgSrc", getResObject(twitter.getSourceImgSrc()));
		resComment.put("sourceIsCollect", getResObject(twitter.getSourceIsCollect()));
		resComment.put("sourceIsSupport", getResObject(twitter.getSourceIsSupport()));
		resComment.put("sourceHreflag", getResObject(twitter.getSourceHreflag()));
		resComment.put("sourceForwardNum", getResObject(twitter.getSourceForwardNum()));
		resComment.put("sourceCollectionNum", getResObject(twitter.getSourceCollectionNum()));
		resComment.put("sourceCommentNum", getResObject(twitter.getSourceCommentNum()));
		resComment.put("sourceSupportNum", getResObject(twitter.getSourceSupportNum()));
		
		resComment.put("sourceCountry", getResObject(twitter.getSourceCountry()));
		resComment.put("sourceProvince", getResObject(twitter.getSourceProvince()));
		resComment.put("sourceCity", getResObject(twitter.getSourceCity()));
		resComment.put("sourceCounty", getResObject(twitter.getSourceCounty()));
		resComment.put("sourceStreet", getResObject(twitter.getSourceStreet()));
		resComment.put("sourceAddress", getResObject(twitter.getSourceAddress()));
		resComment.put("sourceTwitterType", getResObject(twitter.getSourceTwitterType()));
		resComment.put("sourceTwitterStatus", getResObject(twitter.getSourceTwitterStatus()));
		resComment.put("sourceVisibleRange", getResObject(twitter.getSourceVisibleRange()));
	}

	private Object getResObject(Object ori) {
		return null != ori ? ori : "";
	}
	
	public static void main(String[] args) {
		JSONObject json = new JSONObject(); 
		json.put("id", 42767);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new GetTwitterById();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("getTtwitterDetail");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
