package com.zte.weibo.common.constant;

import java.util.HashMap;
import java.util.Map;

public class TwitterConstant {

	public static final String DEL_CODE = "99";//分享删除的标志状态
	
	public static final String FILED_NAME_ID = "twitterId";
	public static final String FILED_NAME_STATUS = "twitterStatus";
	public static final String FILED_NAME_CREATETIME = "createTime";
	public static final String FILED_NAME_CREATEUSERID = "createUserId";
	public static final String FILED_NAME_CONTENT = "twitterContent";
	public static final String FILED_NAME_SEARCHCONTENT = "searchContent";
	public static final String FILED_NAME_USERID = "userId";
	public static final String FILED_NAME_TWITTERTYPE = "twitterType";
	
	public static final String TWITTER_TYPE_YC = "01";//原创分享
	public static final String TWITTER_TYPE_ZF = "02";//转发分享
	
	
	/**
	 * 分享类型编码对照(01:原创；02：转发)
	 */
	public static final Map<String, String> weiboType = new HashMap<String, String>();
	
	public static final Map<String, String> visibleRange = new HashMap<String, String>();
	static{
		weiboType.put(TWITTER_TYPE_YC, "原创");
		weiboType.put(TWITTER_TYPE_ZF, "转发");
		
		visibleRange.put("01", "公开");
		visibleRange.put("02", "好友圈");
		visibleRange.put("03", "仅自己可见");
	}
	
}
