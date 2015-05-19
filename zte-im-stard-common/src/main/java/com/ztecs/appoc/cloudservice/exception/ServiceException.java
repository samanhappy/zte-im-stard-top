/**
 * @Title: ServiceException.java
 * @Package com.ztecs.appoc.cloudservice.exception
 * @Description: service异常
 * Copyright: Copyright (c) 2014 
 * Company:ZTE CLOUD SERVICE.CO
 * 
 * @author liujiang
 * @date 2014年7月29日 下午3:43:19
 * @version V0.1
 */
package com.ztecs.appoc.cloudservice.exception;

/**
 * @ClassName: ServiceException
 * @Description: service异常
 * @author liujiang
 * @date 2014年7月29日 下午3:43:19
 * 
 */
public class ServiceException extends Exception {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2039668027430407485L;

    private final String      errorCode;

    public ServiceException(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public ServiceException(String msg, String errorCode, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
