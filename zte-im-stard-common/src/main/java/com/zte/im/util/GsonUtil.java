package com.zte.im.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
	public static Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation() // 不导出实体中没有用@Expose注解的属性
			.enableComplexMapKeySerialization().create(); // 支持Map的key为复杂对象的形式
}
