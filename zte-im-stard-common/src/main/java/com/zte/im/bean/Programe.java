package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class Programe {
	private int sortTime;

	public int getSortTime() {
		return sortTime;
	}

	public void setSortTime(int sortTime) {
		this.sortTime = sortTime;
	}

	@Expose
	private Long id;
	/** 用户id */
	@Expose
	private Long userId;
	/** 日程父id */
	@Expose
	private Long mProgId;

	public Long getmProgId() {
		return mProgId;
	}

	public void setmProgId(Long mProgId) {
		this.mProgId = mProgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	// 标题
	@Expose
	private String progTitle;
	// 日程内容
	@Expose
	private String progContent;
	// 日程开始日期
	@Expose
	private String progStartDate;
	// 日程开始日期
	@Expose
	private String progEndDate;
	// 日程开始时间
	@Expose
	private String progStartTime;
	// 日程结束时间
	@Expose
	private String progEndTime;
	// 日程种类：01、工作；02、家庭
	@Expose
	private String progKind;
	// 提醒标记：1、是；0、否
	@Expose
	private int remindFlag;

	// 是否全天：1、是；0、否
	@Expose
	private int isDay;

	// 提醒类型：1、分；2、时；3、天；4、周；5、月
	@Expose
	private int reminderType;

	// 提醒时长
	@Expose
	private int reminderLong;
	// 提醒提前时长
	@Expose
	private int remindAhead;
	// 提醒时间
	@Expose
	private String remindTime;

	public String getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}

	// 地址
	@Expose
	private String address;
	@Expose
	private String assignedUid;
	@Expose
	private String assignedName;

	// 被指派用户手机号
	private String assignedMobile;

	// 指派人id
	@Expose
	private Long appointUserId;
	// 参与人姓名
	@Expose
	private String userName;
	// 参与人电话
	@Expose
	private String userMobile;

	// 周期：1：星期一；2：星期二；7星期日
	@Expose
	private String intervalTimeList;
	// 事件类型，1：单次事件；2：周期事件：3：指派单次事件；4:指派周期事件
	private int type;

	// 状态
	@Expose
	private String progStatus;

	public String getProgStatus() {
		return progStatus;
	}

	public void setProgStatus(String progStatus) {
		this.progStatus = progStatus;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProgTitle() {
		return progTitle;
	}

	public void setProgTitle(String progTitle) {
		this.progTitle = progTitle;
	}

	public String getProgContent() {
		return progContent;
	}

	public void setProgContent(String progContent) {
		this.progContent = progContent;
	}

	public String getProgStartDate() {
		return progStartDate;
	}

	public void setProgStartDate(String progStartDate) {
		this.progStartDate = progStartDate;
	}

	public String getProgEndDate() {
		return progEndDate;
	}

	public void setProgEndDate(String progEndDate) {
		this.progEndDate = progEndDate;
	}

	public String getProgStartTime() {
		return progStartTime;
	}

	public void setProgStartTime(String progStartTime) {
		this.progStartTime = progStartTime;
	}

	public String getProgEndTime() {
		return progEndTime;
	}

	public void setProgEndTime(String progEndTime) {
		this.progEndTime = progEndTime;
	}

	public String getProgKind() {
		return progKind;
	}

	public void setProgKind(String progKind) {
		this.progKind = progKind;
	}

	public int getRemindAhead() {
		return remindAhead;
	}

	public void setRemindAhead(int remindAhead) {
		this.remindAhead = remindAhead;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAssignedUid() {
		return assignedUid;
	}

	public void setAssignedUid(String assignedUid) {
		this.assignedUid = assignedUid;
	}

	public int getType() {
		return type;
	}

	public String getIntervalTimeList() {
		return intervalTimeList;
	}

	public void setIntervalTimeList(String intervalTimeList) {
		this.intervalTimeList = intervalTimeList;
	}

	public String getAssignedName() {
		return assignedName;
	}

	public void setAssignedName(String assignedName) {
		this.assignedName = assignedName;
	}

	public String getAssignedMobile() {
		return assignedMobile;
	}

	public void setAssignedMobile(String assignedMobile) {
		this.assignedMobile = assignedMobile;
	}

	public int getRemindFlag() {
		return remindFlag;
	}

	public void setRemindFlag(int remindFlag) {
		this.remindFlag = remindFlag;
	}

	public Long getAppointUserId() {
		return appointUserId;
	}

	public void setAppointUserId(Long appointUserId) {
		this.appointUserId = appointUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public int getIsDay() {
		return isDay;
	}

	public void setIsDay(int isDay) {
		this.isDay = isDay;
	}

	public int getReminderType() {
		return reminderType;
	}

	public void setReminderType(int reminderType) {
		this.reminderType = reminderType;
	}

	public int getReminderLong() {
		return reminderLong;
	}

	public void setReminderLong(int reminderLong) {
		this.reminderLong = reminderLong;
	}

}
