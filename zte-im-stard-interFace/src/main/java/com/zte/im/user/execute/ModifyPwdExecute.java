package com.zte.im.user.execute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import com.zte.im.bean.User;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.IUserService;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.util.Constant;

/**
 * 修改用户密码，需传入旧密码、新密码和确认新密码
 * 
 * @author kaka
 * 
 */
public class ModifyPwdExecute extends AbstractValidatoExecute {

	@Override
	public String doProcess(JSONObject json) {
		Long uid = super.getContext().getUser().getUid();
		String oldPwd = json.getString("oldPwd");
		String newPwd = json.getString("newPwd");
		String chkPwd = json.getString("chkPwd");
		if (uid == null || StringUtils.isNullOrEmpty(oldPwd)
				|| StringUtils.isNullOrEmpty(newPwd)
				|| StringUtils.isNullOrEmpty(chkPwd)) {
			return getErrorRes("param is empty");
		}
		IUserService service = UserServiceImpl.getInstance();
		User user = service.getUserbyid(uid);
		if (user == null) {
			return getErrorRes("user not exists");
		}
		if (!user.getPwd().equalsIgnoreCase(oldPwd)) {
			return getErrorRes("old password error");
		}
		if (!newPwd.equals(chkPwd)) {
			return getErrorRes("new password check error");
		}
		user.setPwd(newPwd);
		service.updateUser(user);
		ResponseMain main = new ResponseMain();
		Res res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		res.setResMessage("更新成功");
		main.setRes(res);
		TwitterServiceImpl.getInstance().saveUserLog(user, "修改密码", "修改密码成功");
		return JSON.toJSONString(main);

	}
}
