package com.zte.im.service;

import java.util.List;
import java.util.Map;

import com.zte.im.mybatis.bean.MnParticipant;

public interface ICreateMnParticipant {
    
    public String saveMnParticipant(MnParticipant participant);
    public String acceptInform(String informId, String participant_id);
    public String refuseInform(String informId, String participant_id, String remark);
    public List<MnParticipant> queryUidByMap(Map<String, Object> map);

}
