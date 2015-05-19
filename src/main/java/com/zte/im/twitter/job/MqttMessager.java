package com.zte.im.twitter.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.im.bean.PushBean;
import com.zte.im.util.CmdExecute;

public class MqttMessager extends Thread {
	
	private final static Logger logger = LoggerFactory
			.getLogger(MqttMessager.class);

	List<PushBean> msgList;

	public MqttMessager(List<PushBean> msgList) {
		this.msgList = msgList;
	}

	@Override
	public void run() {
		if (msgList != null && msgList.size() > 0) {
			for (PushBean bean : msgList) {
				String response = CmdExecute.exe(bean);
				logger.info(response);
			}
		}
	}
}
