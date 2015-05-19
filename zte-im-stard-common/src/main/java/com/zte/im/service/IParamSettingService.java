package com.zte.im.service;

import java.util.List;

import com.zte.im.bean.Param;

public interface IParamSettingService {

	public void saveOrUpdate(Param param);

	public Param getParamByType(String type);

	public List<Param> loadParam(boolean userDefindOnly);

	public void delete(String id);
}
