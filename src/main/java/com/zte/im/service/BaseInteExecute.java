package com.zte.im.service;

import com.alibaba.fastjson.JSON;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.util.Constant;

public abstract class BaseInteExecute {

	public abstract String doProcess(Context executeUtilBean);

	public static String getErrorRes(String errMessage) {
		if (errMessage == null || "".equals(errMessage.trim())) {
			errMessage = "server err.";
		}
		Res r = new Res();
		r.setReCode(Constant.ERROR_CODE);
		r.setResMessage(errMessage);
		ResponseMain rm = new ResponseMain();
		rm.setRes(r);
		return JSON.toJSONString(rm);
	}

	public static String getErrorRes(String errMessage, String code) {
		if (code != null && !"".equalsIgnoreCase(code)) {
			if (errMessage == null || "".equals(errMessage.trim())) {
				errMessage = "server err.";
			}
			Res r = new Res();
			r.setReCode(code);
			r.setResMessage(errMessage);
			ResponseMain rm = new ResponseMain();
			rm.setRes(r);
			return JSON.toJSONString(rm);
		} else {
			return getErrorRes(errMessage);
		}
	}

}
