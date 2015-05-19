package com.zte.im.service;

import com.zte.databinder.SysLogBinder;
import com.zte.im.bean.SysLog;
import com.zte.im.mybatis.bean.page.SysLogPageModel;

public interface ISysLogService {

	public SysLogPageModel queryLog(SysLogBinder binder);

	public void saveLog(SysLog log);

}
