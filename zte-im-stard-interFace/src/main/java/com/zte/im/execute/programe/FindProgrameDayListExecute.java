package com.zte.im.execute.programe;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.Programe;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.Context;
import com.zte.im.service.IProgrameService;
import com.zte.im.service.impl.ProgrameServiceImpl;

public class FindProgrameDayListExecute extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(FindProgrameDayListExecute.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		Long id = json.getLongValue("id");
		Programe programe = new Programe();
		programe.setId(id);
		IProgrameService service = ProgrameServiceImpl.getInstance();
		List<Programe> list = service.findDayList(programe);

		resJson = JSON.toJSONString(list);
		return resJson;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("id", 1455);
		json.put("gid", 1);
		json.put("token", "4602658_75JCEGDQ");

		FindProgrameDayListExecute e = new FindProgrameDayListExecute();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("programe/listPrograme");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
