package com.zte.im.twitter.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.im.bean.TToken;
import com.zte.im.cache.redis.RedisSupport;
import com.zte.im.service.impl.TwitterServiceImpl;
import com.zte.im.sso.HttpClientForSSO;
import com.zte.im.sso.SSOParam;
import com.zte.im.sso.SSOResp;

/**
 * 定时校验token有效性
 * 
 * @author kaka
 * 
 */
public class CheckTokenJob {

	private static Logger logger = LoggerFactory.getLogger(CheckTokenJob.class);

	protected void execute() {
		logger.info("start check token...");
		try {
			List<TToken> tokens = TwitterServiceImpl.getInstance()
					.getAllToken();
			if (null != tokens && !tokens.isEmpty()) {
				for (TToken ttoken : tokens) {
					logger.info("check token:{}", ttoken);
					String token = ttoken.getToken();
					if (isTokenExpire(token)) {
						logger.info("token:{} is expire, remove it", ttoken);
						TwitterServiceImpl.getInstance().delToken(
								ttoken.getTokenId());
						RedisSupport.getInstance().del(token);
					}
				}
			} else {
				logger.info("no token to check");
			}
		} catch (Exception e) {
			logger.error("check token error:{}", e);
		}
		logger.info("end check token!!!");
	}

	private boolean isTokenExpire(String token) {
		SSOParam param = new SSOParam();
		param.setToken(token);
		SSOResp resp = HttpClientForSSO.getInstance().request(param);
		if (resp != null && "1".equals(resp.RC)) {
				return true;
		}
		return false;
	}
}
