package com.zte.im.execute.programe;

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

public class FindProgrameDayList extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(FindProgrameDayList.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String progDate = json.getString("date");
		Long uid = json.getLong("uid");
		Programe programe = new Programe();
		programe.setUserId(uid);
		programe.setProgStartDate(progDate);
		IProgrameService service = ProgrameServiceImpl.getInstance();
		List<Programe> list = service.findDayList(programe);
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> res1 = new HashMap<String, Object>();
		res1.put("reCode", "1");
		res1.put("resMessage", "");
		res.put("res", res1);
		res.put("list", list);
		resJson = JSON.toJSONString(res);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("date", "20141103");
		json.put("uid", 1l);
		json.put("token", "4602658_75JCEGDQ");

		FindProgrameDayList e = new FindProgrameDayList();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("programe/listPrograme");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
