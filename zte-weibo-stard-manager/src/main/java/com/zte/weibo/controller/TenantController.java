package com.zte.weibo.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zte.im.service.IAccountService;
import com.zte.im.service.ICustomizationService;
import com.zte.im.service.impl.AccountServiceImpl;
import com.zte.im.service.impl.CustomizationServiceImpl;
import com.zte.im.util.Constant;
import com.zte.weibo.protocol.ResBase;
import com.zte.weibo.protocol.ResponseMain;

@Controller
@RequestMapping(value="testAction")
public class TenantController extends ResBase {

	public static final Logger logger = LoggerFactory.getLogger(TenantController.class);

	ICustomizationService cusService = CustomizationServiceImpl.getInstance();

	IAccountService accService = AccountServiceImpl.getInstance();


	@RequestMapping(value = "getTenantCustomization")
	@ResponseBody
	public String getTenantCustomization(HttpServletRequest request) {
		// 默认c1支持单企业版本
		String tenantId = "c1";
		Object obj = request.getSession().getAttribute(Constant.TENANT_ID);
		if (obj != null) {
			tenantId = (String) obj;
		}
		ResponseMain main = new ResponseMain();
		main.setData(cusService.getCustomization(tenantId));
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}

}
