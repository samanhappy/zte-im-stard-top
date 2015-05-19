package com.zte.im.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.zte.im.bean.User;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.IUserUpdateService;
import com.zte.im.util.Constant;

public class UserUpdateServiceImpl implements IUserUpdateService{
    
    public static UserUpdateServiceImpl service;
    
    private static Logger logger = LoggerFactory.getLogger(UserUpdateServiceImpl.class);

    public static IUserUpdateService getInstance() {
        if (service == null) {
            service = new UserUpdateServiceImpl();
        }
        return service;
    }
    
    @Override
    public List<User> getUpdateUsersbyid(Long... uid) {
        
        List<User> list = new ArrayList<User>();
        BasicDBObject query = new BasicDBObject();
        Long[] con = null;
        if(uid != null && !"".equals(uid)) {
            con = new Long[uid.length];
            for(int i = 0; i < uid.length; i++) {
                con[i]  = uid[i];
            }
             query.append("uid", new BasicDBObject().append("$in", con));
             DBCollection collection = MongoDBSupport.getInstance().getCollectionByName(Constant.USER_DATA);
             DBCursor cur = collection.find(query);
             while(cur.hasNext()) {
                 User user = JSON.parseObject(cur.next().toString(),User.class);
                 list.add(user);
             }
        }
        return list;
    }

    @Override
    public List<User> getAddUsersbyid(Long... uid) {
        
        List<User> list = new ArrayList<User>();
        BasicDBObject query = new BasicDBObject();
        Long[] con = null;
        if(uid != null && !"".equals(uid)) {
            con = new Long[uid.length];
            for(int i = 0; i < uid.length; i++) {
                con[i] = uid[i];
            }
             query.append("uid", new BasicDBObject().append("$in", con));
             DBCollection collection = MongoDBSupport.getInstance().getCollectionByName(Constant.USER_DATA);
             DBCursor cur = collection.find(query);
             while(cur.hasNext()) {
                 User user = JSON.parseObject(cur.next().toString(),User.class);
                 list.add(user);
             }
        }
        return list;
    }

    /* (non-Javadoc)
     * @see com.zte.im.service.IUpdateContactsService#getDeleUsersbyid(java.lang.Long[])
     */
    @Override
    public String getDeleUsersbyid(Long... uid) {
        
        BasicDBObject query = new BasicDBObject();
        String message = null;
        Long[] con = null;
        if(uid != null && !"".equals(uid)) {
            con = new Long[uid.length];
            for(int i = 0; i < uid.length; i++) {
                con[i] = uid[i];
            }
             query.append("uid", new BasicDBObject().append("$in", con));
             DBCollection collection = MongoDBSupport.getInstance().getCollectionByName(Constant.USER_DATA);
             DBCursor cur = collection.find(query);
             if(cur != null ||"".equals(cur)) {
                 message = "user is deleted";
                 logger.info("user is deleted" + uid.toString());
             }
        }
        return message;
    }
    

    public static void main(String[] args) {
        
        IUserUpdateService  service = UserUpdateServiceImpl.getInstance();
        Long uid = 47053L;
        List<User> user = service.getUpdateUsersbyid(uid);
    }
}
