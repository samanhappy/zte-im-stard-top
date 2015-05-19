package com.zte.im.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		logger.info("load Properties end...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("imService shutdown...");
	}

}
