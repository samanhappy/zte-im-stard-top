package com.zte.service;

import java.util.List;

import com.zte.databinder.ChatRecordBinder;
import com.zte.im.bean.ChatRecord;
import com.zte.im.mybatis.bean.page.ChatRecordPageModel;

public interface IChatRecordService {
	
	public List<ChatRecord> listByAggregate();

	public ChatRecordPageModel list(ChatRecordBinder binder);
	
	public List<ChatRecord> listByIds(String ids);

}
