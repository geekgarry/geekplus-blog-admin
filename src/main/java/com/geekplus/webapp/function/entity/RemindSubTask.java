package com.geekplus.webapp.function.entity;

import com.geekplus.common.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 功能：提醒任务子任务 对象:remind_sub_task
 *
 * @author CodeGenerator
 * @date 2025/09/20
 */
public class RemindSubTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /**
     * 提醒任务子任务 提醒任务子任务
     */
    private Integer id;

    /**
     * 提醒任务子任务 提醒任务子任务
     */
    private Integer taskId;

    /**
     * 提醒任务子任务 提醒任务子任务
     */
    private String taskContent;

    /**
     * 提醒任务子任务 提醒任务子任务
     */
    private Date createTime;

    /**
     * 提醒任务子任务 提醒任务子任务
     */
    private Integer isComplete;

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
	 *获取主任务ID
	 */
	public Integer getTaskId(){
		return taskId;
	}

	/**
	 *设置主任务ID
	 */
	public void setTaskId(Integer taskId){
		this.taskId = taskId;
	}
	/**
	 *获取子任务内容
	 */
	public String getTaskContent(){
		return taskContent;
	}

	/**
	 *设置子任务内容
	 */
	public void setTaskContent(String taskContent){
		this.taskContent = taskContent;
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
	 *获取是否完成0未完成1已完成
	 */
	public Integer getIsComplete(){
		return isComplete;
	}

	/**
	 *设置是否完成0未完成1已完成
	 */
	public void setIsComplete(Integer isComplete){
		this.isComplete = isComplete;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskId", getTaskId())
            .append("taskContent", getTaskContent())
            .append("createTime", getCreateTime())
            .append("isComplete", getIsComplete())
            .toString();
    }
}
