package com.zte.im.service;

import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;
import com.zte.im.bean.MyPageBean;
import com.zte.im.bean.TAttention;
import com.zte.im.bean.TComment;
import com.zte.im.bean.TGroup;
import com.zte.im.bean.TMention;
import com.zte.im.bean.TSupport;
import com.zte.im.bean.TSysMessage;
import com.zte.im.bean.TTGroup;
import com.zte.im.bean.TToken;
import com.zte.im.bean.TTopic;
import com.zte.im.bean.TTransUser;
import com.zte.im.bean.TTwitter;
import com.zte.im.bean.User;
import com.zte.im.util.Page;

public interface ITwitterService {
	/**
	 * 根据人员id查询圈子
	 * 
	 * @param userId
	 * @return
	 */
	public List<TGroup> FindGroupByUserId(Long userId, Long createTime);
	
	/**
	 * 根据人员id查询圈子
	 * 
	 * @param userId
	 * @return
	 */
	public List<TGroup> FindGroupByUserId(Long userId, Page page);

	/**
	 * 收藏微博
	 * 
	 * @param userId
	 * @param twitterId
	 */
	public void collectTwitter(Map<String, Object> param, String type);

	/**
	 * 转发微博
	 * 
	 * @param param
	 */
	public void transTwitter(Map<String, Object> param);

	/**
	 * 点赞微博
	 * 
	 * @param param
	 */
	public void supportTwitter(Map<String, Object> param);

	/**
	 * 获取微博详情
	 * 
	 * @param groupId
	 * @return
	 */
	public TGroup findGroupDetail(Long groupId);

	/**
	 * 获取用户的所有微博信息
	 * 
	 * @param userId
	 * @return
	 */
	List<TTwitter> findTwittersByUserId(Long userId);

	/**
	 * 查询微博详情
	 * 
	 * @param groupId
	 * @return
	 */
	public List<User> findUsersByGroupId(Long groupId);

	/**
	 * 删除微博
	 * 
	 * @param twitterId
	 */
	public void delTwitter(Long twitterId);

	/**
	 * 删除圈子
	 * 
	 * @param groupId
	 */
	public void delTwitterGroup(Long groupId);

	/**
	 * 删除圈子成员
	 * 
	 * @param groupId
	 * @param userId
	 */
	public void removeGroupUser(Long groupId, Long userId);

	/**
	 * 圈子邀请成员
	 * 
	 * @param param
	 */
	public void inviteGroupUser(Map<String, Object> param);

	/**
	 * 获取某用户发布的微博的点赞信息
	 * 
	 * @param tIdArr
	 * @param page
	 * @return
	 */
	List<TComment> getCommentsByTid(Long[] tIdArr, MyPageBean pageBean);

	/**
	 * 获取微博详情
	 * 
	 * @param twitterId
	 * @return
	 */
	public TTwitter findTwitter(Long twitterId);
	
	/**
	 * 获取微博详情
	 * 
	 * @param twitterId
	 * @return
	 */
	public TTwitter findTwitterDetail(Long twitterId, Long userId);

	/**
	 * 添加圈子
	 * 
	 * @param param
	 */
	public void addTwitterGroup(Map<String, Object> param);

	/**
	 * 获取新消息
	 * 
	 * @param userId
	 * @return
	 */
	List<TSysMessage> findMsgByUserId(Long userId);

	/**
	 * 处理系统消息
	 * 
	 * @param messageId
	 * @param operType
	 */
	void operSysMsg(Long messageId, String operType, Long userId);

	/**
	 * 根据消息Id获取系统消息
	 * 
	 * @param messageId
	 * @return
	 */
	TSysMessage getSysMessage(Long messageId);

	/**
	 * 获取某用户微博的点赞信息
	 * 
	 * @param tIdArr
	 * @param page
	 * @return
	 */
	List<TSupport> getSupportsByTid(Long[] tIdArr, MyPageBean pageBean);

	/**
	 * 获取@我的信息
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	List<TMention> getMentionByUserId(Long userId, MyPageBean pageBean);

	/**
	 * 根据id查询评论
	 * 
	 * @param commentId
	 * @return
	 */
	TComment findCommentDetail(Long commentId);

	/**
	 * 根据id查询赞信息
	 * 
	 * @param supportId
	 * @return
	 */
	TSupport findSupportDetail(Long supportId);

	/**
	 * 评论微博
	 */
	void commentTwitter(Map<String, Object> param);

	/**
	 * 获取微博列表
	 * 
	 * @param uIdArr
	 * @param page
	 * @return
	 */
	List<TTwitter> getTwitters(Long useId, Long[] uIdArr, MyPageBean pageBean);

