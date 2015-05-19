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
 * 日程一天列表查询
 * 
 * @author cy
 * 
 */
public class FindProgrameDayPageList extends AbstractValidatoExecute {
	
	private final static Logger logger = LoggerFactory
			.getLogger(FindProgrameDayPageList.class);

	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		String date = json.getString("date");
		int type = json.getIntValue("type");
		Long uid = json.getLong("uid");
		IProgrameService service = ProgrameServiceImpl.getInstance();
		List<Map<String, List<Programe>>> list = service.findDayPageList(date,
				type, uid);
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
		json.put("date", "20141105");
		json.put("uid", 1l);
		json.put("type", 1l);
		json.put("token", "4602658_75JCEGDQ");

		FindProgrameDayPageList e = new FindProgrameDayPageList();
		Context context = new Context();
		context.setJsonRequest(json.toJSONString());
		context.setMethod("programe/listPrograme");
		String result = e.doProcess(context);
		logger.info(result);
	}
}
