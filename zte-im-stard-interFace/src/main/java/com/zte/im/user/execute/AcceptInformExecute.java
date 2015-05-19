package com.zte.im.user.execute;

import org.eclipse.paho.sample.mqttv3app.MqttPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.User;
import com.zte.im.mybatis.bean.MnInform;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseInformMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.ICreateMnInformService;
import com.zte.im.service.ICreateMnParticipant;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.InformConstant;
import com.zte.im.util.InformErrorCode;

/**
 * 用户接受消息通知.
 * @author yejun
 *
 */
public class AcceptInformExecute extends AbstractValidatoExecute {
    
    private final static Logger logger = LoggerFactory.getLogger(AcceptInformExecute.class);
    
    @Override
    public String doProcess(JSONObject json) {
        
        ApplicationContext applicationContext = this.getContext().getApplicationContext();
        ICreateMnParticipant particiService = applicationContext.getBean(ICreateMnParticipant.class);
        ICreateMnInformService informService = applicationContext.getBean(ICreateMnInformService.class);
        
        User user = super.getContext().getUser();
        //获取登陆用户id
        String uid = user.getUid().toString();
        String informId = json.getString("informId");
        
        ResponseInformMain main = new ResponseInformMain();
        Res res = new Res();
        
        if(informId == null || "".equals(informId)) {
            logger.info("inform id is null, you can not accpet any inform");
            res.setResMessage("inform id is null, you can not accpet any inform");
            res.setReCode(InformErrorCode.MISSING_REQUIRED_ARGUMENTS);
            main.setRes(res);
            String resJson = GsonUtil.gson.toJson(main);
            return resJson;
        }
        
        MnInform inform = informService.queryMnInformById(informId);
        if(inform == null) {
            logger.info("query inform failed or you have no inform to accept, please query later");
            res.setResMessage("query inform failed or you have no inform to accept, please query later");
            main.setRes(res);
            res.setReCode(InformErrorCode.INSUFFICIENT_USER_PERMISSIONS);
            String resJson = GsonUtil.gson.toJson(main);
            return resJson;
        }
        //判断通知是否已经过期
        if(inform.getStatus().equalsIgnoreCase(InformConstant.expired)) {
            logger.info("this inform is expied, you can not deal this inform");
            res.setResMessage("this inform is expied, you can not deal this inform");
            res.setReCode(InformErrorCode.INSUFFICIENT_ISV_PERMISSIONS);
            main.setRes(res);
            String resJson = GsonUtil.gson.toJson(main);
            return resJson;
        }
        
        String creatorId = particiService.acceptInform(informId, uid);
        if(creatorId == null || "".equalsIgnoreCase(creatorId)) {
            logger.info("accept inform failed");
            res.setResMessage("accept inform failed, please accept later");
            res.setReCode(InformErrorCode.SERVICE_CURRENTLY_UNAVAILABLE);
            main.setRes(res);
            String resJson = GsonUtil.gson.toJson(main);
            return resJson;
        }
        // 发送mqtt通知消息
        JSONObject obj = new JSONObject();
        obj.put("name", user.getName());
        obj.put("uid", user.getUid());
        obj.put("informId", inform.getId());
        StringBuffer pushMsg = new StringBuffer();
        pushMsg.append(user.getName() + "已经接受参加主题为:" + inform.getTitle() + "会议");
        PushBean bean = new PushBean();
        bean.setTarget(creatorId);
        bean.setKeyid(uid);
        bean.setModeltype("3");
		bean.setContenttype("1");
        bean.setUsername(user.getName());
        bean.setMsg(pushMsg.toString());
        bean.setInform(obj.toJSONString());
        MqttPublisher.publish(bean);
        
        
        res.setReCode(Constant.SUCCESS_CODE);
        res.setResMessage("接受通知成功");
        main.setRes(res);
        String resJson = GsonUtil.gson.toJson(main);
        return resJson;
    }
}
