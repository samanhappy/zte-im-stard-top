package com.zte.im.mybatis.bean;

import com.ztecs.appoc.cloudservice.entity.IdEntity;
import java.util.Date;

public class MnParticipant extends IdEntity {
    /**
     * <pre>
     * mn_participant.inform_id
     * 长度 40
     * 可空:否
     * 默认值:
     * 备注：会议ID
     * </pre>
     */
    private String informId;

    /**
     * <pre>
     * mn_participant.participant_id
     * 长度 40
     * 可空:否
     * 默认值:
     * 备注：参与者ID
     * </pre>
     */
    private String participantId;

    /**
     * <pre>
     * mn_participant.status
     * 长度 64
     * 可空:是
     * 默认值:
     * 备注：状态，a：接受 r：拒绝，空为未处理
     * </pre>
     */
    private String status;

    /**
     * <pre>
     * mn_participant.deal_time
     * 长度 29
     * 可空:是
     * 默认值:
     * 备注：处理时间
     * </pre>
     */
    private Date dealTime;

    /**
     * <pre>
     * mn_participant.remark
     * 长度 1000
     * 可空:是
     * 默认值:
     * 备注：拒绝理由
     * </pre>
     */
    private String remark;
    
    private String name;

    /**
     * {@link #informId}
     */
    public String getInformId() {
        return informId;
    }

    /**
     * {@link #informId}
     */
    public void setInformId(String informId) {
        this.informId = informId == null ? null : informId.trim();
    }

    /**
     * {@link #participantId}
     */
    public String getParticipantId() {
        return participantId;
    }

    /**
     * {@link #participantId}
     */
    public void setParticipantId(String participantId) {
        this.participantId = participantId == null ? null : participantId.trim();
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
     * {@link #dealTime}
     */
    public Date getDealTime() {
        return dealTime;
    }

    /**
     * {@link #dealTime}
     */
    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
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

    @Override
    public String toString() {
        return "MnParticipant [" + "informId=" + informId + ", participantId=" + participantId + ", status=" + status 
                    + ", dealTime=" + dealTime + ", remark=" + remark + ", name=" + name + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}