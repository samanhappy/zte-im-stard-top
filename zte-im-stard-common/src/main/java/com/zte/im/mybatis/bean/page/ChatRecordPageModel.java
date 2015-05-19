package com.zte.im.mybatis.bean.page;

import java.util.List;

import com.zte.im.bean.ChatRecord;

public class ChatRecordPageModel extends PageModel {
	
	private List<ChatRecord> recordList;// 分页数据
	
	private ChatRecordPageModel(final int pageSize, final int currentPage,
			final int totalRecord) {
		this.pageSize = pageSize;
		this.totalRecord = totalRecord;
		setTotalPage();
		setCurrentPage(currentPage);
	}

	public static ChatRecordPageModel newPageModel(final int pageSize,
			final int currentPage, final int totalRecord) {
		return new ChatRecordPageModel(pageSize, currentPage, totalRecord);
	}
	
	public List<ChatRecord> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<ChatRecord> recordList) {
		this.recordList = recordList;
	}

}
