package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class Groupids {
	@Expose
	private GroupChat groupids;

	public GroupChat getGroupids() {
		return groupids;
	}

	public void setGroupids(GroupChat groupids) {
		this.groupids = groupids;
	}

}
