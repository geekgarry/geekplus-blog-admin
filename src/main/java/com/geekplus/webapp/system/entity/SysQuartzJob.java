package com.geekplus.webapp.system.entity;

import com.geekplus.common.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 功能：quartz系统调度任务 对象:sys_quartz_job
 *
 * @author CodeGenerator
 * @date 2025/09/20
 */
public class SysQuartzJob implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * quartz系统调度任务 quartz系统调度任务
     */
    private String id;

    /**
     * quartz系统调度任务 quartz系统调度任务
     */
    private String createBy;

    /**
     * quartz系统调度任务 quartz系统调度任务
     */
    private Date createTime;

    /**
     * quartz系统调度任务 quartz系统调度任务
     */
    private Integer delFlag;

    /**
     * quartz系统调度任务 quartz系统调度任务
     */
    private String updateBy;

    /**
     * quartz系统调度任务 quartz系统调度任务
     */
    private Date updateTime;

    /**
     * quartz系统调度任务 quartz系统调度任务
     */
    private String jobClassName;

    /**
     * quartz系统调度任务 quartz系统调度任务
     */
    private String cronExpression;

    /**
     * quartz系统调度任务 quartz系统调度任务
     */
    private String parameter;

    /**
     * quartz系统调度任务 quartz系统调度任务
     */
    private String description;

    /**
     * quartz系统调度任务 quartz系统调度任务
     */
    private Integer status;

	/**
	 *获取ID
	 */
	public String getId(){
		return id;
	}

	/**
	 *设置ID
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *获取创建人
	 */
	public String getCreateBy(){
		return createBy;
	}

	/**
	 *设置创建人
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
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
	 *获取修改人
	 */
	public String getUpdateBy(){
		return updateBy;
	}

	/**
	 *设置修改人
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *获取修改时间
	 */
	public Date getUpdateTime(){
		return updateTime;
	}

	/**
	 *设置修改时间
	 */
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	/**
	 *获取任务类名
	 */
	public String getJobClassName(){
		return jobClassName;
	}

	/**
	 *设置任务类名
	 */
	public void setJobClassName(String jobClassName){
		this.jobClassName = jobClassName;
	}
	/**
	 *获取cron表达式
	 */
	public String getCronExpression(){
		return cronExpression;
	}

	/**
	 *设置cron表达式
	 */
	public void setCronExpression(String cronExpression){
		this.cronExpression = cronExpression;
	}
	/**
	 *获取参数
	 */
	public String getParameter(){
		return parameter;
	}

	/**
	 *设置参数
	 */
	public void setParameter(String parameter){
		this.parameter = parameter;
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
	 *获取状态 0正常 -1停止
	 */
	public Integer getStatus(){
		return status;
	}

	/**
	 *设置状态 0正常 -1停止
	 */
	public void setStatus(Integer status){
		this.status = status;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("delFlag", getDelFlag())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("jobClassName", getJobClassName())
            .append("cronExpression", getCronExpression())
            .append("parameter", getParameter())
            .append("description", getDescription())
            .append("status", getStatus())
            .toString();
    }
}
