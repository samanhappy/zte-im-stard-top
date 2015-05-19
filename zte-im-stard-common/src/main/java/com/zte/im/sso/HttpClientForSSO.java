package com.zte.im.sso;

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
import com.zte.im.util.SystemConfig;

/**
 * 微博内容获取
 * 
 * @author hp
 */
public class HttpClientForSSO {

	HttpClient client = new DefaultHttpClient();

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(HttpClientForSSO.class);

	private static HttpClientForSSO instance = null;

	public static HttpClientForSSO getInstance() {
		if (null == instance) {
			instance = new HttpClientForSSO();
		}
		return instance;
	}

	public SSOResp request(SSOParam param) {
		SSOResp resp = null;
		String returnStr = null;
		HttpPost post = new HttpPost(SystemConfig.sso_server);
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 7000);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		post.setParams(params);

		try {
			String paramStr = JSON.toJSONString(param);
			logger.info("sso request param:{}", paramStr);
			post.setEntity(new StringEntity(paramStr));
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				byte[] result = EntityUtils.toByteArray(response.getEntity());
				if (result != null && result.length > 0) {
					returnStr = new String(result);
					resp = GsonUtil.gson.fromJson(returnStr, SSOResp.class);
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("HttpClientForSSO error:{}", e.getMessage());
		} catch (IOException e) {
			logger.error("HttpClientForSSO error:{}", e.getMessage());
		}
		logger.info("sso return:{}" + returnStr);

		return resp;
	}

}
