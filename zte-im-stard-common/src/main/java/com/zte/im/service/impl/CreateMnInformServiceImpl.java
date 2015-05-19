package com.zte.im.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.mybatis.bean.MnInform;
import com.zte.im.mybatis.bean.MnInformByMap;
import com.zte.im.mybatis.bean.MnParticipant;
import com.zte.im.mybatis.mapper.MnInformByMapMapper;
import com.zte.im.mybatis.mapper.MnInformMapper;
import com.zte.im.mybatis.mapper.MnParticipantMapper;
import com.zte.im.service.ICreateMnInformService;
import com.zte.im.util.Constant;
import com.zte.im.util.IdGen;
import com.zte.im.util.InformConstant;

/**
 * 处理通知方法.
 * @author yejun
 *
 */
@Service
public class CreateMnInformServiceImpl implements ICreateMnInformService {
    
    private static CreateMnInformServiceImpl service;
    
    private static Logger logger = LoggerFactory.getLogger(CreateMnInformServiceImpl.class);
    
    /**
     * 创建通知mapper.
     */
    @Autowired
    private MnInformMapper informMapper;
    
    /**
     * 保存通知人员信息mapper.
     */
    @Autowired
    private MnParticipantMapper partiMapper;
    
    /**
     * 通知统计mapper.
     */
    @Autowired
    private MnInformByMapMapper mnInformByMapMapper;
    
    public static ICreateMnInformService getInstance() {
        if (service == null) {
            service = new CreateMnInformServiceImpl();
        }
        return service;
    }
    
