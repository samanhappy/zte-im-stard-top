package com.zte.im.user.execute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.User;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.EncryptionDecryption;

public class UserRegExecute extends AbstractValidatoExecute {

	@Override
	public String doProcess(JSONObject json) {
		Long gid = json.getLong("gid");
		String jid = json.getString("jid");
		String pwd = json.getString("pwd");
		IUserService service = UserServiceImpl.getInstance();
		String resJson = null;
		if (gid != null && jid != null && !"".equalsIgnoreCase(jid)
				&& pwd != null && !"".equalsIgnoreCase(pwd)) {

			User user = service.getUserbyid(gid, jid);
			if (user != null) {// 该用户已经注册
				Res r = new Res();
				r.setReCode(Constant.ERROR_CODE);
				r.setResMessage("用户已经注册");
				ResponseMain rm = new ResponseMain();
				rm.setRes(r);
				return JSON.toJSONString(rm);
			} else {
				// 开始注册
				user = service.regUser(gid, jid, pwd);
				Res r = new Res();
				r.setReCode(Constant.SUCCESS_CODE);
				r.setResMessage("注册成功");
				ResponseMain rm = new ResponseMain();
				rm.setRes(r);
				rm.setUser(user);
				resJson = JSON.toJSONString(rm);
			}
		} else {
			return super.getErrorRes("value is null");
		}
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("id", "123");
		json.put("name", "aaa");
		json.put("pwd", "aaa");
		new UserRegExecute().doProcess(json);
	}
}
