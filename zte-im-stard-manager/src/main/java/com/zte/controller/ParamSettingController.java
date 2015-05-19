package com.zte.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zte.im.bean.Param;
import com.zte.im.protocol.ResBase;
import com.zte.im.protocol.ResponseMain;
import com.zte.im.service.IParamSettingService;
import com.zte.im.service.impl.ParamSettingServiceImpl;
import com.zte.util.system.PinYinUtil;

@Controller
public class ParamSettingController extends ResBase {

	IParamSettingService service = ParamSettingServiceImpl.getInstance();

	@RequestMapping(value = "updateSysParam")
	@ResponseBody
	public String updateSysParam(Param param) {
		param.setId("system");
		service.saveOrUpdate(param);
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}
	
	@RequestMapping(value = "updateParam")
	@ResponseBody
	public String updateParam(Param param) {
		service.saveOrUpdate(param);
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}

	@RequestMapping(value = "getSysParam")
	@ResponseBody
	public String getSysParam() {
		Param param = service.getParamByType("system");
		ResponseMain main = new ResponseMain();
		main.setData(param);
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}
	
	@RequestMapping(value = "getContactParam")
	@ResponseBody
	public String getContactParam() {
		Param param = service.getParamByType("contact");
		ResponseMain main = new ResponseMain();
		main.setData(param);
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}

	@RequestMapping(value = "loadParam")
	@ResponseBody
	public String loadParam() {
		ResponseMain main = new ResponseMain();
		main.setItem(service.loadParam(false));
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}
	
	@RequestMapping(value = "loadUserDefineParam")
	@ResponseBody
	public String loadUserDefineParam() {
		ResponseMain main = new ResponseMain();
		main.setItem(service.loadParam(true));
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}

	@RequestMapping(value = "updateContactParam")
	@ResponseBody
	public String updateContactParam(Param param) {
		service.saveOrUpdate(param);
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}

	@RequestMapping(value = "saveOrUpdateParam")
	@ResponseBody
	public String saveOrUpdateParam(Param param) {
		if (param.getParamName() == null || "".equals(param.getParamName())) {
			return getErrorRes("字段名称不能为空");
		}
		if (param.getParamType() == null || "".equals(param.getParamType())) {
			return getErrorRes("字段类型不能为空");
		}
		if (param.getId() != null) {
			service.delete(param.getId());
		}
		param.setId(PinYinUtil.getPinyinStr(param.getParamName()));
		service.saveOrUpdate(param);
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}

	@RequestMapping(value = "removeParam")
	@ResponseBody
	public String removeParam(Param param) {
		if (param == null || param.getId() == null || "".equals(param.getId())) {
			return getErrorRes("字段id不能为空");
		}
		service.delete(param.getId());
		ResponseMain main = new ResponseMain();
		String resultJson = JSON.toJSONString(main);
		return resultJson;
	}

}
