package com.zte.im.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class CheckApp {
	private static Key key;
	private static CheckApp checkApp;
	private static String PATH = "/usr/lib/apk2android/";
	private static String FILE = "/usr/lib/apk2android/file.txt";
	private static String KEY_FILE = "/usr/lib/apk2android/key.txt";
	public static final Logger logger = LoggerFactory.getLogger(CheckApp.class);

	public CheckApp getCheckApp() {
		if (checkApp == null) {
			checkApp = new CheckApp();
		}
		return checkApp;
	}

	public static long getDateNum(String startDate, String endDate) {
		SimpleDateFormat smdf = new SimpleDateFormat("yyyyMMdd");
		long t = 0L;
		try {
			Date start = smdf.parse(startDate);
			Date end = smdf.parse(endDate);
			t = (end.getTime() - start.getTime()) / 86400000L;
		} catch (ParseException e) {
			logger.error("", e);
		}
		return t;
	}

	public static String createStopTimeNumber(String strDate, long number)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		strDate = strDate
				+ sdf.format(Long.valueOf(new Date().getTime())).substring(8);
		Date date = sdf.parse(strDate);
		long time = date.getTime();
		int length = String.valueOf(time).length();
		long tmp = number;
		for (int i = String.valueOf(number).length(); i < length; i++) {
			number *= 10L;
		}
		return String.valueOf(time + number) + tmp
				+ String.valueOf(tmp).length();
	}

	public static String getStopTime(String key) throws Exception {
		int num = Integer.valueOf(key.substring(key.length() - 1)).intValue();
		key = key.substring(0, key.length() - 1);
		long count = Long.parseLong(key.substring(key.length() - num));
		long tmp = count;
		key = key.substring(0, key.length() - num);
		int length = key.length();
		for (int i = String.valueOf(count).length(); i < length; i++) {
			count *= 10L;
		}
		long time = Long.parseLong(key) - count;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date(time);
		return sdf.format(Long.valueOf(date.getTime())).substring(0, 8) + ","
				+ tmp;
	}

	public static String save(String id) throws Exception {
		if (!new java.io.File(PATH).exists()) {
			new java.io.File(PATH).mkdirs();
			new java.io.File(FILE).createNewFile();
		} else if (!new java.io.File(FILE).exists()) {
			new java.io.File(FILE).createNewFile();
		}

		String key = readKey();
		if ((key == null) || ("".equals(key))) {
			return "key不存在！";
		}
		if (key.split(",").length != 2) {
			return "key格式不正确！";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long num = getDateNum(sdf.format(new Date()), key.split(",")[0]);
		if (num < 0L) {
			return "软件已到期！";
		}
		String rs = readTxtFile(FILE);
		if (rs.split(",").length < Integer.parseInt(key.split(",")[1])) {
			if (rs.indexOf("-" + id + "-") < 0) {
				writeFile(FILE, "-" + id + "-,", true);
			}
		} else {
			return "使用用户已达到上限！";
		}
		return "1";
	}

	public static void writeFile(String fileName, String content, boolean flag) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(fileName, flag);
			writer.write(content);
		} catch (IOException e) {
			logger.error("", e);
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e1) {
				logger.error("", e1);
			}
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}

	public static void createKeyFile(String strDate, long number) {
		try {
			String key = createStopTimeNumber(strDate, number);
			getKey();
			String strEnc = getEncString(key);
			if (!new java.io.File(PATH).exists()) {
				new java.io.File(PATH).mkdirs();
				new java.io.File(KEY_FILE).createNewFile();
			} else if (!new java.io.File(KEY_FILE).exists()) {
				new java.io.File(KEY_FILE).createNewFile();
			}

			writeFile(KEY_FILE, strEnc, false);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public static String checkStopTime() {
		String key = readKey();
		if ((key == null) || ("".equals(key))) {
			return "key不存在！";
		}
		if (key.split(",").length != 2) {
			return "key格式不正确！";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long num = getDateNum(sdf.format(new Date()), key.split(",")[0]);
		if (num < 0L) {
			// return "软件已到期！";
		}
		return String.valueOf(num);
	}

	public static String readKey() {
		String key = readTxtFile(KEY_FILE);

		String rs = "";
		if ("".equals(key)) {
			return "";
		}
		getKey();
		key = getDesString(key);
		try {
			rs = getStopTime(key);
		} catch (Exception e) {
			logger.error("", e);
		}
		return rs;
	}

	public static String readTxtFile(String filePath) {
		String rs = "";
		try {
			String encoding = "utf-8";
			java.io.File file = new java.io.File(filePath);
			if ((file.isFile()) && (file.exists())) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					rs = rs + lineTxt;
				}
				read.close();
			}

		} catch (Exception e) {
			logger.error("", e);
		}
		return rs;
	}

	public static void getKey() {
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec("abcdefghijklmn".getBytes());
			key = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public static String getEncString(String strMing) {
		byte[] byteMi = (byte[]) null;
		byte[] byteMing = (byte[]) null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF8");
			byteMi = getEncCode(byteMing);
			strMi = base64en.encode(byteMi);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			base64en = null;
			byteMing = (byte[]) null;
			byteMi = (byte[]) null;
		}
		return strMi;
	}

	public static String getDesString(String strMi) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = (byte[]) null;
		byte[] byteMi = (byte[]) null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(strMi);
			byteMing = getDesCode(byteMi);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			base64De = null;
			byteMing = (byte[]) null;
			byteMi = (byte[]) null;
		}
		return strMing;
	}

	private static byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = (byte[]) null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(1, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	private static byte[] getDesCode(byte[] byteD) {
		byte[] byteFina = (byte[]) null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(2, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}
}
