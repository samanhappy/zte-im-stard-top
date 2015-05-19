package com.zte.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zte.databinder.MemberBinder;
import com.zte.databinder.UserBinder;
import com.zte.databinder.UserPermBinder;
import com.zte.im.bean.User;
import com.zte.im.mybatis.bean.UcGroup;
import com.zte.im.mybatis.bean.UcGroupExample;
import com.zte.im.mybatis.bean.UcGroupMember;
import com.zte.im.mybatis.bean.UcGroupMemberExample;
import com.zte.im.mybatis.bean.UcMember;
import com.zte.im.mybatis.bean.UcMemberExample;
import com.zte.im.mybatis.bean.my.UcGroupRoleMy;
import com.zte.im.mybatis.bean.my.UcMemberMy;
import com.zte.im.mybatis.bean.my.UcTenantMy;
import com.zte.im.mybatis.bean.page.MemberPageModel;
import com.zte.im.mybatis.mapper.UcGroupMapper;
import com.zte.im.mybatis.mapper.UcGroupMemberMapper;
import com.zte.im.mybatis.mapper.UcMemberMapper;
import com.zte.im.mybatis.mapper.my.UcMemberMapperMy;
import com.zte.im.mybatis.mapper.my.UcTenantMapperMy;
import com.zte.im.service.IUserService;
import com.zte.im.service.IVersionService;
import com.zte.im.service.impl.UserServiceImpl;
import com.zte.im.service.impl.VersionServiceImpl;
import com.zte.im.util.Constant;
import com.zte.im.util.EncryptionDecryption;
import com.zte.im.util.ImDataCompare;
import com.zte.im.util.UUIDGenerator;
import com.zte.service.IGroupMemberService;
import com.zte.util.system.PinYinUtil;

@Service
public class GroupMemberServiceImpl implements IGroupMemberService {

	private Logger logger = LoggerFactory.getLogger(GroupMemberServiceImpl.class);

	@Autowired
	private UcGroupMapper groupMapper;

	@Autowired
	private UcMemberMapperMy memberMapperMy;

	@Autowired
	private UcMemberMapper memberMapper;

	@Autowired
	private UcTenantMapperMy tenantMapperMy;

	@Autowired
	private UcGroupMemberMapper groupMemberMapper;

	IUserService userService = UserServiceImpl.getInstance();

	IVersionService verService = VersionServiceImpl.getInstance();

	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public List<UcGroup> getGroupByTenantId(String tenantId, String typeId) {
		UcGroupExample example = new UcGroupExample();
		example.createCriteria().andTenantIdEqualTo(tenantId).andTypeIdEqualTo(typeId);
		return groupMapper.selectByExample(example);
	}

	@Override
	public List<UcGroup> getGroupByName(String tenantId, String name, String typeId) {
		UcGroupExample example = new UcGroupExample();
		example.createCriteria().andTenantIdEqualTo(tenantId).andTypeIdEqualTo(typeId).andNameEqualTo(name);
		return groupMapper.selectByExample(example);
	}

	@Override
	public MemberPageModel getMemberForGroup(MemberBinder mb) {
		if (mb != null && mb.getTenantId() != null) {
			// 查询fullId
			UcGroup group = groupMapper.selectByPrimaryKey(mb.getGroupId());
			mb.setFullId(group == null ? null : group.getFullId());

			int totalRecord = memberMapperMy.getMemberCount4Group(mb);
			MemberPageModel pm = MemberPageModel.newPageModel(mb.getpSize(), mb.getcPage(), totalRecord);
			mb.setOffset(pm.getOffset());
			List<UcMemberMy> memberList = memberMapperMy.getMember4Group(mb);

			// 查询公司名称
			UcTenantMy tenant = tenantMapperMy.selectTenant(mb.getTenantId());

			// 查询职位
			for (UcMemberMy member : memberList) {
				getRole4Member(member, tenant);
			}
			pm.setMemberList(memberList);
			return pm;
		}
		return null;
	}
	
	@Override
	public long getMemberCountForGroup(MemberBinder mb) {
		if (mb != null && mb.getTenantId() != null) {
			// 查询fullId
			UcGroup group = groupMapper.selectByPrimaryKey(mb.getGroupId());
			mb.setFullId(group == null ? null : group.getFullId());
			long totalRecord = memberMapperMy.getMemberCount4Group(mb);
			return totalRecord;
		}
		return 0;
	}

