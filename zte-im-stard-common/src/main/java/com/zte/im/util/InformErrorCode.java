package com.zte.im.util;

public class InformErrorCode {

	/**服务不可用*/
	public final static String SERVICE_CURRENTLY_UNAVAILABLE = "1001";
	/** 应用权限不足*/
	public final static String INSUFFICIENT_ISV_PERMISSIONS = "1002";  
	/** 用户权限不足*/
    public final static String  INSUFFICIENT_USER_PERMISSIONS = "1003";  
    /** 上传错误*/
    public final static String UPLOAD_FAIL = "1004";  
    /** 不允许的Http Action*/
    public final static String HTTP_ACTION_NOT_ALLOWED = "1005";
    /** 错误的编码*/
    public final static String INVALID_ENCODING = "1006";
    /** 请求被拒绝*/
    public final static String FORBIDDEN_REQUEST = "1007";
    /** 废弃的方法*/
    public final static String METHOD_OBSOLETED = "1008";
    /** 业务逻辑错误*/
    public final static String BUSINESS_LOGIC_ERROR = "1009";
    /** 缺少请求参数*/
    public final static String MISSING_REQUIRED_ARGUMENTS = "1032";
    /** 错误的参数*/
    public final static String INVALID_ARGUMENTS = "1033";
    
}
