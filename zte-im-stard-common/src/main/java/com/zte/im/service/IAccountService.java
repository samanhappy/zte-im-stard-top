package com.zte.im.service;

import java.util.List;

import com.zte.im.mybatis.bean.Account;

public interface IAccountService {

	public boolean add(Account account);

	public boolean remove(String name);

	public boolean update(Account account);

	public List<Account> list();

	public Account get(String name);

}
