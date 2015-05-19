package com.zte.im.user.execute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.User;
import com.zte.im.mybatis.bean.MnInformByMap;
import com.zte.im.mybatis.bean.MnParticipant;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseInformMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.ICreateMnInformService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.InformErrorCode;

/**
 * 获取消息通知.
 * @author yejun
 *
 */
public class GetInformExecute extends AbstractValidatoExecute {
    
    private final static Logger logger = LoggerFactory.getLogger(GetInformExecute.class);
    
    @SuppressWarnings("unchecked")
    @Override
    public String doProcess(JSONObject json) {
        
        ApplicationContext applicationContext = this.getContext().getApplicationContext();
        ICreateMnInformService informService = applicationContext.getBean(ICreateMnInformService.class);
        
        User user = super.getContext().getUser();
        //获取登陆用户id
        String uid = user.getUid().toString();
        //获取客户端传来的参数
        Integer  offset = json.getInteger("offset");
        Integer limit = json.getInteger("limit");
        String informId = json.getString("informId");
     
        //返回给客户端的实体类
        ResponseInformMain main = new ResponseInformMain();
        Res res = new Res();
        if(uid == null || "".equals(uid)) {
            logger.info(" uid is null, you can not query any inform");
            res.setResMessage("uid id is null, you can not query any inform");
            res.setReCode(InformErrorCode.MISSING_REQUIRED_ARGUMENTS);
            main.setRes(res);
            String resJson = GsonUtil.gson.toJson(main);
            return resJson;
        }
        
        if(limit != null && limit >1000) {
            logger.info(" limit is too long, limit can not greater than 1000");
            res.setResMessage("limit is too long, limit can not greater than 1000");
            res.setReCode(InformErrorCode.INVALID_ARGUMENTS);
            main.setRes(res);
            String resJson = GsonUtil.gson.toJson(main);
            return resJson;
        } else if(limit == null || "".equals(limit.toString())) {
            limit = 20;
        }
        
        if(offset == null || "".equals(offset.toString())) {
            offset = 0;
        }
        
        Map<String, Object> result = informService.queryInformByMap(uid, informId, limit, offset);
        List<MnInformByMap> item = new ArrayList<MnInformByMap>();
        if(result.get("mnInform") != null) {
            item = (List<MnInformByMap>) result.get("mnInform");
            main.setItem(item);
        }
        List<MnParticipant> users = new ArrayList<MnParticipant>();
        if(result.get("users") != null) {
            users = (List<MnParticipant>) result.get("users");
            main.setUsers(users);
        }
        if(result.get("count") != null || "".equals(result.get("count"))) {
            int count = (Integer) result.get("count");
            main.setCount(count);
        }
        res.setReCode(Constant.SUCCESS_CODE);
        res.setResMessage("获取通知成功");
        main.setRes(res);
        String resJson =  JSON.toJSONString(main);
        return resJson;
    }
}
