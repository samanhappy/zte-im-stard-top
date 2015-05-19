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

/**
 * 日程时间段查询
 * 
 * @author cy
 * 
 */
public class FindProgrameMoreDayList extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(FindProgrameMoreDayList.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String startDate = json.getString("startDate");
		String endDate = json.getString("endDate");
		Long uid = json.getLong("uid");
		IProgrameService service = ProgrameServiceImpl.getInstance();
		List<Map<String, List<Programe>>> list = service.findMoreDayList(
				startDate, endDate, uid);
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
		json.put("startDate", "20141024");
		json.put("endDate", "20141105");
		json.put("uid", 1l);
		json.put("token", "4602658_75JCEGDQ");

		FindProgrameMoreDayList e = new FindProgrameMoreDayList();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("programe/listPrograme");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
