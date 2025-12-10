package com.geekplus.webapp.system.entity;

import com.geekplus.common.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 功能：数据字典 对象:sys_dict
 *
 * @author CodeGenerator
 * @date 2025/09/20
 */
public class SysDict implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * 数据字典 数据字典
     */
    private Long id;

    /**
     * 数据字典 数据字典
     */
    private String dictName;

    /**
     * 数据字典 数据字典
     */
    private String dictCode;

    /**
     * 数据字典 数据字典
     */
    private String status;

    /**
     * 数据字典 数据字典
     */
    private String createBy;

    /**
     * 数据字典 数据字典
     */
    private Date createTime;

    /**
     * 数据字典 数据字典
     */
    private String updateBy;

    /**
     * 数据字典 数据字典
     */
    private Date updateTime;

    /**
     * 数据字典 数据字典
     */
    private String remarks;

    /**
     * 数据字典 数据字典
     */
    private Integer delFlag;

	/**
	 *获取ID
	 */
	public Long getId(){
		return id;
	}

	/**
	 *设置ID
	 */
	public void setId(Long id){
		this.id = id;
	}
	/**
	 *获取字典名称
	 */
	public String getDictName(){
		return dictName;
	}

	/**
	 *设置字典名称
	 */
	public void setDictName(String dictName){
		this.dictName = dictName;
	}
	/**
	 *获取字典编码
	 */
	public String getDictCode(){
		return dictCode;
	}

	/**
	 *设置字典编码
	 */
	public void setDictCode(String dictCode){
		this.dictCode = dictCode;
	}
	/**
	 *获取状态，0为正常，1为停用
	 */
	public String getStatus(){
		return status;
	}

	/**
	 *设置状态，0为正常，1为停用
	 */
	public void setStatus(String status){
		this.status = status;
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
	 *获取更新人
	 */
	public String getUpdateBy(){
		return updateBy;
	}

	/**
	 *设置更新人
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
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
	 *获取备注
	 */
	public String getRemarks(){
		return remarks;
	}

	/**
	 *设置备注
	 */
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	/**
	 *获取删除状态（0正常，1已删除）
	 */
	public Integer getDelFlag(){
		return delFlag;
	}

	/**
	 *设置删除状态（0正常，1已删除）
	 */
	public void setDelFlag(Integer delFlag){
		this.delFlag = delFlag;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dictName", getDictName())
            .append("dictCode", getDictCode())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remarks", getRemarks())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