	/**
	 * 获取微博列表
	 * 
	 * @param uIdArr
	 * @param pageBean
	 * @return
	 */
	List<TTwitter> getTwittersNew(Long useId, Long[] uIdArr, MyPageBean pageBean);

	/**
	 * 首页微博列表
	 * 
	 * @param uIdArr
	 * @param pageBean
	 * @return
	 */
	List<TTwitter> getTwittersIdx(Long userId, Long[] uIdArr, Long[] twitterids,
			MyPageBean pageBean);

	/**
	 * 首页微博列表2
	 * 
	 * @param uIdArr
	 * @param pageBean
	 * @return
	 */
	List<TTwitter> getTwittersIdx2(Long userId, Long[] uIdArr, Long[] twitterids,
			MyPageBean pageBean);
	
	/**
	 * 获取user关注的用户ID
	 * 
	 * @param userId
	 * @param attentionType
	 * @return
	 */
	List<Long> getAttentionUids(Long userId, String attentionType);

	/**
	 * 查询所属圈子ID
	 * 
	 * @param userId
	 * @return
	 */
	List<Long> findGroupIdsByUid(Long userId);

	/**
	 * 查询群组中的所有用户Id
	 * 
	 * @param gIdArr
	 * @return
	 */
	List<Long> getUidsByGroupIds(Long[] gIdArr);

	/**
	 * 取消加好友关注
	 * 
	 * @param param
	 */
	public void RemoveAttentionUid(Map<String, Object> param);

	/**
	 * 添加加好友关注
	 * 
	 * @param param
	 */
	public void AddAttentionUid(Map<String, Object> param);

	/**
	 * 通过id查询 用户是否被关注
	 * 
	 * @param uid
	 * @param attentionId
	 * @return
	 */
	TAttention FindAttentionByUid(Long uid, Long attentionId);

	/**
	 * 更新关注对象
	 * 
	 * @param uid
	 */
	public void UpdateAttentionByUid(TAttention attention);

	/**
	 * 更新圈子简介
	 * 
	 * @param gid
	 */
	public void UpdateGroupIntroduction(TGroup group);

	/**
	 * 获取此微博的所有转发用户ID
	 * 
	 * @param twitterId
	 * @return
	 */
	public List<TTransUser> GettransTwitterUId(Long twitterId);

	/**
	 * 获取单个微博所有评论
	 * 
	 * @param twitterId
	 * @param commentType
	 * @return
	 */
	public List<TComment> GetCommentsList(Long twitterId);

	/**
	 * 获取单个微博所有评论
	 * 
	 * @param twitterId
	 * @param commentType
	 * @return
	 */
	public List<TSupport> GetSupportsList(Long twitterId);

	/**
	 * 添加新话题
	 * 
	 * @param param
	 */
	public void AddNewTopic(Map<String, Object> param);

	/**
	 * 通过内容查询话题
	 * 
	 * @param topicName
	 * @return
	 */
	public TTopic FindTopicByTopicName(String topicName);

	/**
	 * 获取话题列表
	 * 
	 * @param param
	 * @param page
	 * @return
	 */
	public List<TTopic> getTopicList(String topicName, MyPageBean page);

	/**
	 * 添加新动态
	 * 
	 * @param param
	 */
	public void SaveNewTwitter(Map<String, Object> param);

	/**
	 * 根据关键字获取微博列表
	 * 
	 * @return
	 */
	List<TTwitter> getTwittersByKw(Long useId, String kw, String twitterType, MyPageBean pageBean);
	/**
	 * 根据关键字获取微博列表
	 * 
	 * @return
	 */
	List<TTwitter> getTwittersByKw2(Long useId, String kw, String twitterType, MyPageBean pageBean);

	/**
	 * 根据关键字获取话题列表
	 * 
	 * @param kw
	 * @param page
	 * @return
	 */
	List<TTopic> getTopicsByKw(String kw, MyPageBean pageBean);
	/**
	 * 根据关键字获取话题列表
	 * 
	 * @param kw
	 * @param page
	 * @return
	 */
	List<TTopic> getTopicsByKw2(String kw, MyPageBean pageBean);
	/**
	 * 根据关键字获取相关人员
	 * 
	 * @param kw
	 * @param page
	 * @return
	 */
	List<User> getUsersByKw(Long userId, String kw, MyPageBean pageBean);

	/**
	 * 根据关键字获取相关圈子
	 * 
	 * @param kw
	 * @param page
	 * @return
	 */
	List<TGroup> getGroupsByKw(Long userId, String kw, MyPageBean pageBean);

