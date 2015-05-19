package com.zte.im.software.execute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.Software;
import com.zte.im.bean.User;
import com.zte.im.protocol.Res;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.ISoftwareService;
import com.zte.im.service.impl.SoftwareServiceImpl;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.SystemConfig;

/**
 * 
 * @ClassName: SoftwareUpdateExecute
 * @Description: 
 * 客户端更新接口，根据操作系统和版本号判断，如果需要更新则返回更新地址，针对android返回apk下载地址，ios返回plist地址
 * @author wangpk
 * @date 2014-6-25 下午5:13:25
 * 
 */
public class SoftwareUpdateExecute extends AbstractValidatoExecute {

	private static Logger logger = LoggerFactory.getLogger(SoftwareUpdateExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		Software serverSoft = null;
		String resJson = null;
		Software clientSoft = null;
		try {
			clientSoft = GsonUtil.gson.fromJson(json.toString(), Software.class);
			ISoftwareService service = SoftwareServiceImpl.getInstance();
			if (clientSoft.getClient_type() == null || clientSoft.getVersion() == null) {
				return getErrorRes("client_type和version不能为空");
			}
			serverSoft = service.findSoftwareUpdate(clientSoft.getClient_type());
			if (serverSoft == null)
				serverSoft = service.findSoftwareUpdate(null);
		} catch (Exception e) {
			logger.error("", e);
			return getErrorRes("server err.");
		}
		ResponseMain reMain = new ResponseMain();
		reMain.setHost(SystemConfig.fdfs_http_host);
		if (serverSoft != null) {
			serverSoft.setUpdate_url(serverSoft.getUpdate_url().replace(reMain.getHost(), ""));
			Res res = new Res();
			res.setReCode(Constant.SUCCESS_CODE);
			res.setResMessage("当前版本已经是最新版本,无需更新.");
			if (serverSoft.getVersion() != null && clientSoft.getVersion() != null
					&& serverSoft.getVersion() > clientSoft.getVersion()) {
				serverSoft.setClient_type(null);
				res.setResMessage("更新成功.");
				reMain.setSoftware(serverSoft);
				// 记录用户日志
				try {
					User user = super.getContext().getUser();
					StringBuilder oper = new StringBuilder();
					oper.append("用户");
					oper.append(user.getName());
					oper.append("版本更新成功");
					TwitterServiceImpl.getInstance().saveUserLog(user, "用户更新客户端", oper.toString());
				} catch (Exception e) {
				}
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
