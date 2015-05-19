package com.zte.im.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.StringUtils;

import com.google.gson.annotations.Expose;
import com.zte.im.util.StringUtil;

public class Base {

	@Expose
	private Long createUserId;// 创建人id

	@Expose
	private Long createTime;// 创建时间

	@Expose
	private Long updateUserId;// 修改人id

	@Expose
	private Long updateTime;// 修改时间

	@Expose
	private String remark;// 备注
	
	@Expose
	private String createTimeStr;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTimeStr() {
		return (null != createTimeStr && !"".equals(createTimeStr)) ? createTimeStr : (null != createTime) ? sdf.format(new Date(createTime)) : "";
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

}
