package com.zte.weibo.service;

import java.util.List;

import com.zte.weibo.bean.Dept;

public interface DeptService {

	public List<Dept> getListByName(String name) throws Exception;
	
	public void add(Dept dept) throws Exception;
	
	public long getCountByCid(Long cid) throws Exception;
}
