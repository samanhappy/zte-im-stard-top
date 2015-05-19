package com.zte.im.service;

import com.mongodb.DBObject;
import com.zte.im.bean.Inform;

public interface InformService {
	/**
	 * 新建通知
	 */
	public Inform createInform(Inform inform);

	/**
	 * 通过id查询实体
	 * 
	 * @param informid
	 * @return
	 */
	public DBObject queryInformListById(String informid);

}
