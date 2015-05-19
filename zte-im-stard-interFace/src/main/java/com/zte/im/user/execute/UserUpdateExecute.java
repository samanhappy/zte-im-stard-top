package com.zte.im.user.execute;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.paho.sample.mqttv3app.MqttPublisher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.UpdateRecord;
import com.zte.im.bean.User;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UpdateRecordServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;

/**
 * 更新用户信息，包括签名、头像、手机、固定电话、邮箱等字段
 * @author samanhappy
 *
 */
public class UserUpdateExecute extends AbstractValidatoExecute {

	@Override
	public String doProcess(JSONObject json) {
		Long uid = super.getContext().getUser().getUid();
		String autograph = json.getString("autograph");
		String hp = json.getString("minipicurl");
		String shp = json.getString("bigpicurl");
		String otherMob = json.getString("otherMob");//手机号码，逗号分隔                                                                                                                                                                                                                                                                                                                                                                                            
		String email = json.getString("email");
		String otherTele = json.getString("otherTele");//固定电话，逗号分隔
		String fax = json.getString("fax");
		String remark = json.getString("remark");
		if (uid == null) {
			return getErrorRes("param is empty");
		}

		IUserService service = UserServiceImpl.getInstance();
		User user = service.getUserbyid(uid);
		if (user == null) {
			return getErrorRes("user not exists");
		}

		PushBean bean = null;
		StringBuilder oper = new StringBuilder();
		if (autograph != null) {
			// 更新签名
			user.setAutograph(autograph);
			oper.append("签名");
		}
		if (hp != null) {
			// 更新头像
			user.setMinipicurl(hp);
			if (oper.length() == 0) {
				oper.append("头像");
			} else {
				oper.append("、头像");
			}
			if (shp != null) {
				// 更新头像原图
				user.setBigpicurl(shp);
			}
			bean = new PushBean();
			bean.setUid(String.valueOf(uid));
			bean.setContenttype("7");
			bean.setMinipicurl(hp);
			bean.setBigpicurl(shp);
			bean.setModeltype("0");
		}
		if (!StringUtils.isNullOrEmpty(otherMob)) {
			user.setOtherMob(otherMob);
			if (oper.length() == 0) {
				oper.append("手机");
			} else {
				oper.append("、手机");
			}
		}
		if (!StringUtils.isNullOrEmpty(otherTele)) {
			user.setOtherTele(otherTele);
			if (oper.length() == 0) {
				oper.append("电话");
			} else {
				oper.append("、电话");
			}
		}
		if (!StringUtils.isNullOrEmpty(email)) {
			user.setEmail(email);
			if (oper.length() == 0) {
				oper.append("邮箱");
			} else {
				oper.append("、邮箱");
			}
		}
		if (!StringUtils.isNullOrEmpty(fax)) {
			user.setFax(fax);
			if (oper.length() == 0) {
				oper.append("传真");
			} else {
				oper.append("、传真");
			}
		}
		if (!StringUtils.isNullOrEmpty(remark)) {
			user.setRemark(remark);
			if (oper.length() == 0) {
				oper.append("备注");
			} else {
				oper.append("、备注");
			}
		}
		service.updateUser(user);

		if (bean != null) {
			// 发送群消息通知头像变化
			MqttPublisher.publish(bean);
		}

		ResponseMain main = new ResponseMain();
		Res res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		res.setResMessage("更新成功");
		main.setRes(res);
		TwitterServiceImpl.getInstance().saveUserLog(user, "用户修改个人信息",
				oper.toString());
		
		//记录用户更新信息
		UpdateRecord record = new UpdateRecord();
		record.setGid(user.getGid());
		record.setUid(user.getUid());
		record.setUserListVer(new Date().getTime());
		record.setOper("changed");
		UpdateRecordServiceImpl.getInstance().saveUpdateRecord(record);
		
		return JSON.toJSONString(main);
	}
	
	  public static void main(String[] args) {
	      AbstractValidatoExecute  abvexecute = new UserUpdateExecute();
	      JSONObject json = new JSONObject();
          json.put("token", "0000380_1Q1MCMDL");
          json.put("otherMob", "13685534866");
          Context context = new Context();
          context.setJsonRequest(json.toJSONString());
          context.setMethod("userupdate");
          User user = new User();
          user.setGid(1L);
          user.setUid(47043L);
          context.setUser(user);
          abvexecute.setContext(context);
          String result = abvexecute.doProcess(json);
          JSON.toJSON(result);
	    }
}
