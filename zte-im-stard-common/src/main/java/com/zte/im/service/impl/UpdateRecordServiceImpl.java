package com.zte.im.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.zte.im.bean.UpdateRecord;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.IUpdateRecordService;
import com.zte.im.util.Constant;
import com.zte.im.util.MongoDBMapReduce;

public class UpdateRecordServiceImpl implements IUpdateRecordService{
    
    public static UpdateRecordServiceImpl service;
    
    private static Logger logger = LoggerFactory.getLogger(UpdateRecordServiceImpl.class);

    public static IUpdateRecordService getInstance() {
        if (service == null) {
            service = new UpdateRecordServiceImpl();
        }
        return service;
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public List<List> getUpdateRecordbyid(Long gid, Long userListVer) {
        BasicDBObject query = new BasicDBObject();
        if(gid != null) {
            query.put("gid", gid);
        }
        if(userListVer != null) {
            query.append("userListVer", new BasicDBObject().append("$gt", userListVer));
        }
        List<List> uids = new ArrayList<List>();
        DBCollection collection = MongoDBSupport.getInstance().getCollectionByName(Constant.update_record);
        try {
            uids = MongoDBMapReduce.mapRequce(collection, query);
        } catch (Exception e) {
            logger.info("query record failed");
            e.printStackTrace();
        }
        return uids;
    }

    @Override
    public boolean saveUpdateRecord(UpdateRecord record) {
        BasicDBObject param = new BasicDBObject();
        if(record.getGid() != null || "".equals(record.getGid())) {
            param.append("gid", record.getGid());
        }
        if(record.getUid() != null || "".equals(record.getUid())) {
            param.append("uid", record.getUid());
        }
        if(record.getuserListVer() != 0 || "".equals(record.getuserListVer())) {
            param.append("userListVer", record.getuserListVer());
        }
        if(record.getOper() != null || "".equals(record.getOper())) {
            param.append("oper", record.getOper());
        }
        DBCollection collection = MongoDBSupport.getInstance().getCollectionByName(Constant.update_record);
        WriteResult result = collection.insert(param);
        logger.info(result.toString());
        if(result.getN() == 0) {
            logger.info("save update record is success");
        } else {
            logger.info("save update record is failed");
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        
//         UpdateRecord record  = new UpdateRecord();
//         record.setGid(26L);
//         record.setUid(46L);
//         record.setOper("用户更新信息");
//         record.setTimestamp(Calendar.getInstance().getTimeInMillis());
//         UpdateRecordServiceImpl.getInstance().saveUpdateRecord(record);
        
        DBCollection collection = MongoDBSupport.getInstance().getCollectionByName(Constant.update_record);
        BasicDBObject query = new BasicDBObject();
        query.put("userListVer", new BasicDBObject().append("$gt", 1426484584763L));
        query.put("gid", 26L);
        System.out.println(query);
        List<List> uids = null;
        try {
            uids = MongoDBMapReduce.mapRequce(collection, query);
        } catch (Exception e) {
            logger.info("query record failed");
            e.printStackTrace();
        }
    }
}
