package com.zte.im.mybatis.bean.page;

import java.util.List;

import com.mongodb.DBObject;

public class FeedbackPageModel extends PageModel {

	private List<DBObject> feedbackList;// 分页数据

	private FeedbackPageModel() {

	}

	private FeedbackPageModel(final int pageSize, final int currentPage,
			final int totalRecord) {
		this.pageSize = pageSize;
		this.totalRecord = totalRecord;
		setTotalPage();
		setCurrentPage(currentPage);
	}

	public static FeedbackPageModel newPageModel(final int pageSize,
			final int currentPage, final int totalRecord) {
		return new FeedbackPageModel(pageSize, currentPage, totalRecord);
	}

	public List<DBObject> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<DBObject> feedbackList) {
		this.feedbackList = feedbackList;
	}

}