	/**
	 * 添加@ 到我的关联信息
	 * 
	 * @param params
	 */
	public void addMentionInfo(Map<String, Object> params);

	/**
	 * dec 更新微博操作统计数
	 * 
	 * @param twitterId
	 * @param typeOper
	 *            点赞、评论、转发、收藏
	 * @param numValue
	 */
	public void updateTwitterNum(Long twitterId, String typeOper, Long numValue);

	/**
	 * 添加微博圈子对照
	 * 
	 * @param params
	 */
	public void addTwitterGroupId(List<Map<String, Object>> params);

	/**
	 * 添加微博同事对照
	 * 
	 * @param params
	 */
	public void addTwitterStaffId(List<Map<String, Object>> params);
	
	/**
	 * 添加微博部门对照
	 * 
	 * @param params
	 */
	public void addTwitterDeptId(List<Map<String, Object>> params);

	/**
	 * 通过uid获取最新微博id
	 * 
	 * @param uId
	 * @return
	 */
	public Long findNewTwitterIdByUserID(Long uId);

	/**
	 * dec获取组织机构
	 * 
	 * @param gid
	 * @return
	 */
	public List<DBObject> getOrgan(Long gid);

	/**
	 * 获取系统微博
	 * 
	 * @param userId
	 * @return
	 */
	TTwitter getSysTwitter(Long userId);

	/**
	 * 获取分享到该圈子的微博
	 * 
	 * @param groupId
	 * @return
	 */
	List<TTGroup> findTGroup(Long groupId, MyPageBean pageBean);

	/**
	 * 添加反馈信息
	 * 
	 * @param param
	 */
	public void addFeedBack(Map<String, Object> param);

	/**
	 * 获取收藏微博列表
	 * 
	 * @param userId
	 * @param pageBean
	 * @return
	 */
	public List<TTwitter> getCollects(Long userId, MyPageBean pageBean);

	/**
	 * 根据人员id关注信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<TAttention> getAttentsByUserId(Long userId);

	/**
	 * 保存系统消息
	 * 
	 * @param param
	 */
	public void addSysMessage(Map<String, Object> param);

	/**
	 * 获取关注详情
	 * 
	 * @param userId
	 *            关注用户的id
	 * @param operUserId
	 *            操作用户的id
	 * @return
	 */
	public TAttention getAttention(Long userId, Long operUserId);

	/**
	 * 添加@信息
	 * 
	 * @param param
	 */
	public void addMention(Map<String, Object> param);

	/**
	 * 添加token信息
	 * 
	 * @param token
	 */
	public void addToken(String token);

	/**
	 * 删除token信息
	 * 
	 * @param tokenId
	 */
	public void delToken(Long tokenId);

	/**
	 * 获取所有token信息
	 * 
	 * @return
	 */
	public List<TToken> getAllToken();

	/**
	 * 记录用户日志
	 */
	public void saveUserLog(User user, String oper, String content);

	/**
	 * 取消点赞
	 * @param userId
	 * @param twitterId
	 */
	public void cancelSupport(Long userId, Long twitterId);
	
	/**
	 * 获取部门下微博id
	 * @param deptId
	 * @return
	 */
	public List<Long> getTwitterIdByDept(Long deptId);
	
	/**
	 * 获取圈子下下微博id
	 * @param deptId
	 * @return
	 */
	public List<Long> getTwitterIdByGroups(Long[] groupId);
	
	/**
	 * 获取邀请人下发的微博id
	 * @param userId
	 * @return
	 */
	public List<Long> getTwitterIdByStaff(Long userId);
	
	/**
	 * dec根据人员id获取所在圈子id
	 * @param userId
	 * @return
	 */
	public List<Long> getGroupIdByUserId(Long userId);
	
	/**
	 * 保存安全日志.
	 * @param user
	 * @param oper
	 * @param type
	 */
	void saveSecureLog(User user, String oper, String type);
	
	/**
	 * 获取某用户被评论信息
	 * @param becomentUserId
	 * @param pageBean
	 * @return
	 */
	public List<TComment> findUserComments(Long becomentUserId, MyPageBean pageBean);

	/**
	 * 查询是否发送过邀请或申请的消息未被处理
	 * @param sendId 发送人
	 * @param receiveId 接收人
	 * @param groupId 圈子id
	 * @return
	 */
	public TSysMessage getSysMessage(TSysMessage sysMsg) ;

	/**
	 * 查看点赞记录
	 * @param userId
	 * @param twitterId
	 */
	public TSupport getSupport(Long userId, Long twitterId);
	
	/**
	 * dec 获取系统微博列表
	 * @param userId
	 * @return
	 */
	public List<TTwitter> getSysTwitters(Long userId);
	
}
