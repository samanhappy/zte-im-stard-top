package com.zte.im.user.execute;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.Composition;
import com.zte.im.bean.Firm;
import com.zte.im.bean.User;
import com.zte.im.cache.redis.RedisSupport;
import com.zte.im.protocol.ResponseListMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IFirmService;
import com.zte.im.service.impl.FirmServiceImpl;
import com.zte.im.service.impl.VersionServiceImpl;

/**
 * 根据企业id查询企业架构
 * 
 * @author Administrator
 * 
 */
public class EnterpriseContactExecute extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(EnterpriseContactExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		User user = super.getContext().getUser();
		String resJson = null;
		if (user != null) {
			Long gid = user.getGid();
			IFirmService firmService = FirmServiceImpl.getInstance();
			Firm firm = firmService.getFirm(gid);
			List<Composition> list = firmService.getComposition(gid);
			firm.setComposition(list);
			ResponseListMain listmain = new ResponseListMain();
			listmain.setFirm(firm);
			listmain.setDataVer(VersionServiceImpl.getInstance().getDataVer());
			resJson = JSON.toJSONString(listmain);
		} else {
			resJson = getErrorRes("token not find by user.");
		}
		return resJson;
	}

	public static void main(String[] args) {
		AbstractValidatoExecute e = new EnterpriseContactExecute();
		JSONObject json = new JSONObject();
		json.put("token", "2355660_D3TAYJ7E");
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		logger.info(json.toJSONString());
		context.setMethod("enterpriseContact");
		String tokenUser = RedisSupport.getInstance().get("2355660_D3TAYJ7E");
		User user = JSON.parseObject(tokenUser, User.class);
		context.setUser(user);
		e.setContext(context);
		String result = e.doProcess(json);
		logger.info(result);

	}

}
