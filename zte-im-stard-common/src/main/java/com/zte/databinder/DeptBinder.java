package com.zte.databinder;

public class DeptBinder {

	private String id;
	private String gname;
	private String deptName; // 上级部门名称
	private String deptId; // 上级部门ID
	private String deptDesc;
	private Long sequ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public Long getSequ() {
		return sequ;
	}

	public void setSequ(Long sequ) {
		this.sequ = sequ;
	}

}
