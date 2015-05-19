package com.zte.im.mdm;

import com.google.gson.annotations.Expose;

public class MDMUser implements Comparable<MDMUser> {

	@Expose
	public String birthday;
	@Expose
	public Integer enabled;
	@Expose
	public String org_level;//职级
	@Expose
	public String ext_number;
	@Expose
	public String org_account;
	@Expose
	public Integer status;
	@Expose
	public Integer state;
	@Expose
	public Long longid;
	@Expose
	public String tel_number;//手机
	@Expose
	public String org_post;
	@Expose
	public String org_department;
	@Expose
	public Integer is_deleted;
	@Expose
	public Integer is_admin;
	@Expose
	public String name;
	@Expose
	public String login_name;
	@Expose
	public String gender;//性别
	@Expose
	public String email_address;
	@Expose
	public String officePhone;//固定电话
	@Expose
	public Long sort_id;

	@Override
	public int compareTo(MDMUser o) {
		if (this.sort_id != null && o != null && o.sort_id != null) {
			return (int) (this.sort_id - o.sort_id);
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
}
