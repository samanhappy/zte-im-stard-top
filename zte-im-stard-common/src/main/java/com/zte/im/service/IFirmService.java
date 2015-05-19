package com.zte.im.service;

import com.zte.im.bean.Firm;

public interface IFirmService {
	/**
	 * 根据企业id查询企业
	 * 
	 * @param gid
	 * @return
	 */
	public Firm getFirm(Long gid);

	/**
	 * 根据关系数据库id查询企业
	 * 
	 * @param id
	 * @return
	 */
	public Firm getFirm(String id);

}
