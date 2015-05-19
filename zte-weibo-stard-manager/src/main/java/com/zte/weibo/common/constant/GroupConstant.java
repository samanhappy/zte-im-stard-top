package com.zte.weibo.common.constant;

import java.util.HashMap;
import java.util.Map;

public class GroupConstant {

	public static final String STATUS_ON = "00";
	public static final String STATUS_OFF = "99";
	
	public static final String TYPE_PUBLIC = "01";
	public static final String TYPE_PRIVATE = "02";
	
	
	
	public static final String FILED_NAME_USERID = "userId";
	public static final String FILED_NAME_CREATEUSERID = "createUserId";
	public static final String FILED_NAME_GROUPID = "groupId";
	public static final String FILED_NAME_GROUPNAME = "groupName";
	public static final String FILED_NAME_GROUPTYPE = "groupType";
	public static final String FILED_NAME_GROUPSTATUS = "groupStatus";
	public static final String FILED_NAME_STOPDESC = "stopDesc";
	
	
	
	public static final Map<String, String> groupStatusMap;
	
	static{
		groupStatusMap = new HashMap<String, String>();
		groupStatusMap.put(STATUS_ON, "已启用");
		groupStatusMap.put(STATUS_OFF, "已停用");
	}
	
}
