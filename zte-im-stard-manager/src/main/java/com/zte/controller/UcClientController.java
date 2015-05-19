package com.zte.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.databinder.ClientBinder;
import com.zte.im.mybatis.bean.my.UcClientMy;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.IClientService;
import com.zte.im.service.impl.ClientServiceImpl;

@Controller
public class UcClientController extends ResBase {

	private static Logger logger = LoggerFactory
			.getLogger(UcClientController.class);

	@RequestMapping(value = "clientList")
	@ResponseBody
	public String clientList() {
		IClientService service = ClientServiceImpl.getInstance();
		List<UcClientMy> list = service.getListByPage();
		ResponseMain main = new ResponseMain();
		main.setItem(list);
		String resultJson = JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd");
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "addClient", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String addClient(@ModelAttribute("model") ClientBinder model) {
		UcClientMy my = binder2Bean(model);
		IClientService service = ClientServiceImpl.getInstance();
		String code = "1";
		try {
			service.addClient(my);
		} catch (Exception e) {
			logger.error("", e);
			code = "2";
		}
		ResponseMain main = new ResponseMain();
		main.getRes().setReCode(code);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	private UcClientMy binder2Bean(ClientBinder model) {
		UcClientMy my = new UcClientMy();
		my.setId(model.getCid());
		my.setType(model.getCtype());
		if (model.getCtype().equals("Android应用")) {
			my.setApkUrl(model.getCapkUrl());
			my.setVersionCode(model.getVersionCode());
		} else {
			my.setApkUrl(model.getPlist());
			my.setVersionCode(model.getVersion());
		}
		my.setIconUrl(model.getCiconUrl());
		my.setName(model.getCname());
		my.setOs(model.getCos());
		my.setIsActive(model.getCisActive());
		if (model.getCisActive() == null)
			my.setIsActive("off");
		my.setUpdateLog(model.getCupdateLog());
		my.setVersion(model.getVersion());
		my.setApkName(model.getCapk());
		my.setForceUpdate(model.getForceUpdate());
		return my;
	}

	@RequestMapping(value = "toeditClient")
	@ResponseBody
	public String toeditClient(@RequestParam("cid") String cid) {
		IClientService service = ClientServiceImpl.getInstance();
		UcClientMy my = service.selectClientById(cid);
		JSONObject resultJson = new JSONObject();
		resultJson.put("cid", my.getId());
		resultJson.put("ctype", my.getType());
		resultJson.put("capkUrl", my.getApkUrl());
		resultJson.put("cname", my.getName());
		resultJson.put("ciconUrl", my.getIconUrl());
		resultJson.put("cos", my.getOs());
		if (my.getIsActive() == null)
			my.setIsActive("off");
		resultJson.put("cisActive", my.getIsActive());
		resultJson.put("cupdateLog", my.getUpdateLog());
		resultJson.put("version", my.getVersion());
		resultJson.put("versionCode", my.getVersionCode());
		resultJson.put("capk", my.getApkName());
		resultJson.put("forceUpdate", my.getForceUpdate());
		resultJson.put("result", true);
		return resultJson.toJSONString();
	}

	@RequestMapping(value = "editClient")
	@ResponseBody
	public String editClient(@ModelAttribute("model") ClientBinder model) {
		UcClientMy my = binder2Bean(model);
		IClientService service = ClientServiceImpl.getInstance();
		String code = "1";

		if (my.getOs().equals("android") && !my.getApkUrl().contains("?attname=")) {
			my.setApkUrl(my.getApkUrl() + "?attname=" + my.getName()
					+ (my.getOs().equals("android") ? ".apk" : ".ipa"));
		}

		try {
			service.modifyClient(my);
		} catch (Exception e) {
			logger.error("", e);
			code = "2";
		}
		ResponseMain main = new ResponseMain();
		main.getRes().setReCode(code);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "delClient")
	@ResponseBody
	public String delClient(@RequestParam("id") String id) {
		IClientService service = ClientServiceImpl.getInstance();
		String code = "1";
		try {
			service.deleteClient(id);
		} catch (Exception e) {
			logger.error("", e);
			code = "2";
		}
		ResponseMain main = new ResponseMain();
		main.getRes().setReCode(code);
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "setClientActive")
	@ResponseBody
	public String setClientActive(@RequestParam("id") String id,
			@RequestParam("isActive") String isActive) {
		if (id == null || "".equals(id) || isActive == null
				|| "".equals(isActive)) {
			return getErrorRes("id和isActive参数不能为空");
		}
		IClientService service = ClientServiceImpl.getInstance();
		service.setActive(id, isActive);
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}

	@RequestMapping(value = "removeClient")
	@ResponseBody
	public String removeClient(@ModelAttribute("model") ClientBinder model) {
		ResponseMain main = new ResponseMain();
		String ids = model.getCid();
		if (ids != null && !"".equals(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			IClientService service = ClientServiceImpl.getInstance();
			if (!service.removeClient(Arrays.asList(ids.split(",")))) {
				return getErrorRes("操作失败");
			}
		}
		String resultJson = JSON.toJSONString(main);
		logger.info(resultJson);
		return resultJson;
	}
}
