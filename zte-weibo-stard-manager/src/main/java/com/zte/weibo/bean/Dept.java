package com.zte.weibo.bean;

import com.google.gson.annotations.Expose;

public class Dept {

	@Expose
	private String id; // PostgreSQL数据主键
	@Expose
	private Long gid; // 对应公司表的总公司id
	@Expose
	private Long cid; // 唯一主键
	@Expose
	private Long pid; // 父节点id
	@Expose
	private String name;// 节点名称
	@Expose
	private Long sequ; // 排序字段
	@Expose
	private Integer isroot;
	@Expose
	private Integer nodeType = 1; //节点类型，0普通，1叶子
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSequ() {
		return sequ;
	}
	public void setSequ(Long sequ) {
		this.sequ = sequ;
	}
	public Integer getIsroot() {
		return isroot;
	}
	public void setIsroot(Integer isroot) {
		this.isroot = isroot;
	}
	public Integer getNodeType() {
		return nodeType;
	}
	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}
	
}
