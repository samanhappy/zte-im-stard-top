/**
 * @Title: IdEntity.java
 * @Package com.ztecs.appoc.cloudservice.entity
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:ZTE CLOUD SERVICE.CO
 * 
 * @author Administrator
 * @date 2014年7月24日 上午9:33:45
 * @version V0.1
 */
package com.ztecs.appoc.cloudservice.entity;


/**
 * 这是个桩类，只要字段与业务工程内的同名类一致即可。
 *  
 * @author 陈勇
 */
public class IdEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4761576373654520325L;
	
	protected String id;
	
	public String generateId(){
	    return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