	@Override
	public UcMemberMy getUcMemberById(String id, String tenantId) {
		UcMemberMy member = memberMapperMy.getUcMemberById(id);
		// 查询职位
		if (member != null) {
			// 查询公司名称
			UcTenantMy tenant = tenantMapperMy.selectTenant(tenantId);
			getRole4Member(member, tenant);
			return member;
		}
		return null;
	}

	@Override
	public String getOrgGroupJsonByTenantId(String tenantId) {
		return null;
	}

	@Override
	public String getOrgGroupJsonByTenantId() {
		return null;
	}

	@Override
	public void getRole4Member(UcMemberMy member, UcTenantMy tenant) {
		StringBuffer roleNames = new StringBuffer();
		StringBuffer roleIds = new StringBuffer();
		StringBuffer roleRelationIds = new StringBuffer();
		List<UcGroupRoleMy> roleList = memberMapperMy.getRole4Member(member.getId());
		if (roleList != null && roleList.size() > 0) {
			roleNames.append(roleList.get(0).getName());
			roleIds.append(roleList.get(0).getId());
			roleRelationIds.append(roleList.get(0).getRoleRelationId());
			for (int i = 1; i < roleList.size(); i++) {
				roleNames.append(",").append(roleList.get(i).getName());
				roleIds.append(",").append(roleList.get(i).getId());
				roleRelationIds.append(",").append(roleList.get(i).getRoleRelationId());
			}
		}
		member.setRoleId(roleIds.toString());
		member.setRoleName(roleNames.toString());
		member.setRoleRelationId(roleRelationIds.toString());

		if (member.getDeptId() == null && tenant != null) {
			member.setDeptId(tenant.getId());
			member.setDeptName(tenant.getName());
		}
	}

	@Override
	public String saveOrUpdateUser(UserBinder user) {
		return saveOrUpdateUser(user, false);
	}

