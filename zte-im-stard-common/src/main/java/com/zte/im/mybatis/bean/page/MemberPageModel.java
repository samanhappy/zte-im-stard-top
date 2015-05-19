package com.zte.im.mybatis.bean.page;

import java.util.List;

import com.zte.im.mybatis.bean.my.UcMemberMy;

public class MemberPageModel extends PageModel {

	private List<UcMemberMy> memberList;// 分页数据

	private MemberPageModel() {

	}

	private MemberPageModel(final int pageSize, final int currentPage,
			final int totalRecord) {

		this.pageSize = pageSize;
		this.totalRecord = totalRecord;
		setTotalPage();
		setCurrentPage(currentPage);

	}

	public static MemberPageModel newPageModel(final int pageSize,
			final int currentPage, final int totalRecord) {
		return new MemberPageModel(pageSize, currentPage, totalRecord);
	}

	public List<UcMemberMy> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<UcMemberMy> memberList) {
		this.memberList = memberList;
	}

}
