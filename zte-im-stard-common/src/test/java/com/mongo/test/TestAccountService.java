package com.mongo.test;

import com.zte.im.service.impl.AccountServiceImpl;

public class TestAccountService {
	
	public static void main(String[] args) {
		System.out.println(AccountServiceImpl.getInstance().list());
	}

}
