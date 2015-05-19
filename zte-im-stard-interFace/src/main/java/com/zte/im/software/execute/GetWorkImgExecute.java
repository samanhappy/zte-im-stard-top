package com.zte.im.software.execute;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBObject;
import com.zte.im.protocol.ResponseListMain;
import com.zte.im.service.AbstractValidatoExecute;
import com.zte.im.service.IWorkImgService;
import com.zte.im.service.impl.VersionServiceImpl;
import com.zte.im.service.impl.WorkImgServiceImpl;

/**
 * 获取工作台展示图片，imgCateVer最新时不返回数据
 * @author samanhappy
 *
 */
public class GetWorkImgExecute extends AbstractValidatoExecute {
	
	@Override
	public String doProcess(JSONObject json) {
		String resJson = null;
		IWorkImgService service = WorkImgServiceImpl.getInstance();
		List<DBObject> obj = service.listWorkImg();
		ResponseListMain listmain = new ResponseListMain();
		listmain.setDataVer(VersionServiceImpl.getInstance().getDataVer());
		listmain.setItem(obj);
		resJson = JSON.toJSONString(listmain);
		return resJson;
	}
}
