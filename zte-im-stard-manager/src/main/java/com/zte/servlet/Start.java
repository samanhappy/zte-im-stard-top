package com.zte.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.eclipse.paho.sample.mqttv3app.MqttPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.im.service.impl.GroupServiceImpl;
import com.zte.im.service.impl.TenantServcieImpl;
import com.zte.im.util.CheckApp;
import com.zte.im.util.InitHelper;

@WebListener
public class Start implements ServletContextListener {

	private static Logger logger = LoggerFactory.getLogger(Start.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		TenantServcieImpl.getInstance().initPass();
		TenantServcieImpl.getInstance().initScale();
		TenantServcieImpl.getInstance().initCity();

		// 初始化所有人群组
		GroupServiceImpl.getInstance().initAllGroup();

		logger.info("checkApp result is:{}", CheckApp.checkStopTime());

		//new MqttPublisher().init("30");
		logger.info("MqttPublisher init...");

		// 初始化IDSequence，uid从100开始
		//InitHelper.initIDSequence();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