    @Override
    public Map<String, Object> saveMnInform(MnInform inform, String[] users) {
        
        MnInform mnInform = new MnInform();
        mnInform.setStartTime(inform.getStartTime());
        mnInform.setEndTime(inform.getEndTime());
        mnInform.setRemark(inform.getRemark());
        mnInform.setTitle(inform.getTitle());
        mnInform.setAddress(inform.getAddress());
        Date currentTime = new Date();
        mnInform.setCreateTime(currentTime);
        mnInform.setCreator(inform.getCreator());
        String id = IdGen.generateId();
        mnInform.setId(id);
        if (inform.getEndTime().after(new Date())) {
            mnInform.setStatus(InformConstant.run);
        } else {
            mnInform.setStatus(InformConstant.expired);
        }
        //创建新建通知
        informMapper.insertSelective(mnInform);
        
        //保存参与者信息
        for (int i = 0; i < users.length; i++) {
            MnParticipant participant = new MnParticipant();
            participant.setInformId(id);
            participant.setId(IdGen.generateId());
            participant.setParticipantId(users[i]);
            partiMapper.insertSelective(participant);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("informId", id);
        result.put("creatTime", currentTime.getTime());
        return result;
    }

    @Override
    public Map<String, Object> cancellMnInform(String informId, String uid) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("creator", uid);
        map.put("id", informId);
        MnInform inform = informMapper.selectById(informId);
        if (inform == null) {
            logger.info("query inform failed or inform is null, please query later");
            return null;
        }
        //当前时间不能大于通知结束时间
        if (inform.getEndTime().before(new Date()) 
                || inform.getStatus().equalsIgnoreCase(InformConstant.cancell)) {
            logger.info("this inform is expied or inform is cancelled, you can not deal this inform");
            return null;
        }
        if (!inform.getCreator().equalsIgnoreCase(uid)) {
            logger.info("you have insufficient permissions, so you can not cancell this inform");
            return null;
        }
        //更新会议数据库
        MnInform newInform = new MnInform();
        newInform.setId(inform.getId());
        newInform.setCreator(inform.getCreator());
        newInform.setStatus(InformConstant.cancell);
        informMapper.updateSelectiveById(newInform);
        
        //查询参会者Uid
        Map<String, Object> mnMap = new HashMap<String, Object>();
        mnMap.put("informId", informId);
        List<MnParticipant> participant = partiMapper.selectByMap(mnMap);
        List<String> uids = new ArrayList<String>();
        if (participant == null) {
            logger.info("there is no participant, so you can not query any participant");
            return null;
        }
        for (MnParticipant par : participant) {
            uids.add(par.getParticipantId());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("uids", uids);
        result.put("title", inform.getTitle());
        return result;
    }

    @Override
    public MnInform queryMnInformById(String id) {
        
        if (id == null || "".equals(id)) {
            logger.info("informId is null, you can not query any inform");
            return null;
        }
        MnInform inform = informMapper.selectById(id);
        if (inform == null) {
            logger.info("query inform failed or null, please query later");
            return null;
        }
        return inform;
    }

    @Override
    public Map<String, Object> queryInformByMap(String uid, String informId, int limit, int offset) {
        
        if (uid == null || "".equals(uid)) {
            logger.info("uid is null, you can not query any inform");
            return null;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        List<MnInformByMap> mnInformByMap = new ArrayList<MnInformByMap>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        //获取当前时间
        Date now = new Date();
        //查询单个记录
        if (informId != null) {
            queryMap.put("informId", informId);
            queryMap.put("userId", uid);
            queryMap.put("limit", 1);
            queryMap.put("offset", 0);
            mnInformByMap = mnInformByMapMapper.selectByMap(queryMap);
            //获取通知数
            Integer count = mnInformByMapMapper.countByMap(queryMap);
            result.put("count", count);
            //查询参会者信息
            List<MnParticipant> mnParticipant = partiMapper.selectParticipantByMap(queryMap);
            
            //根据参会者uid查询mongodb中用户的姓名
            if(mnParticipant != null && mnParticipant.size() > 0) {
                for(MnParticipant mn : mnParticipant) {
                BasicDBObject query = new BasicDBObject();
                query.put("uid", Long.parseLong(mn.getParticipantId()));
                DBObject obj = MongoDBSupport.getInstance().queryOneByParam(Constant.USER_DATA, query, null);
                mn.setName((String)obj.get("name"));
                }
            }
            //查询当前用户是否已经处理该通知
            for(MnInformByMap mn : mnInformByMap) {
                Map<String, Object> query = new HashMap<String, Object>();
                if(mn.getEndTime().before(now)) {
                    mn.setStatus(InformConstant.expired);
                }
                query.put("informId", mn.getId());
                query.put("participantId", uid);
                List<MnParticipant> mnPart = partiMapper.selectByMap(query);
                if(mnPart != null && mnPart.size() > 0) {
                    mn.setDealStatus(mnPart.get(0).getStatus());
                }   
            }
            result.put("mnInform", mnInformByMap);
            result.put("users", mnParticipant);
            return result;
        }
        queryMap.put("userId", uid);
        queryMap.put("limit", limit);
        queryMap.put("offset", offset);
        mnInformByMap = mnInformByMapMapper.selectByMap(queryMap);
        //查询当前用户是否已经处理该通知
        for(MnInformByMap mn : mnInformByMap) {
            Map<String, Object> query = new HashMap<String, Object>();
            //判断获取的通知是否已经过期
            if(mn.getEndTime().before(now)) {
                mn.setStatus(InformConstant.expired);
            }
            query.put("informId", mn.getId());
            query.put("participantId", uid);
            List<MnParticipant> mnPart = partiMapper.selectByMap(query);
            if(mnPart != null && mnPart.size() > 0) {
                mn.setDealStatus(mnPart.get(0).getStatus());
            }   
        }
        
        //查询用户所有通知数目
        Map<String, Object> countMap = new HashMap<String, Object>();
        countMap.put("userId", uid);
        Integer count = mnInformByMapMapper.countByMap(countMap);
        result.put("mnInform", mnInformByMap);
        result.put("count", count);
        return result;
    }
    
    
    public Map<String, Object> reminderMnInform(String informId, String uid) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("creator", uid);
        map.put("id", informId);
        MnInform inform = informMapper.selectById(informId);
        if (inform == null) {
            logger.info("query inform failed or inform is null, please query later");
            return null;
        }
        //当前时间不能大于通知结束时间
        if (inform.getEndTime().before(new Date()) 
                || inform.getStatus().equalsIgnoreCase(InformConstant.cancell)) {
            logger.info("this inform is expied or inform is cancelled, you can not remind any participants");
            return null;
        }
        if (!inform.getCreator().equalsIgnoreCase(uid)) {
            logger.info("you have insufficient permissions, so you can not remind any participants");
            return null;
        }
        
        //未处理的参会人员Uid
        Map<String, Object> mnMap = new HashMap<String, Object>();
        mnMap.put("informId", informId);
        List<MnParticipant> participant = partiMapper.selectNoDealParticipantByMap(mnMap);
        List<String> uids = new ArrayList<String>();
        if (participant == null) {
            logger.info("there is no participant, so you can not query any participant");
            return null;
        }
        for (MnParticipant par : participant) {
            uids.add(par.getParticipantId());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("uids", uids);
        result.put("title", inform.getTitle());
        return result;
    }
    
}
