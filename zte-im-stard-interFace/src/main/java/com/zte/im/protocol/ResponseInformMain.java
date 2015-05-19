package com.zte.im.protocol;

import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * 新建通知返回给客户端的实体类.
 * @author yejun
 *
 */
public class ResponseInformMain {
	@Expose
	private Res res;
	@Expose
	private String informId;
	@Expose
	private Long creatTime;
	@Expose
	private List<?> item;
	@Expose
    private List<?> users;
    @Expose
    private int count;

	public Res getRes() {
		return res;
	}

	public void setRes(Res res) {
		this.res = res;
	}

    public String getInformId() {
        return informId;
    }

    public void setInformId(String informId) {
        this.informId = informId;
    }

    public Long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Long creatTime) {
        this.creatTime = creatTime;
    }

    public List<?> getItem() {
        return item;
    }

    public void setItem(List<?> item) {
        this.item = item;
    }

    public List<?> getUsers() {
        return users;
    }

    public void setUsers(List<?> users) {
        this.users = users;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
