//package com.zte.im.pool;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.mongodb.DBObject;
//import com.zte.im.bean.GroupChat;
//import com.zte.im.bean.User;
//import com.zte.im.service.IGroupServiceImpl;
//import com.zte.im.service.IUserService;
//import com.zte.im.service.impl.GroupServiceImpl;
//import com.zte.im.service.impl.UserServiceImpl;
//
//
//public class IMHandleCallable implements Runnable, Serializable {
//	private static final long serialVersionUID = 1L;
//	
//	private Logger logger = LoggerFactory.getLogger(IMHandleCallable.class);
//
//	private Long uid;
//	
//	private Long groupID;
//	
//	private List<Long> users;
//	
//	private GroupChat group;
//	
//	public IMHandleCallable(GroupChat group,List<Long> users,Long groupID,Long uid){
//		this.uid = uid;
//		this.groupID = groupID;
//		this.group = group;
//		this.users = users;
//	}
//	
//	
//	@Override
//	public void run() {
//		IUserService userService = UserServiceImpl.getInstance();
//		List<User> userList = new ArrayList<User>();
//		for(Long u:users){
//			if(u!=null){
//				User user = userService.getUserbyid(u);
//				if(user!=null){
//					user.setPwd(null);
//					user.setToken(null);
//					List<GroupChat> ids = user.getGroupids();
//					if(ids==null){
//						ids = new ArrayList<GroupChat>();
//					}
//					GroupChat g = new GroupChat();
//					g.setGroupid(groupID);
//					g.setGroupName(group.getGroupName());
//					g.setVer("1");
//					ids.add(g);
//					user.setGroupids(ids);
//					userList.add(user);
//					userService.addGroup(user.getUid(), user.getGroupids());//将群添加到用户中 
//				}
//			}
//		}
//		group.setUserlist(userList);
//		IGroupServiceImpl groupService = GroupServiceImpl.getInstance();
//		groupService.saveGroup(group);
//		logger.info("create group end id:"+groupID);
//	}
//	
// }
