package com.zte.im.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class PathUtil {

	public static String path = "/usr/im4img";

	/**
	 * 
	 * @param type
	 *            0为原图，1位缩略图
	 * @return
	 */
	public static String getPath(int type) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		String now = df.format(new Date());
		StringBuffer path = new StringBuffer();
		if (isWindowsOS()) {
			path.append("c:\\");
			path.append("im4img");
		} else {
			path.append(path);
		}
		path.append(File.separator);
		path.append(now);
		path.append(File.separator);
		if (type == 1) {
			path.append("mini");
			path.append(File.separator);
		}
		File file = new File(path.toString());
		if (!file.exists()) {
			file.mkdirs();
		}
		return path.toString();
	}

	/**
	 * 
	 * TODO 判断操作系统类型
	 * 
	 * @return true(windows),false(其他的操作系统)
	 */
	private static boolean isWindowsOS() {
		Properties props = System.getProperties();
		String osName = props.getProperty("os.name");
		osName = osName.toLowerCase();
		if (osName.startsWith("windows")) {
			return true;
		} else {
			return false;
		}
	}
}
