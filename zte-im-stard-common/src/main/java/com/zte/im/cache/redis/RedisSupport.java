package com.zte.im.cache.redis;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

public class RedisSupport extends AbstractJedisCache {

	public static final Logger logger = LoggerFactory.getLogger(RedisSupport.class);

	public static RedisSupport service = new RedisSupport();

	public static RedisSupport getInstance() {
		if (service == null) {
			service = new RedisSupport();
		}
		return service;
	}

	public long del(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			return jedis.del(key);
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
			return 0;
		} finally {
			getPool().returnResource(jedis);
		}
	}

	public boolean exists(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
			return false;
		} finally {
			getPool().returnResource(jedis);
		}
	}

	public void hsetItem(String key, String field, String value) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
		} finally {
			getPool().returnResource(jedis);
		}
	}

	public String hgetItem(String key, String field) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			return jedis.hget(key, field);
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
			return null;
		} finally {
			getPool().returnResource(jedis);
		}
	}

	public long hdelItem(String key, String field) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			return jedis.hdel(key, field);
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
			return 0;
		} finally {
			getPool().returnResource(jedis);
		}
	}

	public boolean hexists(String key, String field) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			return jedis.hexists(key, field);
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
			return false;
		} finally {
			getPool().returnResource(jedis);
		}

	}

	public Map<String, String> hgetAllItem(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
			return null;
		} finally {
			getPool().returnResource(jedis);
		}
	}

	public void lPush(String key, List<String> list, int time) {
		ShardedJedis jedis = null;
		if (list != null && list.size() > 0) {
			try {
				jedis = getPool().getResource();
				jedis.del(key);
				for (String value : list) {
					jedis.lpush(key, value);
				}
				if (time != 0) {
					jedis.expire(key, time);
				}
			} catch (Exception e) {
				logger.error("", e);
				getPool().returnBrokenResource(jedis);
			} finally {
				getPool().returnResource(jedis);
			}
		}
	}

	public List<String> getListAll(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			return jedis.lrange(key, 0, -1);
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
			return null;
		} finally {
			getPool().returnResource(jedis);
		}
	}

	public boolean set(String key, String value, int time) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			jedis.set(key, value);
			if (time > 0) {
				jedis.expire(key, time);
			}
			return true;
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
			return false;
		} finally {
			getPool().returnResource(jedis);
		}
	}
	
	public boolean set(String key, String value) {
		return set(key, value, -1);
	}

	public String get(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			return jedis.get(key);
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
			return null;
		} finally {
			getPool().returnResource(jedis);
		}
	}

	public Long inc(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			return jedis.incr(key);
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
			return null;
		} finally {
			getPool().returnResource(jedis);
		}
	}
	
	public Long incBy(String key, long value) {
		ShardedJedis jedis = null;
		try {
			jedis = getPool().getResource();
			return jedis.incrBy(key, value);
		} catch (Exception e) {
			logger.error("", e);
			getPool().returnBrokenResource(jedis);
			return null;
		} finally {
			getPool().returnResource(jedis);
		}
	}

}
