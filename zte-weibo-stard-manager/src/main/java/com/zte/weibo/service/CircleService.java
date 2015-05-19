package com.zte.weibo.service;

import java.util.List;
import java.util.Map;

import com.zte.im.bean.TGroup;
import com.zte.im.util.Page;
import com.zte.weibo.databinder.CircleBinder;

public interface CircleService {

	/**
	 * 分页查询圈子信息
	 * @author syq	2015年1月17日
	 * @param circleBinder
	 * @param page
	 * @return
	 */
	public List<TGroup> getList(CircleBinder circleBinder,Page page);
	
	/**
	 * 修改指定圈子的状态
	 * @author syq	2015年1月17日
	 * @param groupids	圈子ID数组
	 * @param status	圈子状态（0：停用；1：启用）
	 * @param stopDesc	停用时的说明
	 * @throws Exception
	 */
	public void modifyGroupStatus(Long[] groupids,String status,String stopDesc) throws Exception;
	
	/**
	 * 导出指定圈子ID对应的圈子信息
	 * @author syq	2015年1月17日
	 * @param groupids	圈子ID数组，为空时导出全部
	 * @return
	 * @throws Exception
	 */
	public String export(Long[] groupids) throws Exception;
	
	/**
	 * 查询指定圈子ID对应的成员数
	 * @author syq	2015年1月17日
	 * @param groupId	圈子ID，（为空时查询全部）
	 * @return 
	 * @throws Exception
	 */
	public long getQZCYS(Long groupId) throws Exception;
	
	/**
	 * 查询指定圈子ID对应的分享数
	 * @author syq	2015年1月17日
	 * @param groupId	圈子ID（为空时查询全部）
	 * @return
	 * @throws Exception
	 */
	public long getQZWBS(Long groupId) throws Exception;
	
	/**
	 * 分组查询圈子成员数
	 * @author syq	2015年1月31日
	 * @param groupId	为空时查询全部
	 * @return	key:groupid;value:num
	 * @throws Exception
	 */
	public Map<Long, Long> getQZCYS(Long[] groupId) throws Exception;
	/**
	 * 分组查询圈子分享数
	 * @author syq	2015年1月31日
	 * @param groupId	为空时查询全部
	 * @return	key:groupid; value:num
	 * @throws Exception
	 */
	public Map<Long, Long> getQZWBS(Long[] groupId) throws Exception;
	/**
	 * 查询指定的圈子信息
	 * @author syq	2015年1月24日
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public TGroup getByGroupId(Long groupId) throws Exception;
	
}
