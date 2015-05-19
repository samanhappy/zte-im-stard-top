package com.zte.im.util;

import com.alibaba.fastjson.JSONArray;

/**
 * JsonArray转换为String数组.
 * @author yejun
 *
 */
public class JsonArraytoStringArray {
    
    public static String[] parseJsonString (JSONArray json) {
       
        String[] uid = new String[json.size()];
        for(int i = 0; i < json.size(); i++) {
            uid[i] = json.getString(i);
        }
        return uid;
    }
}
