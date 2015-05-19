package com.zte.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zte.databinder.MemberBinder;
import com.zte.im.mybatis.bean.UcGroupMember;
import com.zte.im.mybatis.bean.UcMember;
import com.zte.im.mybatis.bean.my.UcMemberMy;
import com.zte.im.mybatis.bean.page.MemberPageModel;
import com.zte.im.mybatis.mapper.my.UcMemberMapperMy;
import com.zte.im.util.Constant;

public class GroupMemberServiceTest extends SpringTestBase {

	private static Logger logger = LoggerFactory
			.getLogger(GroupMemberServiceTest.class);

	@Autowired
	private IGroupMemberService memberService;
	
	@Autowired
	private UcMemberMapperMy memberMapperMy;

	@Autowired
	private IGroupService groupService;
	
	@Test
	public void testGetMemberById() {
		UcMember member =  memberMapperMy.getUcMemberById("a726252584144f1fbe602ac4c01a1f37");
		System.out.println(member.getCustomField1());
	}

	@Test
	public void testGetMemberForGroup() {
		MemberBinder mb = new MemberBinder();
		mb.setTenantId("1");
		mb.setGroupId("1");
		mb.setpSize(10);
		mb.setcPage(0);
		mb.setSortStr("uid asc");
		MemberPageModel pm = memberService.getMemberForGroup(mb);
		List<UcMemberMy> memberList = pm.getMemberList();
		for (UcMemberMy member : memberList) {
			logger.info(member.getCn() + "," + member.getDeptName() + ","
					+ member.getRoleName());
		}

		mb.setSortStr("uid desc");
		pm = memberService.getMemberForGroup(mb);
		memberList = pm.getMemberList();
		for (UcMemberMy member : memberList) {
			logger.info(member.getCn() + "," + member.getDeptName() + ","
					+ member.getRoleName());
		}

		mb.setSortStr("cn asc");
		pm = memberService.getMemberForGroup(mb);
		memberList = pm.getMemberList();
		for (UcMemberMy member : memberList) {
			logger.info(member.getCn() + "," + member.getDeptName() + ","
					+ member.getRoleName());
		}

		mb.setSortStr("cn desc");
		pm = memberService.getMemberForGroup(mb);
		memberList = pm.getMemberList();
		for (UcMemberMy member : memberList) {
			logger.info(member.getCn() + "," + member.getDeptName() + ","
					+ member.getRoleName());
		}

		Assert.assertTrue(memberList.size() > 0);
		Assert.assertTrue(memberList.get(0).getUid() != null);
		Assert.assertTrue(memberList.get(0).getDeptName() != null);
		Assert.assertTrue(memberList.get(0).getDeptId() != null);
		Assert.assertTrue(memberList.get(0).getRoleName() != null);

		mb.setGroupId(null);
		mb.setpSize(5);
		mb.setcPage(1);
		pm = memberService.getMemberForGroup(mb);
		memberList = pm.getMemberList();
		for (UcMemberMy member : memberList) {
			logger.info(member.getCn() + "," + member.getDeptName() + ","
					+ member.getRoleName());
		}
		Assert.assertTrue(memberList.size() > 0);
		Assert.assertTrue(memberList.get(0).getUid() != null);
		Assert.assertTrue(memberList.get(0).getDeptName() != null);
	}

	@Test
	public void testGetPageMemberForGroup() {
		MemberBinder mb = new MemberBinder();
		mb.setTenantId("1");
		mb.setGroupId(null);
		mb.setpSize(3);
		mb.setcPage(1);
		MemberPageModel pm = memberService.getMemberForGroup(mb);
		logger.info("TotalRecord:" + pm.getTotalRecord() + ",PageSize:"
				+ pm.getPageSize() + ",TotalPage:" + pm.getTotalPage()
				+ ",CurrentPage:" + pm.getCurrentPage());
		List<UcMemberMy> memberList = pm.getMemberList();
		for (UcMemberMy member : memberList) {
			logger.info(member.getCn() + "," + member.getDeptName() + ","
					+ member.getRoleName());
		}

		mb.setcPage(2);
		pm = memberService.getMemberForGroup(mb);
		logger.info("TotalRecord:" + pm.getTotalRecord() + ",PageSize:"
				+ pm.getPageSize() + ",TotalPage:" + pm.getTotalPage()
				+ ",CurrentPage:" + pm.getCurrentPage());
		memberList = pm.getMemberList();
		for (UcMemberMy member : memberList) {
			logger.info(member.getCn() + "," + member.getDeptName() + ","
					+ member.getRoleName());
		}
	}

	@Test
	public void testGetRole4Member() {
		UcMemberMy member = new UcMemberMy();
		member.setId("1");
		// memberService.getRole4Member(member);
		logger.info(member.getRoleName());
		Assert.assertTrue(member.getRoleName() != null
				&& !member.getRoleName().equals(""));
	}

	@Test
	public void testGetGroupMember4User() {
		List<UcGroupMember> list = groupService.getGroupMember4User("c1",
				Constant.GROUP_POSITION, "e1bf556cd7414d6997b8668f3e08c1e1");
		for (UcGroupMember ugm : list) {
			logger.info(ugm.getGroupId() + "," + ugm.getMemberId());
		}
		list = groupService.getGroupMember4User("c1", Constant.GROUP_DEPT,
				"e1bf556cd7414d6997b8668f3e08c1e1");
		for (UcGroupMember ugm : list) {
			logger.info(ugm.getGroupId() + "," + ugm.getMemberId());
		}
	}

}
