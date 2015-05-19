package com.zte.im.service;

import java.util.Map;

import com.zte.im.mybatis.bean.MnInform;

public interface ICreateMnInformService {
    
    public Map<String, Object> saveMnInform(MnInform inform, String[] users);
    
    public Map<String, Object> cancellMnInform(String informId, String uid);
    
    public MnInform queryMnInformById(String id);
    
    public Map<String, Object> queryInformByMap(String uid,String InformId, int limit, int offset);
    
    public Map<String, Object> reminderMnInform(String informId, String uid);
    
}
