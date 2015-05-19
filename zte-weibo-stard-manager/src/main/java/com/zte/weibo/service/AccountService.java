package com.zte.weibo.service;

import java.util.List;
import java.util.Map;

import com.zte.im.util.Page;
import com.zte.weibo.bean.User;
import com.zte.weibo.common.constant.AttentionConstant.AttentionType;
import com.zte.weibo.protocol.ResponseMain;

public interface AccountService {

	/**
	 * 查询账号列表
	 * @author syq	2015年1月15日
	 * @param user 查询条件
	 * @param page 分页
	 * @return 符合查询条件的账号信息
	 */
	public List<User> getList(User user,Page page);
	
	/**
	 * 查询指定部门对应的用户
	 * @author syq	2015年2月2日
	 * @param cid	为空时，查询全部
	 * @return
	 * @throws Exception
	 */
	public List<User> getListByCid(Long cid) throws Exception;
	
	/**
	 * 新增一个账号
	 * 对Uid、loginname是否重复进行了判断
	 * @author syq	2015年1月15日
	 * @param user	name,loginname 为必须的
	 */
	public void addUser(User user ) throws Exception;
	
	/**
	 * 修改用户信息（通过loginname判断）
	 * @author syq	2015年1月19日
	 * @param user
	 * @throws Exception
	 */
	public void update(User user) throws Exception;
	
	/**
	 * 新增或者修改指定的用户（判断是否新增的依据是根据loginname判断的）
	 * @author syq	2015年1月19日
	 * @param user
	 * @throws Exception
	 */
	public void addOrUpdate(User user) throws Exception;
	
	
	/**
	 * 查询指定uid对应的用户信息
	 * @author syq	2015年1月15日
	 * @param uid	账号ID，为空时返回null
	 * @return 符合条件的账号信息
	 */
	public List<User> getByUid(Long[] uid) throws Exception;
	/**
	 * 
	 * @author syq	2015年1月24日
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public Map<Long, User> getByUidForMap(Long[] uid) throws Exception;
	/**
	 * 查询指定uid对应的用户信息
	 * @author syq	2015年1月15日
	 * @param uid	账号ID，为空时返回null
	 * @return 符合条件的账号信息
	 */
	public User getByUid(Long uid);
	
	/**
	 * 批量修改指定uid对应的账号状态
	 * @author syq	2015年1月15日
	 * @param uids 要修改的UID数组
	 * @param states 对应的账号状态
	 * @param stopDesc 停用时的说明
	 * @param main	存放返回结果的信息等，若为空时会自动新建一个返回
	 * @return 修改结果，通过message 可查看修改不成功时的原因
	 */
	public ResponseMain modifyUserState(Long[] uids, String states,String stopDesc,ResponseMain main);

	/**
	 * 导出指定的账号信息到excel中
	 * @author syq	2015年1月15日
	 * @param uid 账号ID数组，为空时导出全部
	 * @return 
	 */
	public String export(Long[] uid) throws Exception;

	
	/**
	 * 查询分享数
	 * @author syq	2015年1月17日
	 * @param uid	账号ID，为空时忽略该条件
	 * @param twitterType 分享类型：（01、原创；02、转发）	为空时忽略该条件
	 * @return
	 * @throws Exception
	 */
	public long getWBSByTwitterType(Long uid,String twitterType) throws Exception;

	/**
	 * 分组查询分享数<br>
	 * select userid,count(1) as num from t_twitter where userid in (?) and twitterType = ?  group by userid
	 * @author syq	2015年1月30日
	 * @param uid	账号ID，为空时忽略该条件，查询全部
	 * @param twitterType 分享类型：（01、原创；02、转发）	为空时忽略该条件
	 * @return 以用户ID为key，对应评论次数为value的map
	 * @throws Exception
	 */
	public Map<Long, Long> getWBSByTwitterType(Long[] uid, String twitterType) throws Exception;
	/**
	 * 查询原创分享数
	 * @author syq	2015年1月17日
	 * @param uid	为空时查询全部
	 * @return
	 * @throws Exception
	 */
	public long getYCWBS(Long uid) throws Exception;
	
	/**
	 * 查询原创分享数
	 * @author syq 2015年1月30日
	 * @param uid	为空时查询全部
	 * @return
	 * @throws Exception
	 */
	public Map<Long, Long> getYCWBS(Long[] uid) throws Exception;
	/**
	 * 查询转发分享数
	 * @author syq	2015年1月17日
	 * @param uid	账号ID，为空时查询全部
	 * @return
	 * @throws Exception
	 */
	public long getZFWFB(Long uid) throws Exception;

	/**
	 * 查询转发分享数
	 * @author syq	2015年1月30日
	 * @param uid	账号ID，为空时查询全部
	 * @return
	 * @throws Exception
	 */
	public Map<Long, Long> getZFWFB(Long[] uid) throws Exception;
	/**
	 * 查询参与评论数
	 * @author syq	2015年1月17日
	 * @param uid	账号ID，为空时查询全部
	 * @return
	 * @throws Exception
	 */
	public long getCYPLS(Long uid) throws Exception;
	
