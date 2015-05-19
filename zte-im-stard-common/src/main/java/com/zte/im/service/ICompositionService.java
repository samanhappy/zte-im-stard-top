package com.zte.im.service;

import java.util.List;

import com.zte.im.bean.Composition;
import com.zte.im.mybatis.bean.UcGroup;

public interface ICompositionService {

	// 根据PostgreSQL主键查询部门
	public Composition getComBySqlId(String id);
	
	public Composition getComByCId(Long cid);
	
	// 判断除cid之外是否还有子部门
	public boolean hasSubComposition(Long pid, Long cid);

	public boolean saveOrUpdate(UcGroup group);

	public boolean deleteBySqlIdList(List<String> ids);
	
	/**
	 * 根据gid和名称查询部门
	 * 
	 * @param gid
	 * @param name
	 * @return
	 */
	public Composition getComposition(Long gid, String name);

	public List<Composition> getComposition(Long gid);
	
	public void updateByCId(Composition com);

}
