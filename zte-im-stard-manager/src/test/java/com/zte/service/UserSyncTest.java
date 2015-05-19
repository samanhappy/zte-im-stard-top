package com.zte.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.zte.job.UserSyncJob;

public class UserSyncTest extends SpringTestBase {

	@Resource
	public UserSyncJob SpringQtzJob;

	@Test
	public void test() {
		SpringQtzJob.execute();
	}

}
