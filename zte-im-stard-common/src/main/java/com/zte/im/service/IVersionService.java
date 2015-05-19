package com.zte.im.service;

import com.alibaba.fastjson.JSONObject;
import com.zte.im.bean.DataVer;
import com.zte.im.bean.User;

public interface IVersionService {

	public DataVer getDataVer();

	public void incAppVer();

	public void incAppCateVer();

	public void incDeptVer();

	public void incUserListVer();
	
	public void incWorkImgListVer();

	public boolean isDataUpdated(String method, JSONObject request, User user);

}
