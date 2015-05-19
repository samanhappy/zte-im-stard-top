package com.zte.util.system;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

public class PinYinUtil {

	public static String getPinyinStr(String str) {
		if (str != null) {
			return PinyinHelper.convertToPinyinString(str, "",
					PinyinFormat.WITHOUT_TONE);
		}
		return null;
	}

	public static String getT9PinyinStr(String str) {
		if (str != null) {
			return PinyinHelper.getShortPinyin(str);
		}
		return null;
	}

}
