package com.geekplus.webapp.system.entity;

import com.geekplus.common.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 功能：数据字典数据项 对象:sys_dict_data
 *
 * @author CodeGenerator
 * @date 2025/09/20
 */
public class SysDictData implements Serializable
{
    private static final long serialVersionUID = 1L;


    /**
     * 数据字典数据项 数据字典数据项
     */
    private Long id;

	/**
	 * 数据字典数据项 数据字典Id
	 */
	private Long dictId;

    /**
     * 数据字典数据项 数据字典数据项
     */
    private Integer dictSort;

    /**
     * 数据字典数据项 数据字典数据项
     */
    private String dictLabel;

    /**
     * 数据字典数据项 数据字典数据项
     */
    private String dictValue;

    /**
     * 数据字典数据项 数据字典数据项
     */
    private String dictCode;

    /**
     * 数据字典数据项 数据字典数据项
     */
    private String isDefault;

    /**
     * 数据字典数据项 数据字典数据项
     */
    private String status;

    /**
     * 数据字典数据项 数据字典数据项
     */
    private String createBy;

    /**
     * 数据字典数据项 数据字典数据项
     */
    private Date createTime;

    /**
     * 数据字典数据项 数据字典数据项
     */
    private String updateBy;

    /**
     * 数据字典数据项 数据字典数据项
     */
    private Date updateTime;

    /**
     * 数据字典数据项 数据字典数据项
     */
    private String remark;

	/**
	 *获取字典编码
	 */
	public Long getId(){
		return id;
	}

	/**
	 *设置字典编码
	 */
	public void setId(Long id){
		this.id = id;
	}

	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	/**
	 *获取字典排序
	 */
	public Integer getDictSort(){
		return dictSort;
	}

	/**
	 *设置字典排序
	 */
	public void setDictSort(Integer dictSort){
		this.dictSort = dictSort;
	}
	/**
	 *获取字典标签
	 */
	public String getDictLabel(){
		return dictLabel;
	}

	/**
	 *设置字典标签
	 */
	public void setDictLabel(String dictLabel){
		this.dictLabel = dictLabel;
	}
	/**
	 *获取字典键值
	 */
	public String getDictValue(){
		return dictValue;
	}

	/**
	 *设置字典键值
	 */
	public void setDictValue(String dictValue){
		this.dictValue = dictValue;
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
	 *获取是否默认（Y是 N否）
	 */
	public String getIsDefault(){
		return isDefault;
	}

	/**
	 *设置是否默认（Y是 N否）
	 */
	public void setIsDefault(String isDefault){
		this.isDefault = isDefault;
	}
	/**
	 *获取状态（0正常 1停用）
	 */
	public String getStatus(){
		return status;
	}

	/**
	 *设置状态（0正常 1停用）
	 */
	public void setStatus(String status){
		this.status = status;
	}
	/**
	 *获取创建者
	 */
	public String getCreateBy(){
		return createBy;
	}

	/**
	 *设置创建者
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
	 *获取更新者
	 */
	public String getUpdateBy(){
		return updateBy;
	}

	/**
	 *设置更新者
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
	public String getRemark(){
		return remark;
	}

	/**
	 *设置备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dictSort", getDictSort())
            .append("dictLabel", getDictLabel())
            .append("dictValue", getDictValue())
            .append("dictCode", getDictCode())
            .append("isDefault", getIsDefault())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
