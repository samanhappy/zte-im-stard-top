package com.zte.im.service.impl;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.bean.Composition;
import com.zte.im.bean.Feedback;
import com.zte.im.bean.Firm;
import com.zte.im.bean.Param;
import com.zte.im.bean.UpdateRecord;
import com.zte.im.bean.User;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.mybatis.bean.UcMember;
import com.zte.im.mybatis.bean.UserPermission;
import com.zte.im.service.ICompositionService;
import com.zte.im.service.IFirmService;
import com.zte.im.service.IParamSettingService;
import com.zte.im.service.IUserService;
import com.zte.im.service.IVersionService;
import com.zte.im.util.Constant;
import com.zte.im.util.EncryptionDecryption;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.IDSequence;
import com.zte.im.util.MyJSON;
import com.zte.im.util.Page;
import com.zte.im.util.ViopIdGenerator;

public class UserServiceImpl implements IUserService {

	private static UserServiceImpl service;

	IVersionService verService = VersionServiceImpl.getInstance();

	private static String USERDATA = Constant.USER_DATA;
	private static String FEEDBACK = Constant.FEEDBACK;

	EncryptionDecryption enc = null;

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserServiceImpl() {
		super();
		try {
			enc = new EncryptionDecryption();
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public static IUserService getInstance() {
		if (service == null) {
			service = new UserServiceImpl();
		}
		return service;
	}

	/**
	 * 根据企业id和工号获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getUserbyid(Long gid, String jid) {
		User user = null;
		BasicDBObject param = new BasicDBObject();
		param.put("gid", gid);
		param.put("jid", jid);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(Constant.USER_DATA, param, null);
		if (obj != null) {
			user = JSON.parseObject(obj.toString(), User.class);
		}
		return user;
	}

	/**
	 * 根据部门id查询用户分组
	 * 
	 * @param gid
	 * @param cid
	 * @return
	 */
	public List<DBObject> getUserbyid(Long gid, Long cid) {
		BasicDBObject param = new BasicDBObject();
		param.put("gid", gid);
		param.put("cid", cid);
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(Constant.USER_DATA, param);
		if (cur != null && cur.size() > 0) {
			return cur.toArray();
		}
		return null;
	}

	/**
	 * 根据uid查询用户
	 * 
	 * @param uid
	 * @return
	 */
	public User getUserbyid(Long uid) {
		User user = null;
		BasicDBObject param = new BasicDBObject();
		param.put("uid", uid);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(Constant.USER_DATA, param, null);
		if (obj != null) {
			user = GsonUtil.gson.fromJson(obj.toString(), User.class);
		}
		return user;
	}

	/**
	 * 根据uid查询用户
	 * 
	 * @param uid
	 * @return
	 */
	public User getTUserbyid(Long uid) {
		User user = null;
		BasicDBObject param = new BasicDBObject();
		param.put("uid", uid);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(Constant.T_USER, param, null);
		if (obj != null) {
			user = GsonUtil.gson.fromJson(obj.toString(), User.class);
		}
		return user;
	}

	@Override
	public User getUserbyJid(String jid) {
		User user = null;
		BasicDBObject param = new BasicDBObject();
		param.put("jid", jid);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(Constant.USER_DATA, param, null);
		if (obj != null) {
			user = GsonUtil.gson.fromJson(obj.toString(), User.class);
		}
		return user;
	}

	@Override
	public User getUserbyLoginname(String loginname) {
		User user = null;
		BasicDBObject param = new BasicDBObject();
		param.put("loginname", loginname);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(Constant.USER_DATA, param, null);
		if (obj != null) {
			user = GsonUtil.gson.fromJson(obj.toString(), User.class);
		}
		return user;
	}

	/**
	 * 增加用户群组版本
	 * 
	 * @param uid
	 * @return
	 */
	public void incUserGroupVer(Long uid) {
		BasicDBObject query = new BasicDBObject();
		query.put("uid", uid);

		BasicDBObject param = new BasicDBObject();
		param.put("$inc", new BasicDBObject("groupVer", 1));
		MongoDBSupport.getInstance().updateCollection(Constant.USER_DATA, query, param);
	}

	/**
	 * 根据企业id分页获取用户数据
	 * 
	 * @param gid
	 * @return
	 */
	public List<User> getUserbyGid(Long gid, Page page, User user) {
		List<User> users = null;

		BasicDBObject keys = new BasicDBObject();// 字段
		keys.put("groupids", 0);

		BasicDBObject ref = new BasicDBObject();// 条件
		ref.put("gid", gid);

		BasicDBObject sort = new BasicDBObject();// 排序
		sort.put("sequ", 1);

		DBCursor cur = MongoDBSupport.getInstance().queryCollectionByParam(Constant.USER_DATA, page, ref, keys, sort);
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<User>>() {
			}.getType();
			List<DBObject> list = cur.toArray();

			spilitByPerm(user, list);

			users = GsonUtil.gson.fromJson(list.toString(), type);
		}
		return users;
	}

	/**
	 * 属性访问保护过滤.
	 */
	public void spilitByPerm(User user, List<DBObject> list) {

		if (user != null && list != null && list.size() > 0) {

			IParamSettingService psService = ParamSettingServiceImpl.getInstance();
			Param param = psService.getParamByType("contact");
			if (param != null && param.getProtectedPropVals() != null) {
				String[] props = param.getProtectedPropVals().split(",");
				if (props.length > 0) {
					for (DBObject data : list) {
						boolean isProtected = data.containsField("isProtected") ? (Boolean) data.get("isProtected")
								: false;
						// 没有开启保护设置的不处理
						if (!isProtected) {
							continue;
						}
						
						BasicDBList postPerms = null;
						BasicDBList deptPerms = null;
						BasicDBList userPerms = null;
						if (data.containsField("postPerms")) {
							postPerms = (BasicDBList) data.get("postPerms");
							data.removeField("postPerms");
						}
						if (data.containsField("deptPerms")) {
							deptPerms = (BasicDBList) data.get("deptPerms");
							data.removeField("deptPerms");
						}
						if (data.containsField("userPerms")) {
							userPerms = (BasicDBList) data.get("userPerms");
							data.removeField("userPerms");
						}

						// 对自己不做过滤
						if (user.getId() != null && user.getId().equals(data.get("id"))) {
							continue;
						}

						if ((postPerms == null || !postPerms.contains(user.getPost()))
								&& (deptPerms == null || !deptPerms.contains(user.getDeptId()))
								&& (userPerms == null || !userPerms.contains(user.getId()))) {
							logger.info("spilit props of user {} for {}", data.get("name"), user.getName());
							for (String prop : props) {
								if (data.containsField(prop)) {
									data.removeField(prop);
								}
							}
						}
					}
				}
			}
		}

	}

	public Long getUserCountbyGid(Long gid) {
		BasicDBObject param = new BasicDBObject();
		param.put("gid", gid);
		Long count = MongoDBSupport.getInstance().getCount(Constant.USER_DATA, param);
		return count;
	}

	/**
	 * 注册
	 * 
	 * @param qid
	 *            企业id
	 * @param jid
	 *            工号
	 * @param pwd
	 *            密码
	 * @return
	 */
	public User regUser(Long gid, String jid, String pwd) {
		return null;
	}

	@Override
	public void updateUser(User user) {
		BasicDBObject query = new BasicDBObject();
		query.put("uid", user.getUid());
		BasicDBObject param = new BasicDBObject();

		BasicDBObject set = new BasicDBObject();
		if (user.getAutograph() != null) {
			set.put("autograph", user.getAutograph());
		}
		if (user.getMinipicurl() != null) {
			set.put("minipicurl", user.getMinipicurl());
		}
		if (user.getBigpicurl() != null) {
			set.put("bigpicurl", user.getBigpicurl());
		}
		if (user.getSn() != null) {
			set.put("sn", user.getSn());
		}
		if (user.getOtherMob() != null) {
			set.put("otherMob", user.getOtherMob());
		}
		if (user.getEmail() != null) {
			set.put("email", user.getEmail());
		}
		if (user.getOtherTele() != null) {
			set.put("otherTele", user.getOtherTele());
		}
		if (user.getFax() != null) {
			set.put("fax", user.getFax());
		}
		if (user.getRemark() != null) {
			set.put("remark", user.getRemark());
		}
		if (user.getPwdErrors() != null) {
			set.put("pwdErrors", user.getPwdErrors());
		}
		if (user.getPwd() != null) {
			set.put("pwd", user.getPwd());
		}
		if (user.getIsProtected() != null) {
			set.put("isProtected", user.getIsProtected());
		}
		if (user.getUsable() != null) {
			set.put("usable", user.getUsable());
		}

		param.put("$set", set);
		MongoDBSupport.getInstance().updateCollection(Constant.USER_DATA, query, param);

		// 修改群组和企业里面的头像
		if (user.getMinipicurl() != null || user.getBigpicurl() != null) {
			updateIcon4ComPosit(user.getUid(), user.getMinipicurl(), user.getBigpicurl());
			updateIcon4GroupDate(user.getUid(), user.getMinipicurl(), user.getBigpicurl());
		}

		// 记录用户更新信息
		UpdateRecord record = new UpdateRecord();
		record.setGid(user.getGid());
		record.setUid(user.getUid());
		record.setUserListVer(new Date().getTime());
		record.setOper("changed");
		UpdateRecordServiceImpl.getInstance().saveUpdateRecord(record);
		// 更新用户信息列表版本
		verService.incUserListVer();
	}

	private void updateIcon4GroupDate(long uid, String hp, String shp) {
		BasicDBObject query = new BasicDBObject();
		query.put("userlist.uid", uid);
		BasicDBObject param = new BasicDBObject();
		param.put("$set", new BasicDBObject("userlist.$.minipicurl", hp).append("userlist.$.bigpicurl", shp));
		MongoDBSupport.getInstance().updateCollection(Constant.GROUP_DATA, query, param);
	}

	private void updateIcon4ComPosit(long uid, String hp, String shp) {
		BasicDBObject query = new BasicDBObject();
		query.put("users.uid", uid);
		BasicDBObject param = new BasicDBObject();
		param.put("$set", new BasicDBObject("users.$.minipicurl", hp).append("users.$.bigpicurl", shp));
		MongoDBSupport.getInstance().updateCollection(Constant.COM_POSIT, query, param);
	}

	@Override
	public List<User> getUserbyGid(Long gid) {
		List<User> users = null;
		BasicDBObject param1 = new BasicDBObject();// 条件
		param1.put("gid", gid);
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionConditions(Constant.USER_DATA, param1,
				new BasicDBObject());
		if (cur != null && cur.size() > 0) {
			Type type = new TypeToken<List<User>>() {
			}.getType();
			users = GsonUtil.gson.fromJson(cur.toArray().toString(), type);
		}
		return users;
	}

	@Override
	public List<Long> searchUser(String name) {
		List<Long> users = new ArrayList<Long>();
		BasicDBObject param = new BasicDBObject();// 条件
		param.put("uid", 1);

		BasicDBObject query = new BasicDBObject();
		query.put("name", Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE));
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionConditions(Constant.USER_DATA, param, query);
		if (cur != null && cur.size() > 0) {
			for (DBObject obj : cur.toArray()) {
				users.add(Long.valueOf(obj.get("uid").toString()));
			}
		}
		return users;
	}

