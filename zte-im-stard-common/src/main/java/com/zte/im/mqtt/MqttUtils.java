package com.zte.im.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import android.content.Context;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//
//import com.zte.im.common.DeviceUuidFactory;
//import com.zte.im.common.IMApplication;
//import com.zte.im.network.NetworkManager;
//import com.zte.im.util.LogUtils;

/**
 * 
 * 打印Log
 * <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2014年7月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MqttUtils {
	
    public static boolean isWriteLog = true;
    public static String DEVICE_ID = null;	
    
	private final static Logger logger = LoggerFactory.getLogger(MqttUtils.class);
    
    // 静态全局变量
    public final static int CHAT_QUERY_ONLINE = 4;// 是否在线

    public static void d(final String tag, String message) {
    	logger.info(tag+":"+message);
    }

    public static void v(final String tag, String message) {
    	logger.info(tag+":"+message);
    }

    public static void i(final String tag, String message) {
    	logger.info(tag+":"+message);
    }

    public static void w(final String tag, String message) {
    	logger.info(tag+":"+message);
    }

    public static void e(final String tag, String message) {
    	logger.info(tag+":"+message);
    }
    
    public static boolean isNetConnected() {
//        return NetworkManager.isNetConnected(IMApplication.getAppContext());

        
              return true;
    }
   
    public static final String getDeviceImei() {
//        TelephonyManager tmManager = (TelephonyManager) IMApplication.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
//        return tmManager.getSubscriberId();
        
        return "";
         
    }
    
    public static String getDeviceId() {
//        if (DEVICE_ID == null || DEVICE_ID.length() > 0) {
//            DeviceUuidFactory factory = new DeviceUuidFactory(IMApplication.getAppContext());
//            DEVICE_ID = factory.getDeviceUuid();
//        }
//        return DEVICE_ID;
    	return "";
    }


}
