package com.zte.im.service;

import com.zte.im.bean.Customization;

public interface ICustomizationService {
	
	public void saveOrUpdate(Customization obj);
	
	public Customization getCustomization(String id);

}
