package com.geekplus.webapp.function.entity;

import com.geekplus.common.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 功能：提醒任务 对象:remind_task
 *
 * @author CodeGenerator
 * @date 2025/09/20
 */
public class RemindTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /**
     * 提醒任务 提醒任务
     */
    private Integer id;

    /**
     * 提醒任务 提醒任务
     */
    private String userId;

    /**
     * 提醒任务 提醒任务
     */
    private String taskTitle;

    /**
     * 提醒任务 提醒任务
     */
    private String taskContent;

    /**
     * 提醒任务 提醒任务
     */
    private Integer taskType;

    /**
     * 提醒任务 提醒任务
     */
    private Integer taskStatus;

    /**
     * 提醒任务 提醒任务
     */
    private Date deadline;

    /**
     * 提醒任务 提醒任务
     */
    private Date createTime;

    /**
     * 提醒任务 提醒任务
     */
    private Integer isRemind;

    /**
     * 提醒任务 提醒任务
     */
    private Date remindTime;

    /**
     * 提醒任务 提醒任务
     */
    private Integer isRepeat;

	/**
	 *获取ID
	 */
	public Integer getId(){
		return id;
	}

	/**
	 *设置ID
	 */
	public void setId(Integer id){
		this.id = id;
	}
	/**
	 *获取关联用户ID
	 */
	public String getUserId(){
		return userId;
	}

	/**
	 *设置关联用户ID
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 *获取任务标题
	 */
	public String getTaskTitle(){
		return taskTitle;
	}

	/**
	 *设置任务标题
	 */
	public void setTaskTitle(String taskTitle){
		this.taskTitle = taskTitle;
	}
	/**
	 *获取任务内容
	 */
	public String getTaskContent(){
		return taskContent;
	}

	/**
	 *设置任务内容
	 */
	public void setTaskContent(String taskContent){
		this.taskContent = taskContent;
	}
	/**
	 *获取任务类型
	 */
	public Integer getTaskType(){
		return taskType;
	}

	/**
	 *设置任务类型
	 */
	public void setTaskType(Integer taskType){
		this.taskType = taskType;
	}
	/**
	 *获取任务状态,0已创建，1进行中，2已完成
	 */
	public Integer getTaskStatus(){
		return taskStatus;
	}

	/**
	 *设置任务状态,0已创建，1进行中，2已完成
	 */
	public void setTaskStatus(Integer taskStatus){
		this.taskStatus = taskStatus;
	}
	/**
	 *获取任务截止时间
	 */
	public Date getDeadline(){
		return deadline;
	}

	/**
	 *设置任务截止时间
	 */
	public void setDeadline(Date deadline){
		this.deadline = deadline;
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
	 *获取是否提醒
	 */
	public Integer getIsRemind(){
		return isRemind;
	}

	/**
	 *设置是否提醒
	 */
	public void setIsRemind(Integer isRemind){
		this.isRemind = isRemind;
	}
	/**
	 *获取提醒时间
	 */
	public Date getRemindTime(){
		return remindTime;
	}

	/**
	 *设置提醒时间
	 */
	public void setRemindTime(Date remindTime){
		this.remindTime = remindTime;
	}
	/**
	 *获取是否重复提醒直到任务结束
	 */
	public Integer getIsRepeat(){
		return isRepeat;
	}

	/**
	 *设置是否重复提醒直到任务结束
	 */
	public void setIsRepeat(Integer isRepeat){
		this.isRepeat = isRepeat;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("taskTitle", getTaskTitle())
            .append("taskContent", getTaskContent())
            .append("taskType", getTaskType())
            .append("taskStatus", getTaskStatus())
            .append("deadline", getDeadline())
            .append("createTime", getCreateTime())
            .append("isRemind", getIsRemind())
            .append("remindTime", getRemindTime())
            .append("isRepeat", getIsRepeat())
            .toString();
    }
}
