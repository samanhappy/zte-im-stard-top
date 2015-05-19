package com.zte.im.user.execute;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.sample.mqttv3app.MqttPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.User;
import com.zte.im.mybatis.bean.MnInform;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseInformMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.ICreateMnInformService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.InformErrorCode;
import com.zte.im.util.JsonArraytoStringArray;

/**
 * 新建消息通知.
 * @author yejun
 *
 */
public class CreateInformExecute extends AbstractValidatoExecute {
    
    private final static Logger logger = LoggerFactory.getLogger(CreateInformExecute.class);
    
    @Override
    public String doProcess(JSONObject json) {
        
        ApplicationContext applicationContext = this.getContext().getApplicationContext();
        ICreateMnInformService service = applicationContext.getBean(ICreateMnInformService.class);
        
        User user = super.getContext().getUser();
        //获取登陆用户id
        String uid = user.getUid().toString();
        MnInform form = new MnInform();
        //参会人员
        String[] uids = null;
        String title = json.getString("title");
        String address = json.getString("address");
        String remark = json.getString("remark");
        String startTime = json.getString("startTime");
        String endTime = json.getString("endTime");
        JSONArray users = json.getJSONArray("users");
        
        //返回给客户端的实体类
        ResponseInformMain main = new ResponseInformMain();
        Res res = new Res();
        String resJson = null;
        if (uid == null || "".equals(uid)) {
            logger.info("create inform failed, login uid is null");
            res.setResMessage("create inform failed, login uid is null");
            res.setReCode(InformErrorCode.MISSING_REQUIRED_ARGUMENTS);
            main.setRes(res);
            resJson = GsonUtil.gson.toJson(main);
            return resJson;
        }
        form.setCreator(uid);
        
        if (title != null && !"".equals(title)) {
            if (title.getBytes().length > 100) {
                logger.info("title length cannot be greater than 100 bytes");
                res.setResMessage("create inform failed, title length cannot be greater than 100 bytes");
                res.setReCode(InformErrorCode.INVALID_ARGUMENTS);
                main.setRes(res);
                resJson = GsonUtil.gson.toJson(main);
                return resJson;
            }
            form.setTitle(title);
        }
        
        if (address != null && !"".equals(address)) {
            if (address.getBytes().length > 100) {
                logger.info("address length cannot be greater than 100 bytes");
                res.setResMessage("create inform failed, address length cannot be greater than 100 bytes");
                res.setReCode(InformErrorCode.INVALID_ARGUMENTS);
                main.setRes(res);
                resJson = GsonUtil.gson.toJson(main);
                return resJson;
            }
            form.setAddress(address);
        }
        
        if ((startTime != null &&  !"".equals(startTime)) 
                || (endTime != null && !"".equals(startTime))) {
            Date start = new Date(Long.parseLong(startTime));
            Date end = new Date(Long.parseLong(endTime));
            //判断会议开始时间是否大于或等于结束时间
            if (start.after(end) || start.equals(end)) { 
                logger.info("create mnInform is failed, inform start time is greater than the end time");
                res.setResMessage("create mnInform is failed, inform start time is greater than the end time");
                res.setReCode(InformErrorCode.INVALID_ARGUMENTS);
                main.setRes(res);
                resJson = GsonUtil.gson.toJson(main);
                return resJson;
            }
            form.setStartTime(start);
            form.setEndTime(end);
        } 
        if (users == null || users.size() == 0) {
            logger.info("create mnInform is failed, users can not be null");
            res.setResMessage("create mnInform is failed, users can not be null");
            res.setReCode(InformErrorCode.MISSING_REQUIRED_ARGUMENTS);
            main.setRes(res);
            resJson =  GsonUtil.gson.toJson(main);
            return resJson;
        }
        uids = JsonArraytoStringArray.parseJsonString(users);
        
        if (remark != null && !"".equals(remark)) {
            if (remark.getBytes().length > 1000) {
                logger.info("create mnInform is failed,remark length cannot be greater than 1000 bytes");
                res.setResMessage("create mnInform is failed, remark length cannot be greater than 1000 bytes");
                res.setReCode(InformErrorCode.INVALID_ARGUMENTS);
                main.setRes(res);
                resJson =  GsonUtil.gson.toJson(main);
                return resJson;
            }
            form.setRemark(remark);
        }
        Map<String, Object> result = service.saveMnInform(form, uids);
        if (result == null) {
            logger.info("create inform failed, result map is null");
            res.setResMessage("create mnInform is failed, result map is null");
            res.setReCode(InformErrorCode.SERVICE_CURRENTLY_UNAVAILABLE);
            main.setRes(res);
            resJson =  GsonUtil.gson.toJson(main);
            return resJson;
        }
        
        // 发送mqtt通知消息
        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("informId", result.get("informId"));
        StringBuffer pushMsg = new StringBuffer();
        pushMsg.append(user.getName() + "新建主题为:" + title + "会议");
        List<PushBean> list = new ArrayList<PushBean>();
        for (int i = 0; i < uids.length; i++) {
            PushBean bean = new PushBean();
            bean.setTarget(uids[i]);
            bean.setKeyid(uid);
            bean.setMsg(pushMsg.toString());
            bean.setModeltype("3");
			bean.setContenttype("0");
            bean.setUsername(user.getName());
            bean.setMsg(pushMsg.toString());
            bean.setInform(obj.toJSONString());
            list.add(bean);
        }
        MqttPublisher.publish(list);
        
        res.setReCode(Constant.SUCCESS_CODE);
        res.setResMessage("新建通知成功");
        main.setInformId(result.get("informId").toString());
        main.setCreatTime((Long) result.get("creatTime"));
        main.setRes(res);
        resJson = GsonUtil.gson.toJson(main);
        return resJson;
    }
}
