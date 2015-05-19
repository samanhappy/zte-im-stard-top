package com.zte.im.mybatis.bean.page;

import java.util.List;

import com.mongodb.DBObject;

public class SysLogPageModel extends PageModel {

	private List<DBObject> logList;// 分页数据

	private SysLogPageModel() {

	}

	private SysLogPageModel(final int pageSize, final int currentPage,
			final int totalRecord) {
		this.pageSize = pageSize;
		this.totalRecord = totalRecord;
		setTotalPage();
		setCurrentPage(currentPage);
	}

	public static SysLogPageModel newPageModel(final int pageSize,
			final int currentPage, final int totalRecord) {
		return new SysLogPageModel(pageSize, currentPage, totalRecord);
	}

	public List<DBObject> getLogList() {
		return logList;
	}

	public void setLogList(List<DBObject> logList) {
		this.logList = logList;
	}

}
