package com.zte.weibo.common.constant;

import java.util.HashMap;
import java.util.Map;

public class UserConstant {

	public static final String STATE_ON = "00";
	public static final String STATE_OFF = "99";
	
	/**
	 * 启用和停用状态编码对照(1:启用；2：停用)
	 */
	public static Map<String, String> stateMap = new HashMap<String, String>();
	static{
		stateMap.put(STATE_ON, "启用");
		stateMap.put(STATE_OFF, "停用");
		
	}
}