	public static void main(String[] args) {
		UserServiceImpl.getInstance().searchUser("测试");
	}

	@Override
	public boolean saveOrUpdate(UcMember member, String deptId, String post) {
		return saveOrUpdate(member, deptId, post, false);
	}

	@Override
	public boolean saveOrUpdate(UcMember member, String deptId, String post, boolean isImport) {

		if (member.getId() == null || deptId == null) {
			logger.info("member id or deptId not exists");
			return false;
		}

		// 检查企业
		IFirmService firmService = FirmServiceImpl.getInstance();
		String tenantId = member.getTenantId();
		Firm firm = firmService.getFirm(tenantId);
		if (firm == null || firm.getGid() == null) {
			logger.info("firm not exists for:" + tenantId);
			return false;
		}

		// 检查所属部门
		ICompositionService comService = CompositionServiceImpl.getInstance();
		Composition comp = comService.getComBySqlId(deptId);
		if (comp == null) {
			logger.info("composition not exists for:" + deptId);
			return false;
		}

		boolean userExists = true;
		User user = getUserbySqlId(member.getId());

		// 记录用户是新增还是更新操作
		String oper = "changed";
		if (user == null) {
			oper = "added";
			userExists = false;
			user = new User();
			user.setId(member.getId());

			if (isImport) {
				// 同步导入的用户直接使用用户ID
				user.setUid(Long.valueOf(member.getUid()));
			} else {
				// 统一使用自动生成的UID
				user.setUid(IDSequence.getGroupId());
			}
		}

		user.setGid(firm.getGid());
		user.setName(member.getCn());
		try {
			user.setPwd(enc.encrypt(member.getPassword()));
		} catch (Exception e) {
			logger.error("", e);
		}
		user.setJid(member.getUid());
		user.setMob(member.getMobile());
		user.setEmail(member.getMail());
		user.setCid(comp.getCid());
		user.setCname(comp.getName());
		user.setPost(post);
		user.setAutograph(member.getPhotoSign());
		user.setMinipicurl(member.getPhoto());
		user.setBigpicurl(member.getPhoto());
		user.setTelephone(member.getHometelephone());
		user.setFax(member.getFax());
		user.setRemark(member.getInfo());
		user.setLoginname(member.getL());
		user.setSequ(member.getSequ());

		// 更新增加的手机号码和固定电话字段
		user.setOtherMob("");
		user.setOtherTele("");
		if (member.getBirthday() != null) {
			user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").format(member.getBirthday()));
		}

		// 保存部门数据库ID
		user.setDeptId(deptId);

		if (user.getViopId() == null) {
			// 生成viopId
			user.setViopId(ViopIdGenerator.pop());
		}

		BasicDBObject dbObject = (BasicDBObject) MyJSON.parse(GsonUtil.gson.toJson(user));

		if (userExists) {
			BasicDBObject query = new BasicDBObject();
			query.put("id", user.getId());
			MongoDBSupport.getInstance().findAndModifyCollection(USERDATA, query, dbObject);
		} else {
			MongoDBSupport.getInstance().save(dbObject, USERDATA);
			// 添加用户到全部人群组
			GroupServiceImpl.getInstance().addUserToAllGroup(user.getUid());
		}

		// 记录用户更新信息
		UpdateRecord record = new UpdateRecord();
		record.setGid(firm.getGid());
		record.setUid(user.getUid());
		record.setUserListVer(new Date().getTime());
		record.setOper(oper);
		UpdateRecordServiceImpl.getInstance().saveUpdateRecord(record);
		logger.info("update record :" + record.toString());

		return true;
	}

