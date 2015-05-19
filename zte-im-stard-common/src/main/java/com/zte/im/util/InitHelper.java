package com.zte.im.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.im.cache.redis.RedisSupport;

public class InitHelper {
	
	private final static Logger logger = LoggerFactory.getLogger(InitHelper.class);
	
	/**
	 * 初始化IDSequence，uid从100开始
	 */
	public static void initIDSequence() {
		if (!RedisSupport.getInstance().exists(IDSequence.IM_UIDSEQ)) {
			RedisSupport.getInstance().incBy(IDSequence.IM_UIDSEQ, 100);
			logger.info("success to init uid-seq");
		}
	}

}
