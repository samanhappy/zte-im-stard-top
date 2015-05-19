package com.zte.im.util;

import com.zte.im.cache.redis.RedisSupport;

public class IDSequence {

	private static final String JID_UID_MAP = "JID-UID";
	public static final String IM_UIDSEQ = "im_uidseq";
	
	public static final String dept_cid = "dept_cid_seq";

	public static Long getUID() {
		Long id = RedisSupport.getInstance().inc(IM_UIDSEQ);
		return id;
	}
	
	/**
	 * 根据工号获取用户ID.
	 * 
	 * @return
	 */
	public static Long getUIDByJid(String jid) {
		if (jid != null) {
			Long uid = null;
			try {
				uid = Long.valueOf(RedisSupport.getInstance().hgetItem(JID_UID_MAP, jid));
			} catch (Exception e) {
			}
			if (uid == null) {
				if (uid == null) {
					uid = RedisSupport.getInstance().inc(IM_UIDSEQ);
					RedisSupport.getInstance().hsetItem(JID_UID_MAP, jid, uid.toString());
				}
			}
			return uid;
		}
		return RedisSupport.getInstance().inc(IM_UIDSEQ);
	}

	/**
	 * mqtt订阅会重复，客户端要求id要不一样
	 * 
	 * @return
	 */
	public static Long getGroupId() {
		Long id = RedisSupport.getInstance().inc(IM_UIDSEQ);
		return id;
	}

	

	public String getToken() {
		Long time = System.currentTimeMillis();
		String temp = time.toString();
		temp = temp.substring(6, temp.length());
		StringBuffer buf = new StringBuffer(temp);
		buf.append("_");
		buf.append(createPassword(8));
		return buf.toString();
	}

	private String createPassword(int length) {
		String str = "0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(str.charAt((int) (Math.random() * 36)));
		}
		return builder.toString().toUpperCase();
	}

}
