package com.zte.im.util;

public class StringUtil {

	public static String subStrBySize(String str, int maxLength) {
		if (str != null && str.length() > maxLength) {
			return str.substring(0, maxLength) + "……";
		}
		return str;
	}

	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
}