	@Override
	public String saveOrUpdateUser(UserBinder user, boolean isImport) {

		UcMember member = null;
		
	    //用户接受用户数据判断结果,fasle更新数据库,true不更新数据库(yejun)
        boolean result = true;
        
		if (user.getId() != null && !"".equals(user.getId())) {

			// 根据工号判断用户是否存在
			UcMemberExample ue = new UcMemberExample();
			ue.createCriteria().andUidEqualTo(user.getUid()).andIdNotEqualTo(user.getId());
			List<UcMember> memberList = memberMapper.selectByExample(ue);
			if (memberList != null && memberList.size() > 0) {
				return "工号已存在";
			}

			// 更新
			member = memberMapper.selectByPrimaryKey(user.getId());
			if (member != null) {
				member.setId(user.getId());
				try {
					member.setBirthday(StringUtils.isNotBlank(user.getBirthday()) ? df.parse(user.getBirthday()) : null);
				} catch (ParseException e) {
					logger.error("", e);
				}
				member.setCn(user.getCn());
				// 处理汉字拼音
				member.setPinyin(PinYinUtil.getPinyinStr(user.getCn()));
				member.setT9Pinyin(PinYinUtil.getT9PinyinStr(user.getCn()));
				member.setCreateTime(new Date());
				member.setCreator("");// 创建者
				member.setEditable("true");
				member.setDeletable("true");
				member.setMail(user.getMail());
				member.setMobile(user.getMobile());
				member.setNickname(user.getNickname());
				member.setPassword(user.getPassword());
				member.setPhoto(user.getPhotoUrl());
				member.setPhotoSign(user.getPhotoSign());
				member.setPostaladdress(user.getAddress());
				member.setQq(user.getQq());
				member.setSequ(user.getSequ() != null ? user.getSequ() : 0L);
				member.setSex(user.getSex());
				member.setShortcode(user.getShortCode());
				member.setState("1");
				member.setTenantId(user.getTenantId());
				member.setUid(user.getUid());
				member.setUsable("true");
				member.setUserType("user");
				member.setWwwhomepage(user.getUrl());
				member.setHometelephone(user.getHometelephone());
				member.setFax(user.getFax());
				member.setInfo(user.getInfo());
				member.setL(user.getL());
				
				member.setCustomField1(user.getHobby());
				member.setCustomField2(user.getWeibo());
				member.setCustomField3(user.getWeixin());
				
				
			    //判断MDM用户数据与postgres用户数据是否一致(yejun)
				UcMember member2 = memberMapper.selectByPrimaryKey(user.getId());
				result = ImDataCompare.compare(member, member2);
				
				if ((memberMapper.updateByPrimaryKey(member) == 1)) {
					logger.info("update member success for:" + member.getCn());
				} else {
					return "保存数据失败";
				}
		
//				if (!result) {
//				    memberMapper.updateByPrimaryKey(member);
//				    logger.info("update member success for:"+ member.getCn());
//				}

				if ((user.getDeptRelationId() == null || user.getDeptRelationId().equals(""))
						&& (!"".endsWith(user.getDeptId()))) {
					// 新加部门属性
					UcGroupMember deptGroupMember = new UcGroupMember();
					deptGroupMember.setId(UUIDGenerator.randomUUID());
					deptGroupMember.setTenantId(user.getTenantId());
					deptGroupMember.setMemberId(user.getId());
					deptGroupMember.setGroupId(user.getDeptId());
					if (groupMemberMapper.insert(deptGroupMember) == 1) {
					    //数据有更新,将result记录为fasle(yejun)
					    result = false;
						logger.info("update dept group member success for:" + member.getCn());
					} else {
						return "保存数据失败";
					}
				} else {
					UcGroupMember deptGroupMember = groupMemberMapper.selectByPrimaryKey(user.getDeptRelationId());
					// 所属机构发生变化
					if (deptGroupMember != null && !deptGroupMember.getGroupId().equals(user.getDeptId())) {
						deptGroupMember.setId(UUIDGenerator.randomUUID());
						deptGroupMember.setTenantId(user.getTenantId());
						deptGroupMember.setMemberId(user.getId());
						deptGroupMember.setGroupId(user.getDeptId());
						if (groupMemberMapper.deleteByPrimaryKey(user.getDeptRelationId()) == 1
								&& groupMemberMapper.insert(deptGroupMember) == 1) {
							logger.info("update dept group member success for:" + member.getCn());
							
							//数据有更新,将result记录为fasle(yejun)
	                        result = false;
						} else {
							return "保存数据失败";
						}
					}
				}

				if ((user.getRoleRelationId() == null || user.getRoleRelationId().equals(""))
						&& (!"".endsWith(user.getPos()))) {
					// 新加职位属性
					UcGroupMember posGroupMember = new UcGroupMember();
					posGroupMember.setId(UUIDGenerator.randomUUID());
					posGroupMember.setTenantId(user.getTenantId());
					posGroupMember.setMemberId(user.getId());
					posGroupMember.setGroupId(user.getPos());
					if (groupMemberMapper.insert(posGroupMember) == 1) {
						logger.info("update dept group member success for:" + member.getCn());
						
						//数据有更新,将result记录为fasle(yejun)
                        result = false;
					} else {
						return "保存数据失败";
					}
				} else {
					UcGroupMember posGroupMember = groupMemberMapper.selectByPrimaryKey(user.getRoleRelationId());
					// 职位发生变化
					if (posGroupMember != null && !posGroupMember.getGroupId().equals(user.getPos())) {
						posGroupMember.setId(UUIDGenerator.randomUUID());
						posGroupMember.setTenantId(user.getTenantId());
						posGroupMember.setMemberId(user.getId());
						posGroupMember.setGroupId(user.getPos());
						if (groupMemberMapper.deleteByPrimaryKey(user.getRoleRelationId()) == 1
								&& groupMemberMapper.insert(posGroupMember) == 1) {
							logger.info("update dept group member success for:" + member.getCn());
							
							//数据有更新,将result记录为fasle(yejun)
                            result = false;
						} else {
							return "保存数据失败";
						}
					}
				}

			}

		} else {

			// 根据工号判断用户是否存在
			UcMemberExample ue = new UcMemberExample();
			ue.createCriteria().andUidEqualTo(user.getUid());
			List<UcMember> memberList = memberMapper.selectByExample(ue);
			if (memberList != null && memberList.size() > 0) {
				return "工号已存在";
			}

			member = new UcMember();
			String memberId = UUIDGenerator.randomUUID();
			member.setId(memberId);
			// 保存新增ID到user，供同步时判断用户
			user.setId(memberId);
			try {
				member.setBirthday(StringUtils.isNotBlank(user.getBirthday()) ? df.parse(user.getBirthday()) : null);
			} catch (ParseException e) {
				logger.error("", e);
			}
			member.setCn(user.getCn());
			// 处理汉字拼音
			member.setPinyin(PinYinUtil.getPinyinStr(user.getCn()));
			member.setT9Pinyin(PinYinUtil.getT9PinyinStr(user.getCn()));
			member.setCreateTime(new Date());
			member.setCreator("");// 创建者
			member.setEditable("true");
			member.setDeletable("true");
			member.setMail(user.getMail());
			member.setMobile(user.getMobile());
			member.setNickname(user.getNickname());
			member.setPassword(user.getPassword());
			member.setPhoto(user.getPhotoUrl());
			member.setPhotoSign(user.getPhotoSign());
			member.setPostaladdress(user.getAddress());
			member.setQq(user.getQq());
			member.setSequ(user.getSequ() != null ? user.getSequ() : 0L);
			member.setSex(user.getSex());
			member.setShortcode(user.getShortCode());
			member.setState("1");
			member.setTenantId(user.getTenantId());
			member.setUid(user.getUid());
			member.setUsable("true");
			member.setUserType("user");
			member.setWwwhomepage(user.getUrl());
			member.setHometelephone(user.getHometelephone());
			member.setFax(user.getFax());
			member.setInfo(user.getInfo());
			member.setL(user.getL());
			
			member.setCustomField1(user.getHobby());
			member.setCustomField2(user.getWeibo());
			member.setCustomField3(user.getWeixin());

			UcGroupMember deptGroupMember = new UcGroupMember();
			deptGroupMember.setId(UUIDGenerator.randomUUID());
			deptGroupMember.setTenantId(user.getTenantId());
			deptGroupMember.setMemberId(memberId);
			deptGroupMember.setGroupId(user.getDeptId());

			UcGroupMember posGroupMember = new UcGroupMember();
			posGroupMember.setId(UUIDGenerator.randomUUID());
			posGroupMember.setTenantId(user.getTenantId());
			posGroupMember.setMemberId(memberId);
			posGroupMember.setGroupId(user.getPos());

			if (memberMapper.insert(member) == 1) {
				logger.info("save member success for:" + member.getCn());
			} else {
				return "保存数据失败";
			}
			if (groupMemberMapper.insert(deptGroupMember) == 1) {
				logger.info("save dept group member success for:" + member.getCn());
			} else {
				return "保存数据失败";
			}
			if (groupMemberMapper.insert(posGroupMember) == 1) {
				logger.info("save pos group member success for:" + member.getCn());
			} else {
				return "保存数据失败";
			}
			
			 //新增用户 ,数据也等于更新
            result = false;

		}

		if (member != null) {
			// 更新mongodb
			userService.saveOrUpdate(member, user.getDeptId(), user.getPosDisplay(), isImport);
			// 更新用户信息列表版本
			verService.incUserListVer();
		}

		return null;

	}

