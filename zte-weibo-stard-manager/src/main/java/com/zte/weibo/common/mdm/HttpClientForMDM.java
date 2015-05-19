package com.zte.weibo.common.mdm;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.alibaba.fastjson.JSON;
import com.zte.im.util.GsonUtil;
import com.zte.weibo.common.util.SystemConfig;

/**
 * 分享内容获取
 * 
 * @author hp
 */
public class HttpClientForMDM {

	HttpClient client = new DefaultHttpClient();

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(HttpClientForMDM.class);

	private static HttpClientForMDM instance = null;

	public static HttpClientForMDM getInstance() {
		if (null == instance) {
			instance = new HttpClientForMDM();
		}
		return instance;
	}

	public MDMResp request(MDMParam param) {
		MDMResp resp = null;
		String returnStr = null;
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 7000);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);

		try {
			HttpPost post = new HttpPost(SystemConfig.mdm_server);
			post.setParams(params);
			String paramStr = JSON.toJSONString(param);
			logger.info("sso request param:{}", paramStr);
			post.setEntity(new StringEntity(paramStr));
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				byte[] result = EntityUtils.toByteArray(response.getEntity());
				if (result != null && result.length > 0) {
					returnStr = new String(result,"UTF-8");
					resp = GsonUtil.gson.fromJson(returnStr, MDMResp.class);
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		logger.info("sso return:{}" + returnStr);

		return resp;
	}

	public static void main(String[] args) {
		MDMParam param = new MDMParam();
		MDMResp resp = HttpClientForMDM.getInstance().request(param);
		logger.info(resp.result);
	}

}
