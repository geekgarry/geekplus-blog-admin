package com.geekplus.webapp.function.entity;

import com.geekplus.common.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 功能：用户预约任务时间表 对象:user_appointment
 *
 * @author CodeGenerator
 * @date 2025/09/12
 */
public class UserAppointment implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * 用户预约任务时间表 用户预约任务时间表
     */
    private String id;

    /**
     * 用户预约任务时间表 用户预约任务时间表
     */
    private String userId;

    /**
     * 用户预约任务时间表 用户预约任务时间表
     */
    private Instant scheduleAt;

    /**
     * 用户预约任务时间表 用户预约任务时间表
     */
    private String payload;

    /**
     * 用户预约任务时间表 用户预约任务时间表
     */
    private String status;

    /**
     * 用户预约任务时间表 用户预约任务时间表
     */
    private Date createTime;

    /**
     * 用户预约任务时间表 用户预约任务时间表
     */
    private Date updateTime;

    /**
     * 用户预约任务时间表 用户预约任务时间表
     */
    private Integer delFlag;

    /**
     * 用户预约任务时间表 用户预约任务时间表
     */
    private String description;

    /**
     * 用户预约任务时间表 用户预约任务时间表
     */
    private String jobType;

	/**
	 *获取主键
	 */
	public String getId(){
		return id;
	}

	/**
	 *设置主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *获取小程序用户ID
	 */
	public String getUserId(){
		return userId;
	}

	/**
	 *设置小程序用户ID
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 *获取预约开始时间
	 */
	public Instant getScheduleAt(){
		return scheduleAt;
	}

	/**
	 *设置预约开始时间
	 */
	public void setScheduleAt(Instant scheduleAt){
		this.scheduleAt = scheduleAt;
	}
	/**
	 *获取载荷内容
	 */
	public String getPayload(){
		return payload;
	}

	/**
	 *设置载荷内容
	 */
	public void setPayload(String payload){
		this.payload = payload;
	}
	/**
	 *获取状态
	 */
	public String getStatus(){
		return status;
	}

	/**
	 *设置状态
	 */
	public void setStatus(String status){
		this.status = status;
	}
	/**
	 *获取创建时间
	 */
	public Date getCreateTime(){
		return createTime;
	}

	/**
	 *设置创建时间
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 *获取更新时间
	 */
	public Date getUpdateTime(){
		return updateTime;
	}

	/**
	 *设置更新时间
	 */
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	/**
	 *获取删除状态
	 */
	public Integer getDelFlag(){
		return delFlag;
	}

	/**
	 *设置删除状态
	 */
	public void setDelFlag(Integer delFlag){
		this.delFlag = delFlag;
	}
	/**
	 *获取描述
	 */
	public String getDescription(){
		return description;
	}

	/**
	 *设置描述
	 */
	public void setDescription(String description){
		this.description = description;
	}
	/**
	 *获取JobType
	 */
	public String getJobType(){
		return jobType;
	}

	/**
	 *设置JobType
	 */
	public void setJobType(String jobType){
		this.jobType = jobType;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("scheduleAt", getScheduleAt())
            .append("payload", getPayload())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("description", getDescription())
            .append("jobType", getJobType())
            .toString();
    }
}