	/**
	 * 分组查询参与评论数
	 * @author syq	2015年1月17日
	 * @param uid	账号ID，为空时查询全部
	 * @return 以用户ID为key，对应评论次数为value的map
	 * @throws Exception
	 */
	public Map<Long, Long> getCYPLS(Long[] uids) throws Exception;

	/**
	 * 查询创建圈子数
	 * @author syq	2015年1月17日
	 * @param uid	账号ID，为空时查询全部
	 * @return
	 * @throws Exception
	 */
	public long getCJQZS(Long uid) throws Exception;
	/**
	 * 查询创建圈子数
	 * @author syq	2015年1月30日
	 * @param uid	账号ID，为空时查询全部
	 * @return
	 * @throws Exception
	 */
	public Map<Long, Long> getCJQZS(Long[] uid) throws Exception;

	/**
	 * 查询参与圈子数
	 * @author syq	2015年1月17日
	 * @param uid	账号ID，为空时查询全部
	 * @return
	 * @throws Exception
	 */
	public long getCYQZS(Long uid) throws Exception;
	
	/**
	 * 查询参与圈子数
	 * @author syq	2015年1月30日
	 * @param uid	账号ID，为空时查询全部
	 * @return
	 * @throws Exception
	 */
	public Map<Long, Long> getCYQZS(Long[] uid) throws Exception;
	
	/**
	 * 查询指定用户ID，评论类型对应的评论次数
	 * @author syq	2015年1月17日
	 * @param uid	用户ID，为空时，忽略该条件
	 * @param commentType	评论类型：（01、评论分享；02、回复评论；03、回复赞），为空时，忽略该条件
	 * @return 
	 * @throws Exception
	 */
	public long getCommentCountByCommentType(Long uid,String commentType) throws Exception;
	/**
	 * 分组查询指定用户ID，评论类型对应的评论次数<br>
	 * select userid,count(1) as num from t_comment where userid in (?) and commentType = ? group by userid
	 * @author syq	2015年1月17日
	 * @param uid	用户ID，为空时，忽略该条件，查询全部
	 * @param commentType	评论类型：（01、评论分享；02、回复评论；03、回复赞），为空时，忽略该条件
	 * @return 以用户ID为key，对应评论次数为value的map
	 * @throws Exception
	 */
	public Map<Long, Long> getCommentCountByCommentType(Long[] uid,String commentType) throws Exception;
	
	/**
	 *	查询指定sname对应的数量
	 * @author syq	2015年1月22日
	 * @param sname
	 * @return
	 * @throws Exception
	 */
	public long getCountBySname(String sname) throws Exception;
	
	/**
	 * 查询指定loginname对应的个数
	 * @author syq	2015年1月22日
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	public long getCountByLoginName(String loginName) throws Exception;
	
	/**
	 * 设置指定的人员被全员关注
	 * @author syq	2015年1月22日
	 * @param uid
	 * @throws Exception
	 */
	public void setAllAttention(Long[] uids) throws Exception;
	
	/**
	 * 删除指定用户默认被全员关注
	 * @author syq	2015年2月2日
	 * @param uids
	 * @throws Exception
	 */
	public void delAllAttention(Long[] uids) throws Exception;
	
	/**
	 * 设置所有同部门下的用户相互关注
	 * @author syq	2015年2月2日
	 * @throws Exception
	 */
	public void setAttentionDept()throws Exception;
	/**
	 * 删除同部门员工互相关注
	 * @author syq	2015年2月2日
	 * @throws Exception
	 */
	public void delAttentionDept()throws Exception;
	/**
	 * 增加用户关注（UID关注attentionUid）
	 * @author syq	2015年1月22日
	 * @param uid
	 * @param attentionUid
	 * @param attentionType 关注类型：01、一般；02、特别
	 * @param attentionAttr 关注性质：01、用户关注；02、默认关注；03、部门关注
	 * @throws Exception
	 */
	public void addAttention(Long uid,Long attentionUid,AttentionType attentionType,String attentionAttr) throws Exception;
	
	/**
	 * 判断Uid是否已经关注attentionId
	 * 
	 * @author syq	2015年1月22日
	 * @param uid
	 * @param attentionId
	 * @return
	 * @throws Exception
	 */
	public boolean checkIsAttention(Long uid,Long attentionUid) throws Exception;
	
	
	public String importUserData(String fileUrl) throws Exception;

	/**
	 * 初始化企信的用户和部门数据到微信库中，在初始化之前会清空当前用户表和部门表的数据
	 * @author syq	2015年1月30日
	 * @throws Exception
	 */
	public void initData() throws Exception;
	
}
