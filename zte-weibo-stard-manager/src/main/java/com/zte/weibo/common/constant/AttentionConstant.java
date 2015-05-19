package com.zte.weibo.common.constant;

public class AttentionConstant {

	public static final String ATTENTIONTYPE_NORMAL = "01";//关注类型-一般
	public static final String ATTENTIONTYPE_SPECIAL ="02";//关注类型-特别
	public static final String ATTENTIONSTATUS_ON ="00";//状态-启用
	public static final String ATTENTIONSTATUS_OFF ="99";//状态-停用
	
	public static final String ATTR_USER = "01";             //关注性质-用户关注
	public static final String ATTR_DEFAULT = "02";          //关注性质-默认关注
	public static final String ATTR_DEPT = "03";             //关注性质-部门关注
	
	public static final String FILED_NAME_USERID ="userId";
	public static final String FILED_NAME_ATTENTIONUSERID ="attentionUserId";
	public static final String FILED_NAME_ATTENTIONSTATUS ="attentionStatus";
	public static final String FILED_NAME_ATTENTIONATTR ="attentionAttr";
	
	public enum AttentionType{
		normal,special
	}
	
}
