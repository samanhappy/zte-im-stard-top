package com.zte.im.service;

import java.util.List;

import com.zte.im.bean.User;

public interface IUserUpdateService {
	/**
	 * 根据用户uid更新用户信息记录.
	 * 
	 * @param uid         用户id
	 * @return            返回更新用户信息
	 */
    public List<User> getUpdateUsersbyid(Long... uid);
    
    /**
     * 根据用户uid查询新增用户信息.
     * @param uid         用户id
     * @return            返回新增用户信息
     */
    public List<User> getAddUsersbyid(Long... uid);
    
    /**
     * 查询删除的用户记录.
     * @param uid         用户id
     */
    public String getDeleUsersbyid(Long...uid);
	
}
