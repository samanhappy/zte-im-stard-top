package com.zte.im.service;

import java.util.List;
import java.util.Map;

import com.zte.im.bean.Programe;

public interface IProgrameService {
	/**
	 * 插入日程
	 * 
	 * @param programe
	 * @return
	 */
	public void addPrograme(Programe programe);

	/**
	 * 插入日程
	 * 
	 * @param programe
	 * @return
	 */
	public List<Programe> findList(Programe programe);

	/**
	 * 删除日程
	 * 
	 * @param uid
	 *            用户id
	 * @param ids
	 *            日程id数组
	 */
	public void removePrograme(Long uid, String ids);

	/**
	 * 修改日程
	 * 
	 * @param programe
	 */
	public void updatePrograme(Programe programe);

	/**
	 * 完成日程
	 * 
	 * @param uid
	 *            用户id
	 * @param ids
	 *            日程id数组
	 * @param days
	 *            日程开始时间数组
	 * @param progTypes
	 *            日程类型数组
	 */
	public void finishPrograme(Long uid, String ids, String days,
			String progTypes);

	/**
	 * 查询当天日程
	 * 
	 * @param programe
	 * @return
	 */
	public List<Programe> findDayList(Programe programe);

	/**
	 * 查询日程详情
	 * 
	 * @param programe
	 * @return
	 */
	public Programe findProgrameDetail(Programe programe);

	/**
	 * 查询单条日程被指派人
	 * 
	 * @param programe
	 * @return
	 */
	public List<Programe> findProgrameAppointList(Programe programe);

	/**
	 * 查询多天日程
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param uid
	 *            用户id
	 * @return
	 */
	public List<Map<String, List<Programe>>> findMoreDayList(String startDate,
			String endDate, Long uid);

	/**
	 * 查询列表日程
	 * 
	 * @param date
	 *            时间
	 * @param type
	 *            类型 1 代办 2已完成 3指派 4全部
	 * @param uid
	 *            用户id
	 * @return
	 */
	public List<Map<String, List<Programe>>> findDayPageList(String date,
			int type, Long uid);

	/**
	 * 驳回指派日程
	 * 
	 * @param uid
	 *            用户id
	 * @param id
	 *            日程id
	 * @param content
	 *            驳回原因
	 * @param startDate
	 *            日程开始时间
	 */
	public void rejectPrograme(Long uid, int id, String content,
			String startDate);
}
