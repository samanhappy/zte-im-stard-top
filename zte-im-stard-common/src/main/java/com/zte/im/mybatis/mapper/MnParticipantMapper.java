package com.zte.im.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.zte.im.mybatis.bean.MnParticipant;
import com.ztecs.appoc.cloudservice.base.BaseDao;

public interface MnParticipantMapper extends BaseDao<MnParticipant> {
    /***
     * 根据条件查询记录
     * 
     * @param map
     * @return
     */
    public List<MnParticipant> selectParticipantByMap(Map<String, Object> map);
    
    public List<MnParticipant> selectNoDealParticipantByMap(Map<String, Object> map);
   
}