package com.zte.im.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zte.im.bean.GroupChat;
import com.zte.im.bean.Groupids;
import com.zte.im.bean.User;
import com.zte.im.bean.Users;
import com.zte.im.mongodb.MongoDBSupport;
import com.zte.im.service.IGroupService;
import com.zte.im.util.Constant;
import com.zte.im.util.GsonUtil;
import com.zte.im.util.MyJSON;

public class GroupServiceImpl implements IGroupService {

	private static GroupServiceImpl service;

	private GroupServiceImpl() {
		super();
	}

	public static GroupServiceImpl getInstance() {
		if (service == null) {
			service = new GroupServiceImpl();
		}
		return service;
	}

	/**
	 * 创建一个群
	 */
	public void saveGroup(GroupChat group) {
		DBObject dbObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(group));
		MongoDBSupport.getInstance().save(dbObject, Constant.GROUP_DATA);
	}

	/**
	 * 根据群id查询群
	 */
	public GroupChat findGroupChatById(Long groupid) {
		GroupChat gc = null;
		BasicDBObject param = new BasicDBObject();
		param.put("groupid", groupid);
		DBObject obj = MongoDBSupport.getInstance().queryOneByParam(Constant.GROUP_DATA, param, null);
		if (obj != null) {
			gc = GsonUtil.gson.fromJson(obj.toString(), GroupChat.class);
		}
		return gc;
	}

	/**
	 * 退群，返回最新群成员列表
	 */
	public List<User> quitGroup(Long uid, GroupChat group, List<User> removeUsers) {
		User u = UserServiceImpl.getInstance().getUserbyid(uid);
		boolean havagroup = false;// 判断用户在不在这个群中，如果不在则不删除了
		List<GroupChat> list = null;
		if (u != null) {
			list = u.getGroupids();
			if (list != null && list.size() > 0) {
				for (GroupChat g : list) {
					Long id = g.getGroupid();
					if (id.longValue() == group.getGroupid().longValue()) {
						havagroup = true;
					}
				}
			}
		}

		if (havagroup) {
			// 保存删除的用户用于推送消息
			if (removeUsers != null) {
				removeUsers.add(u);
			}

			BasicDBObject query = new BasicDBObject();
			query.put("uid", uid);
			BasicDBObject param = new BasicDBObject();
			GroupChat g = new GroupChat();
			g.setGroupid(group.getGroupid());
			Groupids ids = new Groupids();
			ids.setGroupids(g);
			DBObject dbObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(ids));
			param.put("$pull", dbObject); // 从指定文档删除一个内嵌文档
			param.append("$set", new BasicDBObject("ver", System.currentTimeMillis()));
			MongoDBSupport.getInstance().updateCollection(Constant.USER_DATA, query, param);

			// 如果群里没有成员了，直接删除群；否则删除user和userList的对应字段
			if (group.getUsers().size() == 1) {
				BasicDBObject paramGroupObj = new BasicDBObject();
				paramGroupObj.put("groupid", group.getGroupid());
				DBObject groupObj = MongoDBSupport.getInstance().queryOneByParam(Constant.GROUP_DATA, paramGroupObj,
						null);
				MongoDBSupport.getInstance().removeDBObject(Constant.GROUP_DATA, groupObj);
			} else {
				BasicDBObject query1 = new BasicDBObject();
				BasicDBObject param1 = new BasicDBObject();
				query1.put("groupid", group.getGroupid());
				com.zte.im.util.Users u1 = new com.zte.im.util.Users();
				u1.setUsers(uid);
				DBObject dbObject1 = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(u1));
				param1.put("$pull", dbObject1); // 从指定文档删除一个内嵌文档
				MongoDBSupport.getInstance().updateCollection(Constant.GROUP_DATA, query1, param1);

				BasicDBObject param2 = new BasicDBObject();
				Users users1 = new Users();
				User user = new User();
				user.setUid(uid);
				users1.setUserlist(user);
				DBObject dbObject2 = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(users1));
				param2.put("$pull", dbObject2); // 从指定文档删除一个内嵌文档
				param2.append("$set", new BasicDBObject("ver", System.currentTimeMillis()));
				param2.append("$inc", new BasicDBObject("len", -1));
				MongoDBSupport.getInstance().updateCollection(Constant.GROUP_DATA, query1, param2);
			}
		}

		// 删除group对象中的user
		Iterator<User> it = group.getUserlist().iterator();
		while (it.hasNext()) {
			User usr = it.next();
			if (usr.getUid() == uid)
				it.remove();
		}

		return group.getUserlist();

	}

	/**
	 * 为指定用户添加一个群
	 * 
	 * @param uid
	 * @param groupIds
	 */
	public User addGroup(Long uid, GroupChat group) {
		User u = UserServiceImpl.getInstance().getUserbyid(uid);
		boolean havagroup = false;// 判断用户在不在这个群中，如果在则不添加了
		if (u != null) {
			List<GroupChat> list = u.getGroupids();
			if (list != null && list.size() > 0) {
				for (GroupChat g : list) {
					Long id = g.getGroupid();
					if (id.longValue() == group.getGroupid().longValue()) {
						havagroup = true;
						break;
					}
				}
			}

			if (!havagroup) {
				BasicDBObject query = new BasicDBObject();
				query.put("uid", uid);
				BasicDBObject param = new BasicDBObject();
				GroupChat g = new GroupChat();
				g.setGroupid(group.getGroupid());
				g.setGroupName(group.getGroupName());
				g.setVer(group.getVer());
				Groupids ids = new Groupids();
				ids.setGroupids(g);
				DBObject dbObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(ids));
				param.put("$push", dbObject); // 为指定文档添加一个内嵌文档
				param.append("$set", new BasicDBObject("ver", System.currentTimeMillis()));
				MongoDBSupport.getInstance().updateCollection(Constant.USER_DATA, query, param);

				BasicDBObject query1 = new BasicDBObject();
				BasicDBObject param1 = new BasicDBObject();
				query1.put("groupid", group.getGroupid());
				com.zte.im.util.Users u1 = new com.zte.im.util.Users();
				u1.setUsers(uid);
				DBObject dbObject1 = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(u1));
				param1.put("$push", dbObject1); // 为指定文档添加一个内嵌文档
				MongoDBSupport.getInstance().updateCollection(Constant.GROUP_DATA, query1, param1);

				u.setGroupids(null);
				u.setPwd(null);
				u.setToken(null);
				BasicDBObject param2 = new BasicDBObject();
				Users users1 = new Users();
				users1.setUserlist(u);
				DBObject dbObject2 = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(users1));
				param2.put("$push", dbObject2); // 为指定文档添加一个内嵌文档
				param2.append("$set", new BasicDBObject("ver", System.currentTimeMillis()));
				param2.append("$inc", new BasicDBObject("len", 1));
				MongoDBSupport.getInstance().updateCollection(Constant.GROUP_DATA, query1, param2);
				return u;
			}
		}
		//不存在的用户或者用户已经在群组中时返回null
		return null;
	}

	/**
	 * 批量用户添加群
	 * 
	 * @param uids
	 * @param groupid
	 */
	public List<User> addGroup(List<Long> uids, GroupChat group, List<String> unameList) {
		if (group != null) {
			for (Long uid : uids) {
				User u = addGroup(uid, group);
				if (u != null) {
					group.getUserlist().add(u);
					unameList.add(u.getName());
				}
			}
		}
		return group.getUserlist();
	}

	@Override
	public List<User> quitGroup(List<Long> uids, GroupChat group, List<User> removeUsers) {
		List<User> users = null;
		for (Long uid : uids) {
			users = quitGroup(uid, group, removeUsers);
		}
		return users;
	}

	@Override
	public boolean removeGroup(GroupChat group) {
		for (Long uid : group.getUsers()) {
			quitGroup(uid, group, null);
		}
		return true;
	}

	@Override
	public void updateGroup(GroupChat group) {
		DBObject dbObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(group));
		DBObject query = new BasicDBObject();
		query.put("groupid", group.getGroupid());
		MongoDBSupport.getInstance().findAndModifyCollection(Constant.GROUP_DATA, query, dbObject);
	}

	@Override
	public void addUserToAllGroup(Long uid) {
		if (uid != null) {
			GroupChat group = findGroupChatById(0L);
			if (group == null || group.getUsers() == null) {
				group = new GroupChat();
				group.setGroupid(0L);
				List<Long> users = new ArrayList<Long>();
				users.add(uid);
				group.setUsers(users);
				DBObject userDBObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(group));
				MongoDBSupport.getInstance().save(userDBObject, Constant.GROUP_DATA);
			} else {
				if (!group.getUsers().contains(uid)) {
					group.getUsers().add(uid);
					DBObject userDBObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(group));
					BasicDBObject query = new BasicDBObject("groupid", 0L);
					MongoDBSupport.getInstance().upsertCollection(Constant.GROUP_DATA, query, userDBObject);
				}
			}
		}
	}

	@Override
	public void removeUserFromAllGroup(Long uid) {
		if (uid != null) {
			GroupChat group = findGroupChatById(0L);
			if (group != null && group.getUsers() != null && group.getUsers().contains(uid)) {
				group.getUsers().remove(uid);
				DBObject userDBObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(group));
				BasicDBObject query = new BasicDBObject("groupid", 0L);
				MongoDBSupport.getInstance().upsertCollection(Constant.GROUP_DATA, query, userDBObject);
			}
		}
	}

	@Override
	public void removeUserFromAllGroup(List<Long> uids) {
		if (uids != null && uids.size() > 0) {
			GroupChat group = findGroupChatById(0L);
			if (group != null && group.getUsers() != null) {
				for (Long uid : uids) {
					group.getUsers().remove(uid);
				}
				DBObject userDBObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(group));
				BasicDBObject query = new BasicDBObject("groupid", 0L);
				MongoDBSupport.getInstance().upsertCollection(Constant.GROUP_DATA, query, userDBObject);
			}
		}
	}

	@Override
	public void initAllGroup() {
		GroupChat group = new GroupChat();
		group.setGroupid(0L);
		List<Long> users = new ArrayList<Long>();
		DBCursor cur = MongoDBSupport.getInstance().queryCollection(Constant.USER_DATA);
		List<DBObject> list = cur.toArray();
		for (DBObject obj : list) {
			users.add(Long.valueOf(obj.get("uid").toString()));
		}
		group.setUsers(users);
		DBObject userDBObject = (DBObject) MyJSON.parse(GsonUtil.gson.toJson(group));
		BasicDBObject query = new BasicDBObject("groupid", 0L);
		MongoDBSupport.getInstance().upsertCollection(Constant.GROUP_DATA, query, userDBObject);
	}

	public static void main(String[] args) {
		GroupServiceImpl service = GroupServiceImpl.getInstance();
		service.addUserToAllGroup(1L);
		service.addUserToAllGroup(2L);

		service.addUserToAllGroup(3L);
		service.removeUserFromAllGroup(2L);

		List<Long> uids = new ArrayList<Long>();
		uids.add(1L);
		uids.add(2L);
		service.removeUserFromAllGroup(uids);
	}
}
