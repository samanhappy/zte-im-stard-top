package com.zte.client;

import com.zte.databinder.ClientBinder;

/**
 * 
 * @ClassName: IClientHandle
 * @Description:
 * @author wangpk
 * @date 2014-11-4 下午3:01:02
 * 
 */
public interface IClientHandle {
	ClientBinder handleClientPackage(String path, String tmpPath,
			String apktoolPath);

}
