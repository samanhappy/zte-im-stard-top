package com.zte.im.mybatis.bean;

import com.ztecs.appoc.cloudservice.entity.IdEntity;
import java.util.Date;

public class MnInform extends IdEntity {
    /**
     * <pre>
     * mn_inform.title
     * 长度 255
     * 可空:是
     * 默认值:
     * 备注：会议主题
     * </pre>
     */
    private String title;

    /**
     * <pre>
     * mn_inform.address
     * 长度 255
     * 可空:是
     * 默认值:
     * 备注：会议举行地址
     * </pre>
     */
    private String address;

    /**
     * <pre>
     * mn_inform.remark
     * 长度 1000
     * 可空:是
     * 默认值:
     * 备注：会议备注
     * </pre>
     */
    private String remark;

    /**
     * <pre>
     * mn_inform.start_time
     * 长度 29
     * 可空:是
     * 默认值:
     * 备注：开始时间
     * </pre>
     */
    private Date startTime;

    /**
     * <pre>
     * mn_inform.end_time
     * 长度 29
     * 可空:是
     * 默认值:
     * 备注：结束时间
     * </pre>
     */
    private Date endTime;

    /**
     * <pre>
     * mn_inform.status
     * 长度 64
     * 可空:是
     * 默认值:
     * 备注：1: 已创建 2::已取消
     * </pre>
     */
    private String status;

    /**
     * <pre>
     * mn_inform.creator
     * 长度 40
     * 可空:否
     * 默认值:
     * 备注：
     * </pre>
     */
    private String creator;

    /**
     * <pre>
     * mn_inform.create_time
     * 长度 29
     * 可空:是
     * 默认值:
     * 备注：
     * </pre>
     */
    private Date createTime;

    /**
     * {@link #title}
     */
    public String getTitle() {
        return title;
    }

    /**
     * {@link #title}
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * {@link #address}
     */
    public String getAddress() {
        return address;
    }

    /**
     * {@link #address}
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * {@link #remark}
     */
    public String getRemark() {
        return remark;
    }

    /**
     * {@link #remark}
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * {@link #startTime}
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * {@link #startTime}
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * {@link #endTime}
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * {@link #endTime}
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * {@link #status}
     */
    public String getStatus() {
        return status;
    }

    /**
     * {@link #status}
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * {@link #creator}
     */
    public String getCreator() {
        return creator;
    }

    /**
     * {@link #creator}
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * {@link #createTime}
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * {@link #createTime}
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MnInform [" + "title=" + title + ", address=" + address + ", remark=" + remark + ", startTime=" + startTime + ", endTime=" + endTime + ", status=" + status + ", creator=" + creator + ", createTime=" + createTime + "]";
    }
}