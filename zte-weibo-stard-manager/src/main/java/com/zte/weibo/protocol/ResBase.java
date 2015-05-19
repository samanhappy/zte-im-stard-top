package com.zte.weibo.protocol;

import com.alibaba.fastjson.JSON;
import com.zte.im.util.Constant;

public abstract class ResBase {
	
	public static String getSuccessRes() {
		return JSON.toJSONString(new ResponseMain());
	}

	public static String getErrorRes(String errMessage) {
		if (errMessage == null || "".equals(errMessage.trim())) {
			errMessage = "server err.";
		}
		Res r = new Res();
		r.setReCode(Constant.ERROR_CODE);
		r.setResMessage(errMessage);
		ResponseMain rm = new ResponseMain();
		rm.setResult(false);
		rm.setMsg(errMessage);
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
			rm.setResult(false);
			rm.setMsg(errMessage);
			rm.setRes(r);
			return JSON.toJSONString(rm);
		} else {
			return getErrorRes(errMessage);
		}
	}

}
