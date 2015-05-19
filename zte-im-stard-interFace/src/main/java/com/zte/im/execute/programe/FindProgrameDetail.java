package com.zte.im.execute.programe;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.Programe;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IProgrameService;
import com.zte.im.service.impl.ProgrameServiceImpl;

/**
 * 获取日程详情
 * 
 * @author cy
 * 
 */
public class FindProgrameDetail extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(FindProgrameDetail.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Long id = json.getLongValue("id");
		Programe programe = new Programe();
		programe.setId(id);
		IProgrameService service = ProgrameServiceImpl.getInstance();
		Programe rPrograme = service.findProgrameDetail(programe);
		List<Programe> list = service.findProgrameAppointList(programe);
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> pMap = new HashMap<String, Object>();
		res.put("resFlag", "true");
		res.put("msg", "");
		pMap.put("rPrograme", rPrograme);
		pMap.put("appointList", list);
		res.put("programe", pMap);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) throws ParseException {
		JSONObject json = new JSONObject();
		json.put("id", 1543);
		json.put("token", "4602658_75JCEGDQ");

		logger.info(json.toJSONString());
		AbstractValidatoExecute e = new FindProgrameDetail();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("programe/listProgrameDetail");
		String result = e.doProcess(context);
		logger.info(result);

	}
}
