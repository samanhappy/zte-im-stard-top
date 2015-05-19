package com.zte.im.software.execute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.Software;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.ISoftwareService;
import com.zte.im.service.impl.SoftwareServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.SystemConfig;

/**
 * 
 * @ClassName: SoftwareUpdateExecute
 * @Description: 软件更新接口.
 * @author wangpk
 * @date 2014-6-25 下午5:13:25
 * 
 */
public class SoftwareUpdateExecute extends AbstractValidatoExecute {
	
	private static Logger logger = LoggerFactory.getLogger(SoftwareUpdateExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		Software sSoft = null;
		String resJson = null;
		Software cSoft = null;
		try {
			cSoft = GsonUtil.gson.fromJson(json.toString(), Software.class);
			ISoftwareService service = SoftwareServiceImpl.getInstance();
			if (cSoft.getClient_type() == null || cSoft.getVersion() == null) {
				return getErrorRes("client_type和version不能为空");
			}
			sSoft = service.findSoftwareUpdate(cSoft.getClient_type(),
					cSoft.getVersion());
			if (sSoft == null)
				sSoft = service.findSoftwareUpdate(null, cSoft.getVersion());
		} catch (Exception e) {
			logger.error("", e);
			return getErrorRes("server err.");
		}
		ResponseMain reMain = new ResponseMain();
		reMain.setHost(SystemConfig.fdfs_http_host);
		if (sSoft != null) {
			sSoft.setUpdate_url(sSoft.getUpdate_url().replace(reMain.getHost(),
					""));
			Res res = new Res();
			res.setReCode(Constant.SUCCESS_CODE);
			res.setResMessage("当前版本已经是最新版本,无需更新.");
			if (sSoft.getVersion().compareTo(cSoft.getVersion()) > 0) {
				sSoft.setClient_type(null);
				res.setResMessage("更新成功.");
				reMain.setSoftware(sSoft);
			}
			reMain.setRes(res);
		} else {
			Res res = new Res();
			res.setReCode(Constant.SUCCESS_CODE);
			res.setResMessage("当前版本已经是最新版本,无需更新.");
			reMain.setRes(res);
		}
		resJson = GsonUtil.gson.toJson(reMain);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("client_type", "android");
		json.put("version", "1");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new SoftwareUpdateExecute();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("softwareUpdate");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
