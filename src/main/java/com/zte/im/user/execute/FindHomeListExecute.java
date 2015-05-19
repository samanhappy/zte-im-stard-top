package com.zte.im.user.execute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.Firm;
import com.zte.im.protocol.ResponseListMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IFirmService;
import com.zte.im.service.impl.FirmServiceImpl;
import com.zte.im.util.Page;

@Deprecated
public class FindHomeListExecute extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(FindHomeListExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		int cpage = json.getIntValue("page");
		Long gid = json.getLongValue("gid");
		Page page = new Page(3, cpage);
		IFirmService service = FirmServiceImpl.getInstance();
		// Firm firm = service.getComposition(gid, page, cpage);
		ResponseListMain main = new ResponseListMain();
		// main.setFirm(firm);
		resJson = JSON.toJSONString(main);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("page", 3);
		json.put("gid", 1);
		json.put("token", "8776786_EQ0PMDCN");

		AbstractValidatoExecute e = new FindHomeListExecute();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("findhome");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
