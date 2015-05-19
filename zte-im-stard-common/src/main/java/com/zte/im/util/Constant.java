package com.zte.im.util;

public class Constant {

	/**访问正常*/
	public final static String TRUE = "1";// 访问正常
	/** 数据未更新*/
	public final static String DATA_NOT_UPDATED = "0";  
	/** 通用服务器错误 */
	public final static String ERROR_CODE = "-1"; 
	/**  无特别关注用户*/
	public final static String CODE_2 = "2";// 无特别关注用户
	/**  圈子不存在*/
 	public final static String CODE_3 = "3";// 圈子不存在
	/**  圈子内无微博*/
	public final static String CODE_4 = "4";// 圈子内无微博
	/**  无特别关注用户*/
	public final static String CODE_5 = "5";// 无特别关注用户
	/**  此微博不存在或已删除*/
	public final static String CODE_6 = "6";// 此微博不存在或已删除
	/** 圈子简介不能为空*/
	public final static String CODE_7 = "7";// 圈子简介不能为空
	/**  请求用户不存在*/
	public final static String CODE_8 = "8";// 请求用户不存在
	/**  微博不存在*/
	public final static String CODE_9 = "9"; 
	/**  微博已删除*/
	public final static String CODE_10 = "10"; 
	/**  头像缩略图路径不能为空*/
	public final static String CODE_11 = "11"; 
	/**  头像原图路径不能为空*/
	public final static String CODE_12 = "12";
	/** 话题不存在*/
	public final static String CODE_13 = "13";
	/** 系统消息不存在*/
	public final static String CODE_14 = "14";
	 
	public final static String USER_ISREG = "-2";// 该用户已经注册

	public final static String USER_LOINGERR = "-3";// 登陆参数为空

	public final static String USER_LOINGPWDERR = "-4";// 密码错误

	public final static String USER_RELOING = "-5";// token过期

	public final static String GROUP_NAME_SPEC = "-6";// 群组名称不符合规范

	public final static String GROUP_NAME_NUM = "-7";// 群中必须有其他成员

	public final static String GROUP_NAME_MAX_NUM = "-8";// 群中人数已满

	public final static String USER_LOCKED = "-9";// 用户登录锁定
	
	public final static String USER_DISABLED = "-10";// 用户停用

	// public final static String GROUP_SEQ = "group_seq";
	
	public static final String TENANT_ID = "tenantId";
	
	public static final String USERID = "userid";
	
	public static final String USERNAME = "name";
	
	public static final String USERJID = "jid";
	
	public static final String PRIVILEGES = "privileges";
	
	public static final String ERROR = "error";
	
	public static final String ROLE_NAME = "uname";

	public final static String GROUP_DATA = "group_data";

	public final static String SUCCESS_CODE = "1";

	public final static String Firm_SEQ = "firm_seq";

	public final static String Firm = "firm";

	public final static String COM_SEQ = "com_seq";

	public final static String COM_POSIT = "com_posit";

	public final static String USER_DATA = "user_data";
	
	public final static String T_USER = "t_user";

	public final static String APP_CATE = "zte_im_category";

	public final static String APP = "zte_im_apk";
	
	public final static String WORK_IMG = "work_img";

	public final static String SOFTWARE_COLLECTION_NAME = "zte_im_software";

	public final static String DATA_VER_COLLECTION_NAME = "data_ver";

	public static final String TENANT_PASS = "tenant_pass";

	public static final String FEEDBACK = "feedback";

	public static final String TENANT_SELECT = "tenant_select";
	
	public static final String CHAT_RECORD = "chat_record";

	public static final String NEWS = "news";

	public static final String UC_CLIENT = "uc_client";

	public static final String VOIP_ID = "voip_id";
	
	public static final String INFORM = "inform";
	
	public static final String DEFAULT_PWD = "123456";
	
	//用户信息更新记录collection
	public static final String update_record = "update_record";

	/*
	 * 此处设计人员拼写错误，实际应该为voip*
	 */
	public static final String VIOP_ID = "viopId";

	public static final String GROUP_POSITION = "position";

	public static final String GROUP_DEPT = "dept";

	// ========================企业微博===================================
	public static final String TWITTER_GROUP = "t_group";// 圈子

	public static final String TWITTER_GROUP_USER = "t_group_user";// 圈子用户关联

	public static final String TWITTER_TWITTER = "t_twitter";// 微博

	public static final String TWITTER_COMMENT = "t_comment";// 评论操作与用户关联

	public static final String TWITTER_SUPPORT = "t_support";// 点赞操作与用户关联

	public static final String TWITTER_COLLECT = "t_collect";// 收藏操作与用户关联

	public static final String TWITTER_TRANS_USER = "t_trans_user";// 转发微博关联

	public static final String TWITTER_SYS_MESSAGE = "t_sys_message";// 系统消息

	public static final String TWITTER_MENTION = "t_mention";// 提到信息@

	public static final String TWITTER_ATTENTION = "t_attention";// 关注信息

	public static final String TWITTER_TOPIC = "t_topic";// 话题

	public static final String TWITTER_FEEDBACK = "t_feedback";// 反馈信息

	public static final String TWITTER_GROUP_ID = "t_twitter_group";// 微博圈子对照
	/** 新增微博，选择的同事表*/
	public static final String TWITTER_STAFF_ID = "t_twitter_staff";//  

	public static final String TWITTER_DEPT = "t_twitter_dept";// 微博部门对照

	public static final String TWITTER_TOKEN = "t_twitter_token";// token信息表

	public static final String SYS_LOG = "sysLog";

	public static final String TWITTER_QUERYTYPE_REFRESH = "01";// 刷新

	public static final String TWITTER_QUERYTYPE_NEXTPAGE = "02";// 翻页

	public static final String TWITTER_NOTCOLLECT = "00";// 未收藏

	public static final String TWITTER_ISCOLLECT = "01";// 已收藏

	public static final String TWITTER_FEED_01 = "01";// 反馈状态：未处理

	public static final String TWITTER_FEED_02 = "02";// 反馈状态：已处理

	public static final String TWITTER_ATTR_01 = "01";// 微博属性：普通

	public static final String TWITTER_ATTR_02 = "02";// 微博属性：系统
	
	public static final String TWITTER_SYS_MSG_00 = "00";// 微博系统消息状态：未处理
	public static final String TWITTER_SYS_MSG_01 = "01";// 微博系统消息状态：同意
	public static final String TWITTER_SYS_MSG_02 = "02";// 微博系统消息状态：拒绝

	public static final String TWITTER_NOTSUPPORT = "00";// 未赞

	public static final String TWITTER_ISSUPPORT = "01";// 已赞

	public static final String TWITTER_TYPE_01 = "01";// 微博类型01原创

	public static final String TWITTER_GROUP_JOIN_00 = "00";// 当前用户是否加入该圈子；00未加入

	public static final String TWITTER_GROUP_JOIN_01 = "01";// 当前用户是否加入该圈子；01已加入
	
	public static final String T_USER_COLLECTION_NAME = "user_data";//微博管理-账号表名  于2015-03-30日改为企信的表
	public static final String T_DEPT_COLLECTION_NAME = "com_posit";//微博管理-部门表名  于2015-03-30日改为企信的表
	
//	public static final String T_USER_COLLECTION_NAME = "t_user";//微博管理-账号表名
//	public static final String T_DEPT_COLLECTION_NAME = "t_dept";//微博管理-部门表名
}
