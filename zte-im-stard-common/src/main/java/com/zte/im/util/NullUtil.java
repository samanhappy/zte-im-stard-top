package com.zte.im.util;

public class NullUtil {

	public static boolean isAnyNull(Object... objs) {
		for (Object obj : objs) {
			if (obj == null) {
				return true;
			}
		}
		return false;
	}

}
