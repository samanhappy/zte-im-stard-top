package com.zte.im.service;

import java.util.List;

import com.zte.im.mybatis.bean.my.UcClientMy;

public interface IClientService {
	List<UcClientMy> getListByPage();

	void addClient(UcClientMy my);

	void modifyClient(UcClientMy my);

	void deleteClient(String id);

	void setActive(String id, String isActive);

	public boolean removeClient(List<String> ids);

	UcClientMy selectClientById(String id);
}
