package com.zte.im.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zte.im.mybatis.bean.MnParticipant;
import com.zte.im.mybatis.bean.UcMember;
import com.zte.im.mybatis.bean.UcMemberExample;
import com.zte.im.mybatis.mapper.MnInformMapper;
import com.zte.im.mybatis.mapper.MnParticipantMapper;
import com.zte.im.mybatis.mapper.UcMemberMapper;
import com.zte.im.service.ICreateMnParticipant;
import com.zte.im.util.InformConstant;

/**
 * 保存新建通知信息.
 * @author yejun
 *
 */
/**
 * @author root
 *
 */
@Service
public class CreateMnParticipantServiceImpl implements ICreateMnParticipant {
    
    private static Logger logger = LoggerFactory.getLogger(CreateMnParticipantServiceImpl.class);
    
    @Autowired
    private MnInformMapper informMapper;
    
    @Autowired
    private MnParticipantMapper partiMapper;
    
    @Autowired
    private UcMemberMapper memberMapper;

    @Override
    public String saveMnParticipant(MnParticipant participant) {
        return null;
    }

    @Override
    public String acceptInform(String informId, String uid) {
        
        if(informId == null || "".equalsIgnoreCase(informId)) {
            logger.info("informId is null, you can not update");
            return null;
        }
        if(uid == null || "".equalsIgnoreCase(uid)) {
            logger.info("uid is null , you can not update");
            return null;
        }
        MnParticipant participant = new MnParticipant();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("informId", informId);
        map.put("participantId", uid);
        List<MnParticipant> oldParti = partiMapper.selectByMap(map);
        if(oldParti == null) {
            logger.info("query participant failed,something is wrong");
            return null;
        }
        participant.setId(oldParti.get(0).getId());
        participant.setInformId(informId);
        participant.setParticipantId(uid);
        participant.setDealTime(new Date());
        participant.setStatus(InformConstant.accept);
        partiMapper.updateSelectiveById(participant);
        
        String creatorId = informMapper.selectById(informId).getCreator();;
        return creatorId;
    }

    @Override
    public String refuseInform(String informId, String uid, String remark) {
            
        if(informId == null || "".equalsIgnoreCase(informId)) {
            logger.info("informId is null, you can not update");
            return null;
        }
        if(uid == null || "".equalsIgnoreCase(uid)) {
            logger.info("uid is null , you can not update");
            return null;
        }
        MnParticipant participant = new MnParticipant();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("informId", informId);
        map.put("participantId", uid);
        List<MnParticipant> oldParti = partiMapper.selectByMap(map);
        if(oldParti == null) {
            logger.info("query participant failed,something is wrong");
            return null;
        }
        
        participant.setId(oldParti.get(0).getId());
        participant.setInformId(informId);
        participant.setParticipantId(uid);
        participant.setDealTime(new Date());
        participant.setRemark(remark);
        participant.setStatus(InformConstant.refuse);
        partiMapper.updateSelectiveById(participant);
        //消息创建者
        String creatorId = informMapper.selectById(informId).getCreator();
        return creatorId ;
    }

    @Override
    public List<MnParticipant> queryUidByMap(Map<String, Object> map) {
        if(map == null) {
            logger.info(" required arguments  map is null, you can not query any participant");
            return null;
        }
        List<MnParticipant> partic = partiMapper.selectByMap(map);
        return partic;
    }
}
