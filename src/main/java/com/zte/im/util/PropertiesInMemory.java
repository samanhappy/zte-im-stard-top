package com.zte.im.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesInMemory {

	private final Logger logger = LoggerFactory
			.getLogger(PropertiesInMemory.class);

	public void inMemory(String path, Map<String, String> map) {
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream(path);
		Properties props = new Properties();
		try {
			props.load(input);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				if (map.containsKey(key)) {
					logger.error("find Repeat key in the start method!");
				}
				map.put(key, Property);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("input close err." + e.getMessage());
				}
			}
		}
	}

	public void inMemoryList(String path, Map<String, List<String>> map) {
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream(path);
		Properties props = new Properties();
		try {
			props.load(input);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				if (map.containsKey(key)) {
					logger.error("find Repeat key in the start method!");
				}
				map.put(key, Arrays.asList(Property.split(",")));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("input close err." + e.getMessage());
				}
			}
		}
	}
}
