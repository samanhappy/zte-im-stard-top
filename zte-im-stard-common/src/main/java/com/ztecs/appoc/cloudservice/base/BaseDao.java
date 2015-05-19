/**
 * @Title: BaseDao.java
 * @Package com.ztecs.appoc.cloudservice.uc.tenant
 * @Description: 基础Dao类
 * Copyright: Copyright (c) 2014 
 * Company:ZTE CLOUD SERVICE.CO
 * 
 * @author liujiang
 * @date 2014年8月14日 上午9:31:00
 * @version V0.1
 */
package com.ztecs.appoc.cloudservice.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ztecs.appoc.cloudservice.entity.IEntity;

/**
 * @ClassName: BaseDao
 * @Description: 基础Dao类
 * @author liujiang
 * @date 2014年8月14日 上午9:31:00
 * 
 */
public interface BaseDao<T extends IEntity> {

    /***
     * 根据id查询
     * 
     * @param fileId
     * @return
     */
    public T selectById(String fileId);

    /***
     * 根据条件查询记录
     * 
     * @param map
     * @return
     */
    public List<T> selectByMap(Map<String, Object> map);

    /***
     * 根据条件查询记录数
     * 
     * @param map
     * @return
     */
    public Integer countByMap(Map<String, Object> map);

    /***
     * 选择插入
     * 
     * @param obj
     * @return
     */
    public Integer insertSelective(T obj);

    /***
     * 根据id删除
     * 
     * @param fileId
     * @return
     */
    public Integer deleteById(String fileId);

    /***
     * 根据id集合删除
     * 
     * @param ids
     * @return
     */
    public Integer deleteByIds(List<String> ids);

    /***
     * 根据条件删除
     * 
     * @param map
     * @return
     */
    public Integer deleteByMap(Map<String, Object> map);

    /***
     * 根据条件更新
     * 
     * @param obj
     * @return
     */
    public Integer updateSelectiveById(T obj);

    /***
     * 根据条件更新
     * 
     * @param obj
     * @param param
     * @return
     */
    public Integer updateSelectiveByMap(@Param("record") T obj, Map<String, Object> param);

    /***
     * 查询记录数
     * 
     * @param param
     * @return
     */
    public Integer count(Map<String, Object> param);

    /***
     * 分页查询
     * 
     * @param param
     * @param bowBounds
     * @return
     */
    public List<T> selectByMap(Map<String, Object> param, RowBounds bowBounds);
}
