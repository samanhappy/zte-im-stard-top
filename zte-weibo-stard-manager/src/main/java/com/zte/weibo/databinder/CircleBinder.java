package com.zte.weibo.databinder;

import com.zte.databinder.PageBinder;

public class CircleBinder extends PageBinder {

	private String name;
	private String circleType ;//00:全部；01：公开圈；02：私密圈
	private String circleState;//00：启用；99：停用
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCircleType() {
		return circleType;
	}
	public void setCircleType(String circleType) {
		this.circleType = circleType;
	}
	public String getCircleState() {
		return circleState;
	}
	public void setCircleState(String circleState) {
		this.circleState = circleState;
	}
	
}
