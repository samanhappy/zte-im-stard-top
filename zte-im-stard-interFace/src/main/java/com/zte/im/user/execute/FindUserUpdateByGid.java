package com.zte.im.user.execute;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.User;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseContactsMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IUserUpdateService;
import com.zte.im.service.impl.UserUpdateServiceImpl;
import com.zte.im.service.impl.UpdateRecordServiceImpl;

public class FindUserUpdateByGid extends AbstractValidatoExecute {
    
    private final static Logger logger = LoggerFactory.getLogger(FindUserUpdateByGid.class);

	@SuppressWarnings("rawtypes")
    @Override
	public String doProcess(JSONObject json) {
		User user = super.getContext().getUser();
        String resJson = null;
        Long newVersion = null;
        List<List> uids = null;
        Long[] addUid = null;
        Long[] chanUid = null;
        List<String> delUid = new ArrayList<String>();
        if (user != null) {
            Long gid = user.getGid();
            Long userListVer = (Long) json.get("userListVer");
            ResponseContactsMain listmain = new ResponseContactsMain();
            uids = UpdateRecordServiceImpl.getInstance().getUpdateRecordbyid(gid, userListVer);
            
            //读取新增用户uid
            if(uids.get(0) != null && !"".equals(uids.get(0))) {
                addUid = new Long[uids.get(0).size()];
                for(int i = 0; i < uids.get(0).size(); i++) {
                    addUid[i] = (Long) uids.get(0).get(i);
                }
            }  
            //读取更新用户uid
            if(uids.get(1) != null && !"".equals(uids.get(1))) {
                chanUid = new Long[uids.get(1).size()];
                for(int i = 0; i < uids.get(1).size(); i++) {
                    chanUid[i] = (Long) uids.get(1).get(i);
                }
            }
            //读取删除用户uid
            if(uids.get(2) != null && !"".equals(uids.get(2))) {
                for(int i = 0; i < uids.get(2).size(); i++) {
                    delUid.add(uids.get(2).get(i).toString());
                }
            }
            newVersion = (Long) uids.get(3).get(0);
            //查询记录信息
            IUserUpdateService service = UserUpdateServiceImpl.getInstance();
            List<User> addUser = service.getAddUsersbyid(addUid);
            List<User> chanUser = service.getUpdateUsersbyid(chanUid);
            Res res = new Res();
            res.setResMessage("update user info is successful");
            listmain.setNewVersion(newVersion);
            listmain.setAdded(addUser);
            listmain.setChanged(chanUser);
            listmain.setRes(res);
            listmain.setDeleted(delUid);
            listmain.setNewVersion(userListVer);
            resJson = JSON.toJSONString(listmain);    
        } else {
            resJson = getErrorRes("token not find by user.");
	}
        return resJson;
	}
	
	   public static void main(String[] args) {
	        AbstractValidatoExecute e = new FindUserUpdateByGid();
	        JSONObject json = new JSONObject();
	        json.put("token", "0000380_1Q1MCMDL");
	        json.put("timestamp", 1426485454708L);
	        Context context = new Context();
	        context.setJsonRequest(json.toJSONString());
	        logger.info(json.toJSONString());
	        context.setMethod("updatecontacts");
	        //String tokenUser = RedisSupport.getInstance().get("0000380_1Q1MCMDL");
	        //User user = JSON.parseObject(tokenUser, User.class);
	        User user = new User();
	        user.setGid(1L);
	        context.setUser(user);
	        e.setContext(context);
	        String result = e.doProcess(json);
	        JSON.toJSON(result);
	        logger.info(result);
	    }
}
