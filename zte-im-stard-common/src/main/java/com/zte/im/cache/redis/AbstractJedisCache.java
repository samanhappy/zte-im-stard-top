package com.zte.im.cache.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zte.im.util.SystemConfig;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

public abstract class AbstractJedisCache {
	// private static JedisPool pool;

	private static ShardedJedisPool pool;

	public static int content_Time;

	public static int FEE_TIME;

	public static int CAPICHA_TIME;

	public static int VER_CAPICHA_TIME;

	public static int CAPICHA_USER_TIME;

	public static ShardedJedisPool getPool() {
		return pool;
	}

	static {
		JedisPoolConfig config = new JedisPoolConfig();// Jedis池配置

		config.setBlockWhenExhausted(true);

		config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

		config.setJmxEnabled(true);

		// 最大空闲连接数, 默认8个
		config.setMaxIdle(40);

		// 最大连接数, 默认8个
		config.setMaxTotal(40);

		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
		// 默认-1
		config.setMaxWaitMillis(1000);

		// 最小空闲连接数, 默认0
		config.setMinIdle(10);

		config.setTestWhileIdle(true);

		config.setTestOnBorrow(true);

		List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(2);

		String[] servers = SystemConfig.redis_servers.split(",");
		for (String server : servers) {
			String[] strs = server.split(":");
			JedisShardInfo infoA = new JedisShardInfo(strs[0],
					Integer.parseInt(strs[1]));
			jdsInfoList.add(infoA);
		}

		pool = new ShardedJedisPool(config, jdsInfoList, Hashing.MURMUR_HASH,

		Sharded.DEFAULT_KEY_TAG_PATTERN);

	}

	/**
	 * 删除指定key(不能删除list)
	 * 
	 * @param key
	 * @return
	 */
	public abstract long del(String key);

	/**
	 * 查询指定key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public abstract boolean exists(String key);

	/**
	 * 注意： 作为一个key value存在，很多开发者自然的使用set/get方式来使用Redis，实际上这并不是最优化的使用方法。
	 * 尤其在未启用VM情况下，Redis全部数据需要放入内存，节约内存尤其重要。
	 * 假如一个key-value单元需要最小占用512字节，即使只存一个字节也占了512字节。
	 * 这时候就有一个设计模式，可以把key复用，几个key-value放入一个key中，value再作为一个set存入，
	 * 这样同样512字节就会存放10-100倍的容量。 用于存储多个key-value的值，比如可以存储好多的person Object
	 * 例子：>redis-cli 存储：redis 127.0.0.1:6379> hset personhash personId
	 * personObject 获得：redis 127.0.0.1:6379> hget personhash personId
	 * 
	 * @param key
	 *            hashset key
	 * @param field
	 *            相当于hashMap的key
	 * @param value
	 *            Object
	 */
	public abstract void hsetItem(String key, String field, String value);

	/**
	 * 获取指定field中的对象
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public abstract String hgetItem(String key, String field);

	/**
	 * 删除指定map 中的field
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public abstract long hdelItem(String key, String field);

	/**
	 * 判断指定field的值是否存在
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public abstract boolean hexists(String key, String field);

	/**
	 * 获取改key包含的所有field和value
	 * 
	 * @param key
	 * @return
	 */
	public abstract Map<String, String> hgetAllItem(String key);

	public abstract List<String> getListAll(String key);

	/**
	 * 存入集合（非排序集合）
	 * 
	 * @param key
	 * @param list
	 * @param time
	 *            秒，可以不传，默认不设置超时时间
	 */
	public abstract void lPush(String key, List<String> list, int time);

	/**
	 * String类型存储
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            秒，可以不传，默认不设置超时时间
	 * @return
	 */
	public abstract boolean set(String key, String value, int time);

	/**
	 * 获取String类型数据
	 * 
	 * @param key
	 * @return
	 */
	public abstract String get(String key);

	/**
	 * 获取步长为1的增量
	 * 
	 * @param key
	 * @return
	 */
	public abstract Long inc(String key);
}
