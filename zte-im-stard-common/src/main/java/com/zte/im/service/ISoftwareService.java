package com.zte.im.service;

import com.zte.im.bean.Software;

/**
 * 
 * @ClassName: ISoftwareService
 * @Description: 软件表业务接口.
 * @author wangpk
 * @date 2014-6-27 上午11:56:04
 * 
 */
public interface ISoftwareService {
	/**
	 * 
	 * @Title: findSoftwareUpdate
	 * @Description: 根据version client_type获取最新软件包信息.
	 * @param client_type
	 * @param version
	 * @return
	 * @return Software
	 */
	Software findSoftwareUpdate(String client_type);
}
