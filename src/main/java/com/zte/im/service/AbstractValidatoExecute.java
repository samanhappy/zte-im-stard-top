package com.zte.im.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.DataVer;
import com.zte.im.bean.User;
import com.zte.im.cache.redis.RedisSupport;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.impl.VersionServiceImpl;
import com.zte.im.util.Constant;

public abstract class AbstractValidatoExecute extends BaseInteExecute {

	private final static Logger logger = LoggerFactory
			.getLogger(AbstractValidatoExecute.class);

	public Context context;
	public String[] methods = new String[] { "login", "softwareUpdate", "auth" };
	// 做版本控制的接口
	public String[] versionMethods = new String[] { "listApp", "listAppCate",
			"findGroup", "enterpriseContact" };

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String doProcess(Context executeUtilBean) {
		String result = null;
		String jsonRequest = executeUtilBean.getJsonRequest();
		if (jsonRequest == null || "".equals(jsonRequest)
				|| "null".equals(jsonRequest)) {
			return BaseInteExecute.getErrorRes("param is emtpy.");
		}
		JSONObject fastj = JSON.parseObject(jsonRequest.toString());
		User user = null;
		if (!Arrays.toString(methods).contains(executeUtilBean.getMethod())) {
			String token = fastj.getString("token");
			if (token == null) {
				return getErrorRes("token Missing", Constant.USER_RELOING);
			}
			String tokenUser = RedisSupport.getInstance().get(token);
			if (tokenUser == null || "".equalsIgnoreCase(tokenUser)) {
				return getErrorRes("token Expired", Constant.USER_RELOING);
			} else {
				user = JSON.parseObject(tokenUser, User.class);
				executeUtilBean.setUser(user);
			}
		}

		IVersionService service = VersionServiceImpl.getInstance();
		// 版本控制
		if (Arrays.toString(versionMethods).contains(
				executeUtilBean.getMethod())) {
			if (!service
					.isDataUpdated(executeUtilBean.getMethod(), fastj, user)) {
				Res r = new Res();
				r.setReCode(Constant.SUCCESS_CODE);
				r.setResMessage("data not updated");
				ResponseMain rm = new ResponseMain();
				rm.setRes(r);
				DataVer dataVer = service.getDataVer();
				if (user.getGroupVer() != null) {
					dataVer.setGroupVer(user.getGroupVer());
				} else {
					dataVer.setGroupVer(0L);
				}
				rm.setDataVer(dataVer);
				return JSON.toJSONString(rm);
			}
		}

		setContext(executeUtilBean);

		try {
			result = doProcess(fastj);
		} catch (Exception e) {
			logger.error("", e);
			return getErrorRes("服务器异常", Constant.ERROR_CODE);
		}

		return result;
	}

	public abstract String doProcess(JSONObject json);

}
