package com.zte.im.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.zte.im.bean.PushBean;

public class CmdExecute {
	
	private static Logger logger = LoggerFactory.getLogger(CmdExecute.class);

	private static String FILE_BASE_PATH = "/home/zte/pushfile/";

	public static String getCmd_mosquitto_pub(PushBean bean) {
		StringBuffer cmd = new StringBuffer();

		cmd.append(SystemConfig.mqtt_server);
		cmd.append(writeFile(getGsonStringWithLength(JSON.toJSONString(bean))));
		cmd.append(" -t ");
		if (bean.getTarget() != null) {
			cmd.append(bean.getTarget());
		} else {
			cmd.append("g0");
		}
		cmd.append(SystemConfig.mqtt_params);

		logger.info(cmd.toString());
		return cmd.toString();
	}

	public static String exe(PushBean bean) {
		try {
			Process ps = Runtime.getRuntime().exec(getCmd_mosquitto_pub(bean));
			logger.info(loadStream(ps.getInputStream()));
			return loadStream(ps.getErrorStream());
		} catch (IOException ioe) {
			logger.error("", ioe);
		}
		return null;
	}

	static String loadStream(InputStream in) throws IOException {
		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();
		while ((ptr = in.read()) != -1) {
			buffer.append((char) ptr);
		}
		return buffer.toString();
	}

	static String writeFile(byte[] data) {
		String filePath = FILE_BASE_PATH + System.currentTimeMillis();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(filePath));
			fos.write(data);
			fos.close();
		} catch (IOException e) {
			logger.error("", e);
		}
		return filePath;
	}

	public static byte[] getGsonStringWithLength(String strJson) {
		byte[] jsonBytes = strJson.getBytes();
		String jsonlen = String.valueOf(jsonBytes.length);
		String head = jsonlen + "#";
		byte[] data = new byte[head.length() + jsonBytes.length];
		byte[] headbyte = head.getBytes();
		System.arraycopy(headbyte, 0, data, 0, headbyte.length);
		System.arraycopy(jsonBytes, 0, data, headbyte.length, jsonBytes.length);
		return data;
	}

}
