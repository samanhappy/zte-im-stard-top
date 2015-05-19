package com.zte.im.twitter.execute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.sample.mqttv3app.MqttPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.TGroup;
import com.zte.im.bean.TSysMessage;
import com.zte.im.bean.User;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.ITwitterService;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.IDSequence;

/**
 * 处理系统信息
 * 
 * @author kaka
 * 
 */
public class OperSysMsg extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(OperSysMsg.class);

	private static String OPERTYPE_YES = "01";// 同意

	private static String MESSAGETYPE_01 = "01";// 申请

	private static String MESSAGETYPE_02 = "02";// 邀请

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String resFlag = Constant.TRUE;
		String msg = "请求成功";
		try {
			Long userId = json.getLong("userId");
			Long messageId = json.getLong("messageId");
			String operType = json.getString("operType");
			List<PushBean> list = new ArrayList<PushBean>();
			IUserService userService = UserServiceImpl.getInstance();
			ITwitterService twitterService = TwitterServiceImpl.getInstance();
			if(null != operType){
				TSysMessage sysMsg = TwitterServiceImpl.getInstance()
						.getSysMessage(messageId);
				if (null != sysMsg && null != sysMsg.getMessageStatus() && "00".equals(sysMsg.getMessageStatus())){
					Date date = new Date();
					Long groupId = sysMsg.getGroupId();
					String msgType = sysMsg.getMessageType();
					Long sendUserId = sysMsg.getSendUserId();//系统消息发送者id
					Long receiveUserId =  sysMsg.getReceiveUserId();//系统消息接受者id
					Long uid = null;//申请或者被邀请人id
					if (null != msgType && MESSAGETYPE_01.equals(msgType))// 申请
					{
						uid = sendUserId;
					} else if (null != msgType && MESSAGETYPE_02.equals(msgType))// 邀请
					{
						uid = receiveUserId;
					}
					User user = userService.getUserbyid(uid);// 申请或者被邀请人信息
					TGroup group = twitterService.findGroupDetail(groupId);
					if(group != null){
						Long groupCreateUserId = group.getCreateUserId();//圈子创建者
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("groupName", group.getGroupName());
						map.put("groupId", groupId);
						map.put("userName", user.getName());
						map.put("userId", uid);
						//同意操作
						if (OPERTYPE_YES.equals(operType)) {
							Map<String, Object> param = new HashMap<String, Object>();
							param.put("groupId", groupId);
							param.put("status", "00");
							param.put("createUserId", userId);
							param.put("createTime", date.getTime());
							param.put("userId", uid);
							param.put("updateUserId", userId);
							param.put("updateTime", date.getTime());
							param.put("remark", "");
							twitterService.inviteGroupUser(param); //保存用户与圈子关联关系

							//推送给申请人或者被邀请人，提示已加入圈子
							PushBean bean = new PushBean();
							bean.setTarget(String.valueOf(uid));//推送给被邀请人或者申请人，提示已加入xx圈子
							bean.setKeyid(String.valueOf(super.getContext().getUser()
									.getUid()));
							bean.setModeltype("1");
							bean.setType("19");// 提示用户已加入圈子
							bean.setSessionid(String.valueOf(userId));
							bean.setSessionname("已加入圈子");
							Long messageId1 = IDSequence.getUID();
							map.put("messageId", messageId1);
							bean.setMsg(JSON.toJSONString(map));

							// 提示已加入圈子,保存进系统消息
							Map<String, Object> paramMes = new HashMap<String, Object>();
							paramMes.put("messageId", messageId1);
							paramMes.put("groupName", group.getGroupName());
							paramMes.put("groupId", groupId);
							paramMes.put("sendUserId", userId);
							paramMes.put("receiveUserId", uid);
							paramMes.put("messageType", "03");// 03：提示已加入圈子
							paramMes.put("messageStatus", "01");// 01：已处理
							paramMes.put("createUserId", userId);
							paramMes.put("createTime", date.getTime());
							paramMes.put("updateUserId", userId);
							paramMes.put("updateTime", date.getTime());
							paramMes.put("remark", "");
							twitterService.addSysMessage(paramMes);

							//推送圈子创建者，提示xx加入圈子
							PushBean pbean = new PushBean();
							pbean.setTarget(String.valueOf(groupCreateUserId));
							pbean.setKeyid(String.valueOf(super.getContext().getUser()
									.getUid()));
							pbean.setModeltype("1");
							pbean.setType("20");// 提示圈子创建者xx用户已加入圈子
							pbean.setSessionid(String.valueOf(userId));
							pbean.setSessionname("提示圈子创建者xx用户已加入圈子");
							Long messageId2 = IDSequence.getUID();
							map.put("messageId", messageId2);
							pbean.setMsg(JSON.toJSONString(map));

							// 提示圈子创建者，xx加入圈子,保存进系统消息
							Map<String, Object> paramMes1 = new HashMap<String, Object>();
							paramMes1.put("messageId", messageId2);
							paramMes1.put("groupName", group.getGroupName());
							paramMes1.put("groupId", groupId);
							paramMes1.put("sendUserId", userId);
							paramMes1.put("receiveUserId", uid);
							paramMes1.put("messageType", "04");// 04：提示圈子创建者，xx加入圈子
							paramMes1.put("messageStatus", "01");// 01：已处理
							paramMes1.put("createUserId", userId);
							paramMes1.put("createTime", date.getTime());
							paramMes1.put("updateUserId", userId);
							paramMes1.put("updateTime", date.getTime());
							paramMes1.put("remark", "");
							twitterService.addSysMessage(paramMes1);

							if (null != msgType && MESSAGETYPE_02.equals(msgType) && !groupCreateUserId.equals(sendUserId))// 系统消息是邀请类型，且发出邀请人不是圈子创建者(防止同一个人推送两边)
							{
								//推送给发出邀请的人，被邀请用户已加入xx圈子
								PushBean tbean = new PushBean();
								tbean.setTarget(String.valueOf(sendUserId));
								tbean.setKeyid(String.valueOf(super.getContext().getUser()
										.getUid()));
								tbean.setModeltype("1");
								tbean.setType("21");// 推送给发出邀请的人，被邀请用户已加入xx圈子
								tbean.setSessionid(String.valueOf(userId));
								tbean.setSessionname("推送给发出邀请的人，被邀请用户已加入xx圈子");
								Long messageId3 = IDSequence.getUID();
								map.put("messageId", messageId3);
								tbean.setMsg(JSON.toJSONString(map));
								list.add(tbean);

								// 提示给发出邀请的人，被邀请用户已加入xx圈子，保存进系统消息
								Map<String, Object> paramMes2 = new HashMap<String, Object>();
								paramMes2.put("messageId", messageId3);
								paramMes2.put("groupName", group.getGroupName());
								paramMes2.put("groupId", groupId);
								paramMes2.put("sendUserId", userId);
								paramMes2.put("receiveUserId", uid);
								paramMes2.put("messageType", "05");// 05：推送给发出邀请的人，被邀请用户已加入xx圈子
								paramMes2.put("messageStatus", "01");// 01：已处理
								paramMes2.put("createUserId", userId);
								paramMes2.put("createTime", date.getTime());
								paramMes2.put("updateUserId", userId);
								paramMes2.put("updateTime", date.getTime());
								paramMes2.put("remark", "");
								twitterService.addSysMessage(paramMes2);
							}
							list.add(bean);
							list.add(pbean);
						}else{		//拒绝操作
							// 推送给申请人或者被邀请人，提示(被)拒绝加入圈子
							PushBean bean = new PushBean();
							bean.setTarget(String.valueOf(uid));//推送给申请人或者被邀请人，提示（被）拒绝加入圈子
							bean.setKeyid(String.valueOf(super.getContext().getUser()
									.getUid()));
							Long messageId4 = IDSequence.getUID();
							map.put("messageId", messageId4);
							bean.setMsg(JSON.toJSONString(map));
							bean.setModeltype("1");
							if (null != msgType && MESSAGETYPE_01.equals(msgType))// 申请
							{
								bean.setType("23");// 推送给申请人，提示被拒绝加入圈子
								bean.setSessionname("推送给申请人，提示被拒绝加入圈子");
							}else{//被邀请
								bean.setType("26");// 推送给被邀请人，提示已拒绝加入圈子
								bean.setSessionname("推送给被邀请人，提示已拒绝加入圈子");
							}
							bean.setSessionid(String.valueOf(userId));
							list.add(bean);

							// 提示给申请人或者被邀请人，提示被拒绝加入圈子，保存进系统消息
							Map<String, Object> paramMes = new HashMap<String, Object>();
							paramMes.put("messageId", messageId4);
							paramMes.put("groupName", group.getGroupName());
							paramMes.put("groupId", groupId);
							paramMes.put("sendUserId", userId);
							paramMes.put("receiveUserId", uid);
							if (null != msgType && MESSAGETYPE_01.equals(msgType))// 申请
							{
								paramMes.put("messageType", "06");// 06：提示给申请人，被拒绝加入圈子
							}else{//被邀请
								paramMes.put("messageType", "09");// 09：提示被邀请人，已拒绝加入圈子
							}
							paramMes.put("messageStatus", "01");// 01：已处理
							paramMes.put("createUserId", userId);
							paramMes.put("createTime", date.getTime());
							paramMes.put("updateUserId", userId);
							paramMes.put("updateTime", date.getTime());
							paramMes.put("remark", "");
							twitterService.addSysMessage(paramMes);

							if (null != msgType && MESSAGETYPE_02.equals(msgType))// 邀请
							{
								//推送给发出邀请的人(圈子创建者)，被邀请用户拒绝加入xx圈子
								PushBean tbean = new PushBean();
								tbean.setTarget(String.valueOf(groupCreateUserId));
								tbean.setKeyid(String.valueOf(super.getContext().getUser()
										.getUid()));
								tbean.setModeltype("1");
								tbean.setType("22");// 推送给发出邀请的人，被邀请用户拒绝加入xx圈子
								tbean.setSessionid(String.valueOf(userId));
								tbean.setSessionname("推送给发出邀请的人（圈子创建者），被邀请用户拒绝加入xx圈子");
								Long messageId6 = IDSequence.getUID();
								map.put("messageId", messageId6);
								tbean.setMsg(JSON.toJSONString(map));
								list.add(tbean);

								// 提示给发出邀请的人，被邀请用户拒绝加入xx圈子，保存进系统消息
								Map<String, Object> paramMes2 = new HashMap<String, Object>();
								paramMes2.put("messageId", messageId6);
								paramMes2.put("groupName", group.getGroupName());
								paramMes2.put("groupId", groupId);
								paramMes2.put("sendUserId", userId);
								paramMes2.put("receiveUserId", uid);
								paramMes2.put("messageType", "07");// 07：提示给发出邀请的人，被邀请用户拒绝加入xx圈子
								paramMes2.put("messageStatus", "01");// 01：已处理
								paramMes2.put("createUserId", userId);
								paramMes2.put("createTime", date.getTime());
								paramMes2.put("updateUserId", userId);
								paramMes2.put("updateTime", date.getTime());
								paramMes2.put("remark", "");
								twitterService.addSysMessage(paramMes2);
							}else{//申请
								//推送圈子创建者，提示已拒绝xx用户加入xx圈子
								PushBean pbean = new PushBean();
								pbean.setTarget(String.valueOf(groupCreateUserId));
								pbean.setKeyid(String.valueOf(super.getContext().getUser()
										.getUid()));
								pbean.setModeltype("1");
								pbean.setType("25");// 推送圈子创建者，提示已拒绝xx用户加入xx圈子
								pbean.setSessionid(String.valueOf(userId));
								pbean.setSessionname("推送圈子创建者，提示已拒绝xx用户加入xx圈子");
								Long messageId5 = IDSequence.getUID();
								map.put("messageId", messageId5);
								pbean.setMsg(JSON.toJSONString(map));
								list.add(pbean);

								// 提示圈子创建者，已拒绝xx用户加入xx圈子,保存进系统消息
								Map<String, Object> paramMes1 = new HashMap<String, Object>();
								paramMes1.put("messageId", messageId5);
								paramMes1.put("groupName", group.getGroupName());
								paramMes1.put("groupId", groupId);
								paramMes1.put("sendUserId", userId);
								paramMes1.put("receiveUserId", uid);
								paramMes1.put("messageType", "08");// 08：提示圈子创建者，已拒绝xx用户加入xx圈子
								paramMes1.put("messageStatus", "01");// 01：已处理
								paramMes1.put("createUserId", userId);
								paramMes1.put("createTime", date.getTime());
								paramMes1.put("updateUserId", userId);
								paramMes1.put("updateTime", date.getTime());
								paramMes1.put("remark", "");
								twitterService.addSysMessage(paramMes1);
							}
						}

						twitterService.operSysMsg(messageId, operType,
								userId);// 更新状态,邀请或者申请处理完后状态改为-1，查询系统消息列表时不显示处理过历史申请邀请消息
						MqttPublisher.publish(list);
					}else{
						resFlag = Constant.CODE_3;
						msg = Constant.CODE_3; 
					}
				}else{
					resFlag = Constant.CODE_14;
					msg = Constant.CODE_14; 
				}
			}
		} catch (Exception e) {
			resFlag = Constant.ERROR_CODE;
			msg = e.getMessage();
			logger.error("", e);
		}
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> res2 = new HashMap<String, Object>();
		res2.put("resMessage", ""+msg);
		res2.put("resCode", resFlag);
		res2.put("vs", Constant.TRUE);
		res.put("res", res2);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("userId", 33);
		json.put("token", "4602658_75JCEGDQ");
		json.put("messageId", 2142);
		json.put("operType", "01");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new OperSysMsg();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("operSysMsg");
		String result = e.doProcess(context);
		logger.info(result);
	}

}