	@Override
	public boolean removeUser(List<String> uids) {

		UcMemberExample mExample = new UcMemberExample();
		mExample.createCriteria().andIdIn(uids);
		memberMapper.deleteByExample(mExample);

		UcGroupMemberExample gmExample = new UcGroupMemberExample();
		gmExample.createCriteria().andMemberIdIn(uids);
		groupMemberMapper.deleteByExample(gmExample);

		// 更新mongodb
		userService.removeUserBySqlId(uids);
		// 更新用户信息列表版本
		verService.incUserListVer();

		return true;
	}

	@Override
	public boolean updateUserState(String uid, String usable) {
		if (uid != null && !"".equals(uid) && usable != null && !"".equals(usable)) {
			// 更新
			UcMember member = memberMapper.selectByPrimaryKey(uid);
			if (member != null) {
				member.setUsable(usable);
				memberMapper.updateByPrimaryKey(member);
				
			}
			
			User user = userService.getUserbySqlId(uid);
			if (user != null) {
				user.setUsable("true".equals(usable));
				userService.updateUser(user);
			}
		}
		return true;
	}

	@Override
	public boolean resetUserPwd(String uid) {
		UcMember member = memberMapper.selectByPrimaryKey(uid);
		User user = userService.getUserbySqlId(uid);
		logger.info(member.toString());
		logger.info(user.toString());
		if (member != null && user != null) {
			member.setPassword(Constant.DEFAULT_PWD);
			memberMapper.updateByPrimaryKey(member);

			try {
				user.setPwd(new EncryptionDecryption().encrypt(Constant.DEFAULT_PWD));
			} catch (Exception e) {
				logger.error("", e);
				return false;
			}
			userService.updateUser(user);
			return true;
		}
		return false;
	}

	@Override
	public List<UcMemberMy> getAllUserListByTenantId(UserPermBinder user) {
		if (user.getTenantId() != null && !"".equals(user.getTenantId())) {
			return memberMapperMy.getAllUserListByTenantId(user);
		}
		return null;
	}

	@Override
	public UcGroup getGroupByTenantId(String tenantId, String typeId, String name) {
		UcGroupExample example = new UcGroupExample();
		example.createCriteria().andTenantIdEqualTo(tenantId).andTypeIdEqualTo(typeId).andNameEqualTo(name);
		List<UcGroup> list = groupMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
