package com.zte.im.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.eclipse.paho.sample.mqttv3app.MqttPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.im.bean.Composition;
import com.zte.im.service.impl.CompositionServiceImpl;
import com.zte.im.util.InitHelper;
import com.zte.im.util.PropertiesInMemory;

@WebListener
public class Start implements ServletContextListener {
	private final Logger logger = LoggerFactory.getLogger(Start.class);

	public static final Map<String, String> executes = new HashMap<String, String>();

	public static final Map<String, List<String>> params = new HashMap<String, List<String>>();

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		logger.info("start load Properties...");
		PropertiesInMemory proper = new PropertiesInMemory();
		proper.inMemory("executeMapper.properties", executes);
		proper.inMemoryList("paramsMapper.properties", params);
		logger.info("Memory Size:" + executes.size());
		logger.info("load Properties end");

		logger.info("MqttPublisher init start...");
		//new MqttPublisher().init("60");
		logger.info("MqttPublisher init end");

		// 初始化部门节点类型
		logger.info("initNodeType4Composition start...");
		initNodeType4Composition();
		logger.info("initNodeType4Composition end");
		
		// 初始化IDSequence，uid从100开始
		InitHelper.initIDSequence();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("imService shutdown...");
	}

	/**
	 * 初始化部门节点类型
	 */
	public static void initNodeType4Composition() {
		ICompositionService comService = CompositionServiceImpl.getInstance();
		List<Composition> comList = comService.getComposition(1L);
		if (comList != null && comList.size() > 0) {
			for (Composition com : comList) {
				if (comService.hasSubComposition(com.getCid(), -1L)) {
					com.setNodeType(0);
				} else {
					com.setNodeType(1);
				}
				comService.updateByCId(com);
			}
		}
	}

}
