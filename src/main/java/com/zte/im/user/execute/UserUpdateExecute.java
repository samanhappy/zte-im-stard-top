package com.zte.im.user.execute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.PushBean;
import com.zte.im.bean.User;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.CmdExecute;
import com.zte.im.util.Constant;

public class UserUpdateExecute extends AbstractValidatoExecute {

	@Override
	public String doProcess(JSONObject json) {
		Long uid = super.getContext().getUser().getUid();
		String autograph = json.getString("autograph");
		String hp = json.getString("minipicurl");
		String shp = json.getString("bigpicurl");
		if (uid == null) {
			return getErrorRes("param is empty");
		}

		IUserService service = UserServiceImpl.getInstance();
		User user = service.getUserbyid(uid);
		if (user == null) {
			return getErrorRes("user not exists");
		}

		PushBean bean = null;
		String oper = "";
		if (autograph != null) {
			// 更新签名
			user.setAutograph(autograph);
			oper = "修改签名";
		}
		if (hp != null) {
			// 更新头像
			user.setMinipicurl(hp);
			if ("".equals(oper)) {
				oper = "修改头像";
			} else {
				oper = "修改签名和头像";
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

		service.updateUser(user);

		if (bean != null) {
			// 发送群消息通知头像变化
			String error = CmdExecute.exe(bean);
			if (error == null || error.equals("")) {
				error = "发送成功";
			}
		}

		ResponseMain main = new ResponseMain();
		Res res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		res.setResMessage("更新成功");
		main.setRes(res);
		TwitterServiceImpl.getInstance().saveUserLog(user, oper);
		return JSON.toJSONString(main);
	}
}
