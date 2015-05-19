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
import com.zte.im.sso.HttpClientForSSO;
import com.zte.im.sso.SSOParam;
import com.zte.im.sso.SSOResp;
import com.zte.im.util.CheckApp;
import com.zte.im.util.Constant;
import com.zte.im.util.EncryptionDecryption;
import com.zte.im.util.GsonUtil;

/**
 * 用户登陆
 * 
 * @author Administrator
 * 
 */
public class UserAuthExecute extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(UserAuthExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		String token = json.getString("token");
		String sn = json.getString("sn");
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
		String resJson = null;

		if (token != null && !"".equals(token)) {
			// 通过SSO获取的token登录
			SSOParam param = new SSOParam();
			param.setToken(token);
			SSOResp resp = HttpClientForSSO.getInstance().request(param);
			if (resp != null && "0".equals(resp.RC)) {
				if (resp.UId != null && !"".equals(resp.UId)) {
					User user = service.getUserbyLoginname(resp.UId);
					if (user == null) {
						return super
								.getErrorRes("服务器数据出错", Constant.ERROR_CODE);
					}
					
					/*
					 * 更新用户sn
					 */
					if (sn != null) {
						user.setSn(sn);
						service.updateUser(user);
					}
					
					user.setPwd(null);
					if (user.getGid() != null) {
						IFirmService firmService = FirmServiceImpl
								.getInstance();
						Firm firm = firmService.getFirm(user.getGid());
						user.setGname(firm != null ? firm.getName() : "");
					}
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
					Res res = new Res();
					res.setReCode(Constant.SUCCESS_CODE);
					res.setResMessage("认证成功");
					res.setResCheckMessage(check);
					if ("1".equals(check)) {
						res.setResCheckCode(1);
					} else {
						res.setResCheckCode(0);
					}
					main.setRes(res);
					// 记录token信息，用来定时校验
					TwitterServiceImpl.getInstance().addToken(token);
					// 记录用户日志
					TwitterServiceImpl.getInstance().saveUserLog(user, "用户登录");
				} else {
					return super.getErrorRes("服务器数据出错", Constant.ERROR_CODE);
				}
			} else if (resp != null && "1".equals(resp.RC)) {
				return super.getErrorRes("TOKEN失效", Constant.USER_RELOING);
			} else {
				return super.getErrorRes("服务器出错", Constant.ERROR_CODE);
			}
		} else {
			return super.getErrorRes("认证token为空", Constant.USER_LOINGERR);
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
		json.put("jid", "0220000669");
		logger.info(json.toJSONString());
		UserAuthExecute ex = new UserAuthExecute();
		String j = ex.doProcess(json);
		logger.info(j);
	}

}
