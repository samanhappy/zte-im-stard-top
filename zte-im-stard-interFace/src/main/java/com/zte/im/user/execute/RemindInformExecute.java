package com.zte.im.user.execute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.sample.mqttv3app.MqttPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.User;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseInformMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.ICreateMnInformService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.InformErrorCode;

/**
 * 取消消息通知.
 * @author yejun
 *
 */
public class RemindInformExecute extends AbstractValidatoExecute {
    
    private final static Logger logger = LoggerFactory.getLogger(RemindInformExecute.class);
    
    @SuppressWarnings("unchecked")
    @Override
    public String doProcess(JSONObject json) {
        
        ApplicationContext applicationContext = this.getContext().getApplicationContext();
        ICreateMnInformService informService = applicationContext.getBean(ICreateMnInformService.class);
        
        User user = super.getContext().getUser();
        //获取登陆用户id
        String uid = user.getUid().toString();
        String informId = json.getString("informId");
        
        ResponseInformMain main = new ResponseInformMain();
        Res res = new Res();
        
        if(informId == null || "".equalsIgnoreCase(informId)) {
            logger.info("inform id is null, you can not reminder any participants");
            res.setResMessage("inform id is null, you can not reminder any participants");
            res.setReCode(InformErrorCode.MISSING_REQUIRED_ARGUMENTS);
            main.setRes(res);
            String resJson = GsonUtil.gson.toJson(main);
            return resJson;
        }
        //通知所有未处理会议的人
        Map<String, Object> result = informService.reminderMnInform(informId, uid);
        if(result == null) {
            logger.info("remind inform failed,there is something wrong in it");
            res.setResMessage("remind inform failed,there is something wrong in it");
            res.setReCode(InformErrorCode.FORBIDDEN_REQUEST);
            main.setRes(res);
            String resJson = GsonUtil.gson.toJson(main);
            return resJson;
        }
        
        List<String> uids = (List<String>) result.get("uids");
        // 发送mqtt通知消息
        JSONObject obj = new JSONObject();
        obj.put("title", result.get("title"));
        obj.put("informId", informId);
        StringBuffer pushMsg = new StringBuffer();
        pushMsg.append("未处理会议提醒:"+ user.getName() +"创建标题为:"  + result.get("title") + "的会议");
        List<PushBean> list = new ArrayList<PushBean>();
        for(String partId : uids) {
            PushBean bean = new PushBean();
            bean.setTarget(partId);
            bean.setKeyid(uid);
            bean.setMsg(pushMsg.toString());
            bean.setModeltype("3");
			bean.setContenttype("4");
            bean.setUsername(user.getName());
            bean.setMsg(pushMsg.toString());
            bean.setInform(obj.toJSONString());
            list.add(bean);
        }
        MqttPublisher.publish(list);
        
        res.setReCode(Constant.SUCCESS_CODE);
        res.setResMessage("未处理会议提醒成功");
        main.setRes(res);
        String resJson = GsonUtil.gson.toJson(main);
        return resJson;
    }
}
