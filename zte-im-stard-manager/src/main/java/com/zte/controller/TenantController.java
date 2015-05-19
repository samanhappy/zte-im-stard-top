package com.zte.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.databinder.TenantBinder;
import com.zte.im.bean.Customization;
import com.zte.im.mybatis.bean.Account;
import com.zte.im.mybatis.bean.UcTenant;
import com.zte.im.mybatis.bean.my.UcTenantMy;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.IAccountService;
import com.zte.im.service.ICustomizationService;
import com.zte.im.service.impl.AccountServiceImpl;
import com.zte.im.service.impl.CustomizationServiceImpl;
import com.zte.im.service.impl.TenantServcieImpl;
import com.zte.im.util.CheckApp;
import com.zte.im.util.Constant;
import com.zte.im.util.SystemConfig;
import com.zte.service.ITenantService;

@Controller
public class TenantController extends ResBase {

	public static final Logger logger = LoggerFactory.getLogger(TenantController.class);

	@Autowired
	private ITenantService tService;

	ICustomizationService cusService = CustomizationServiceImpl.getInstance();

	IAccountService accService = AccountServiceImpl.getInstance();

	@RequestMapping(value = "toEditTenant")
	@ResponseBody
	public String toEditTenant(HttpServletRequest request) {

		UcTenantMy tenant = tService.selectTenant((String) request.getSession().getAttribute(Constant.TENANT_ID));
		UcTenantMy t2 = TenantServcieImpl.getInstance().findTenantSelect(tenant.getId());
		JSONObject resultJson = new JSONObject();
		if (tenant != null) {
			resultJson.put("tid", tenant.getId());
			resultJson.put("tname", tenant.getName());
			resultJson.put("tpid", tenant.getPlatformId());
			resultJson.put("tlinkman", tenant.getLinkman());
			resultJson.put("tmobile", tenant.getMobile());
			resultJson.put("tmail", tenant.getMail());
			resultJson.put("ttel", tenant.getTel());
			resultJson.put("tusercount", tenant.getUserCount());
			resultJson.put("taddress", tenant.getAddress());
			resultJson.put("tscale", TenantServcieImpl.getInstance().getScaleJSON());
			if (t2 != null) {
				resultJson.put("tguimo", t2.getGuimo());
				resultJson.put("tprov", t2.getProv());
				resultJson.put("tcity", t2.getCity());
				resultJson.put("photoImg", t2.getPhotoImg());
			}
			resultJson.put("tprovs", TenantServcieImpl.getInstance().getProvsJSON());

		}
		return resultJson.toJSONString();
	}

	@RequestMapping(value = "selectTenant")
	@ResponseBody
	public String selectTenant(HttpServletRequest request) {
		UcTenantMy tenant = tService.selectTenant((String) request.getSession().getAttribute(Constant.TENANT_ID));
		UcTenantMy tenantFromMongo = TenantServcieImpl.getInstance().findTenantSelect(tenant.getId());
		if (tenantFromMongo != null) {
			tenant.setGuimo(tenantFromMongo.getGuimo());
			tenant.setProv(tenantFromMongo.getProv());
			tenant.setCity(tenantFromMongo.getCity());
			tenant.setPhotoImg(tenantFromMongo.getPhotoImg());
		}
		ResponseMain main = new ResponseMain();
		main.setData(tenant);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "editTenant")
	@ResponseBody
	public String editTenant(@ModelAttribute("model") TenantBinder model) {
		try {
			UcTenant tenant = binder2Tenant(model);
			UcTenantMy my = binder2TenantMy(model);
			tService.updateTenant(tenant);
			TenantServcieImpl.getInstance().modifySelect(my);
		} catch (Exception e) {
			logger.error("", e);
		}
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "updateTenant")
	@ResponseBody
	public String updateTenant(@ModelAttribute("model") UcTenantMy tenant) {
		try {
			tService.updateTenant(tenant);
			TenantServcieImpl.getInstance().modifySelect(tenant);
		} catch (Exception e) {
			logger.error("", e);
		}
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "updateTenantCustomization")
	@ResponseBody
	public String updateTenantCustomization(Customization obj, HttpServletRequest request) {
		obj.setId((String) request.getSession().getAttribute(Constant.TENANT_ID));
		cusService.saveOrUpdate(obj);
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}

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

	@RequestMapping(value = "cityList")
	@ResponseBody
	public String cityList(@RequestParam("prov") String prov) {
		JSONObject resultJson = new JSONObject();
		try {
			resultJson.put("tcitys", TenantServcieImpl.getInstance().getCityJSON(prov));
		} catch (Exception e) {
			logger.error("", e);
		}
		return resultJson.toJSONString();
	}

	private UcTenant binder2Tenant(TenantBinder model) {
		UcTenant tenant = new UcTenant();
		tenant.setId(model.getTid());
		tenant.setName(model.getTname());
		tenant.setPlatformId(model.getTpid());
		tenant.setLinkman(model.getTlinkman());
		tenant.setMobile(model.getTmobile());
		tenant.setMail(model.getTmail());
		tenant.setTel(model.getTtel());
		tenant.setAddress(model.getTaddress());
		return tenant;
	}

	private UcTenantMy binder2TenantMy(TenantBinder model) {
		UcTenantMy my = new UcTenantMy();
		my.setId(model.getTid());
		my.setGuimo(model.getTguimo());
		my.setProv(model.getTprov());
		my.setCity(model.getTcity());
		my.setPhotoImg(model.getPhotoImg());
		return my;
	}

	@RequestMapping(value = "editPass")
	@ResponseBody
	public String editPass(@ModelAttribute("model") TenantBinder model,
			HttpServletRequest request) {
		
		if (model.getOpass() == null || model.getPass() == null || model.getRpass() == null) {
			return getErrorRes("输入密码不能为空");
		}
		if (!model.getPass().equals(model.getRpass())) {
			return getErrorRes("两次密码输入不一致");
		}
		
		Account acc = accService.get((String) request.getSession().getAttribute(Constant.USERID));
		if (acc == null) {
			return getErrorRes("用户不存在");
		}
		if (!model.getOpass().equals(acc.getPwd())) {
			return getErrorRes("原始密码不正确");
		}
		
		acc.setPwd(model.getPass());
		accService.update(acc);
		return getSuccessRes();
	}

	@RequestMapping(value = "checkLisence")
	@ResponseBody
	public String checkLisence() {
		String daysStr = CheckApp.checkStopTime();
		int remainDays = 0;
		String startDate = SystemConfig.licenseStartDate;
		String endDate = "";
		try {
			remainDays = Integer.valueOf(daysStr);
			Calendar today = Calendar.getInstance();
			today.add(Calendar.DATE, remainDays);
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(today.getTime());
		} catch (Exception e) {
			logger.error("", e);
		}

		JSONObject resultJson = new JSONObject();
		resultJson.put("startDate", startDate);
		resultJson.put("endDate", endDate);
		resultJson.put("remainDays", remainDays);
		return resultJson.toJSONString();
	}
}
