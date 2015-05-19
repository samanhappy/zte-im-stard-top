package com.zte.im.service;

import java.util.List;

import com.zte.im.bean.UpdateRecord;

public interface IUpdateRecordService {
	/**
	 * 根据企业id和时间戳userListVer获取更新记录.
	 * 
	 * @param id          企业id
	 * @param timestamp   时间戳
	 * @return            返回用户id
	 */
	@SuppressWarnings("rawtypes")
    public List<List> getUpdateRecordbyid(Long gid, Long userListVer);
	
	/**
	 * 保存用户更新信息记录.
	 * @param record  用户更新记录
	 * @return
	 */
	public boolean saveUpdateRecord(UpdateRecord record);
	    
}
