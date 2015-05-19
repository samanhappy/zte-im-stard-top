package com.zte.weibo.common.mdm;

import java.util.List;

import com.google.gson.annotations.Expose;

public class MDMResp {
	@Expose
	public String result;
	@Expose
	public String total_num;
	@Expose
	public List<MDMUser> data;

}