	@Override
	public User getUserbySqlId(String id) {
		User user = null;
		BasicDBObject param = new BasicDBObject();
		param.put("id", id);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(USERDATA, param, null);
		if (obj != null) {
			user = GsonUtil.gson.fromJson(obj.toString(), User.class);
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getUidsbySqlIds(List<String> ids) {
		List<Long> uids = null;
		BasicDBObject param = new BasicDBObject();// 要获取的数据
		param.put("uid", 1);
		BasicDBObject query = new BasicDBObject();
		query.put("id", new BasicDBObject("$in", ids));
		DBCursor cur = MongoDBSupport.getInstance().queryCollectionConditions(USERDATA, param, query);
		if (cur != null) {
			uids = GsonUtil.gson.fromJson(cur.toArray().toString(), List.class);
		}
		return uids;
	}

	@Override
	public boolean removeUserBySqlId(List<String> ids) {

		BasicDBObject param = new BasicDBObject();
		param.put("id", new BasicDBObject("$in", ids));

		// 回收viopId
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(USERDATA, param);

		// 记录删除用户信息
		if (cur != null) {
			for (DBObject obj : cur.toArray()) {
				UpdateRecord record = new UpdateRecord();
				record.setGid((Long) obj.get("gid"));
				record.setUid((Long) obj.get("uid"));
				record.setUserListVer(new Date().getTime());
				record.setOper("deleted");
				UpdateRecordServiceImpl.getInstance().saveUpdateRecord(record);
				logger.info("update record :" + record.toString());
			}
		}
		if (cur != null) {
			for (DBObject obj : cur.toArray()) {
				if (obj.containsField(Constant.VIOP_ID)) {
					String viopId = obj.get(Constant.VIOP_ID).toString();
					if (!"".equals(viopId)) {
						ViopIdGenerator.put(Integer.valueOf(viopId));
						logger.info("re-collection viopId:{}", viopId);
					}
				}
			}
		}
		MongoDBSupport.getInstance().removeDBObjects(USERDATA, param);
		// 从所有人群组中删除用户
		List<Long> uids = getUidsbySqlIds(ids);
		GroupServiceImpl.getInstance().removeUserFromAllGroup(uids);

		return true;
	}

	@Override
	public void updateUserPerm(String id, List<UserPermission> permList) {

		if (id != null && !"".equals(id)) {
			BasicDBObject query = new BasicDBObject();
			query.put("id", id);

			List<String> postPerms = new ArrayList<String>();
			List<String> deptPerms = new ArrayList<String>();
			List<String> userPerms = new ArrayList<String>();
			if (permList != null) {
				for (UserPermission up : permList) {
					if (up.getPermType() == 1) {
						postPerms.add(up.getPermName());
					} else if (up.getPermType() == 2) {
						deptPerms.add(up.getPermId());
					} else if (up.getPermType() == 3) {
						userPerms.add(up.getPermId());
					}
				}
			}
			BasicDBObject oper = new BasicDBObject();
			BasicDBObject setParam = new BasicDBObject();
			BasicDBObject unsetParam = new BasicDBObject();
			if (postPerms.size() != 0) {
				setParam.put("postPerms", MyJSON.parse(GsonUtil.gson.toJson(postPerms)));
			} else {
				// 没有设置权限直接清除字段
				unsetParam.put("postPerms", "");
			}

			if (deptPerms.size() != 0) {
				setParam.put("deptPerms", MyJSON.parse(GsonUtil.gson.toJson(deptPerms)));
			} else {
				// 没有设置权限直接清除字段
				unsetParam.put("deptPerms", "");
			}

			if (userPerms.size() != 0) {
				setParam.put("userPerms", MyJSON.parse(GsonUtil.gson.toJson(userPerms)));
			} else {
				// 没有设置权限直接清除字段
				unsetParam.put("userPerms", "");
			}

			if (setParam.size() != 0) {
				oper.put("$set", setParam);
			}
			if (unsetParam.size() != 0) {
				oper.put("$unset", unsetParam);
			}
			MongoDBSupport.getInstance().updateCollection(Constant.USER_DATA, query, oper);
		}
	}

	/**
	 * 新增反馈
	 * 
	 * @param fb
	 */
	@Override
	public void saveFeedback(Feedback fb) {
		BasicDBObject query = new BasicDBObject();

		Long id = IDSequence.getGroupId();
		query.put("id", id);
		query.put("type", fb.getType());
		query.put("name", fb.getName());
		query.put("mobile", fb.getMobile());
		query.put("device", fb.getDevice());
		query.put("os", fb.getOs());
		query.put("resolution", fb.getResolution());
		query.put("content", fb.getContent());
		query.put("time", fb.getTime());

		MongoDBSupport.getInstance().save(query, FEEDBACK);
	}
}
