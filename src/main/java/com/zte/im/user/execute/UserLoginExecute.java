package com.zte.im.user.execute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.DataVer;
import com.zte.im.bean.Firm;
import com.zte.im.bean.User;
import com.zte.im.cache.redis.RedisSupport;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.IFirmService;
import com.zte.im.service.IUserService;
import com.zte.im.service.IVersionService;
import com.zte.im.service.impl.FirmServiceImpl;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.service.impl.VersionServiceImpl;
import com.zte.im.util.CheckApp;
import com.zte.im.util.Constant;
import com.zte.im.util.EncryptionDecryption;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.IDSequence;

/**
 * 用户登陆
 * 
 * @author Administrator
 * 
 */
public class UserLoginExecute extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory
			.getLogger(UserLoginExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		Long gid = json.getLong("gid");
		String jid = json.getString("jid");
		String pwd = json.getString("pwd");
		String sn = json.getString("sn");
		String resJson = null;
		ResponseMain main = new ResponseMain();
		String check = "1";
		if (null != sn && !"".equals(sn)) {
			try {
				check = CheckApp.save(sn);
				logger.info("check:" + check);
			} catch (Exception e) {
				logger.error("", e);
			}
		} else {
			check = "参数缺少";
		}

		IUserService service = UserServiceImpl.getInstance();
		IVersionService vService = VersionServiceImpl.getInstance();

		if (gid != null && jid != null && !"".equalsIgnoreCase(jid)
				&& pwd != null && !"".equalsIgnoreCase(pwd)) {
			User user = service.getUserbyid(gid, jid);
			IFirmService firmService = FirmServiceImpl.getInstance();
			Firm firm = firmService.getFirm(gid);
			if (user != null && firm != null) {
				String upwd = user.getPwd();
				if (upwd.equalsIgnoreCase(pwd)) {

					// 更新用户sn
					boolean isUserUpdated = false;
					if (sn != null && !sn.equals(user.getSn())) {
						if (user.getSn() != null) {
							// 记录安全日志
							TwitterServiceImpl.getInstance().saveSecureLog(
									user, "用户换终端登陆", "changeDevice");
						}
						user.setSn(sn);
						isUserUpdated = true;
					}

					// 密码输入错误清零
					if (user.getPwdErrors() != null && user.getPwdErrors() > 0) {
						user.setPwdErrors(0);
						isUserUpdated = true;
					}

					if (isUserUpdated) {
						service.updateUser(user);
					}

					Res res = new Res();
					res.setReCode(Constant.SUCCESS_CODE);
					res.setResMessage("登陆成功");
					res.setResCheckMessage(check);
					if ("1".equals(check)) {
						res.setResCheckCode(1);
					} else {
						res.setResCheckCode(0);
					}
					main.setRes(res);
					user.setPwd(null);
					user.setGid(gid);
					user.setGname(firm.getName());
					IDSequence idseq = new IDSequence();
					String token = idseq.getToken();
					user.setToken(token);
					user.setGroupids(null);// 不返回群组信息
					String j = GsonUtil.gson.toJson(user);
					RedisSupport.getInstance().set(token, j, -1);
					main.setUser(user);

					DataVer dataVer = vService.getDataVer();
					if (user.getGroupVer() != null) {
						dataVer.setGroupVer(user.getGroupVer());
					} else {
						dataVer.setGroupVer(0L);
					}
					main.setDataVer(dataVer);
					// 记录token信息，用来定时校验
					TwitterServiceImpl.getInstance().addToken(token);
					// 记录用户日志
					TwitterServiceImpl.getInstance().saveUserLog(user, "用户登录");
				} else {
					// 更新密码错误统计
					String result = null;
					if (user.getPwdErrors() == null || user.getPwdErrors() == 0) {
						user.setPwdErrors(1);
						result = getErrorRes("密码错误", Constant.USER_LOINGPWDERR);
					} else if (user.getPwdErrors() < 4) {
						user.setPwdErrors(user.getPwdErrors() + 1);
						result = getErrorRes(
								"密码错误，还有" + (4 - user.getPwdErrors()) + "次输入机会",
								Constant.USER_LOINGPWDERR);
					} else {
						user.setPwdErrors(0);
						result = getErrorRes("密码连续错误5次，当前账号被锁定",
								Constant.USER_LOCKED);
						// 记录安全日志
						TwitterServiceImpl.getInstance().saveSecureLog(user,
								"用户试密码超过5次", "pwdError");
					}
					service.updateUser(user);
					return result;
				}
			} else {
				return super.getErrorRes("用户不存在", Constant.USER_LOINGERR);
			}
		} else {
			return super.getErrorRes("登陆参数为空", Constant.USER_LOINGERR);
		}
		resJson = GsonUtil.gson.toJson(main);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		try {
			EncryptionDecryption enc = new EncryptionDecryption();
			String pwd = enc.encrypt("123456");
			json.put("pwd", pwd);
		} catch (Exception e) {
			logger.error("", e);
		}

		json.put("gid", 1);
		json.put("jid", "000010");
		logger.info(json.toJSONString());
		UserLoginExecute ex = new UserLoginExecute();
		String j = ex.doProcess(json);
		logger.info(j);
	}

}
