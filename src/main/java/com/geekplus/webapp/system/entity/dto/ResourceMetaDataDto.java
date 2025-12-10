package com.geekplus.webapp.system.entity.dto;

import com.geekplus.common.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;


/**
 * 功能：资源数据表 对象:resource_meta_data
 *
 * @author geekplus
 * @date 2025/12/02
 */
public class ResourceMetaDataDto extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /**
     * 文件资源ID
     */
    private Long id;

    /**
     * 文件原始名称
     */
    private String originalFileName;

    /**
     * 文件在服务器上的实际名称
     */
    private String storedFileName;

    /**
     * 文件在服务器上的相对路径
     */
    private String actualStoragePath;

    /**
     * 客户端将用于请求的路径
     */
    private List<String> logicalPaths;

    /**
     * 内容类型,图片，视频，文件
     */
    private String contentType;

    /**
     * 上传用户ID
     */
    private Long ownerUserId;

    /**
     * 关联实体(文章)id，当entity_type为1时
     */
    private String relatedEntityId;

    /**
     * 访问级别(PUBLIC, AUTHENTICATED, PRIVATE)
     */
    private String accessLevel;

    /**
     * 可用true/false,0/1
     */
    private Integer isAvailable;

    /**
     * 实体类型，文章，轮播图，头像等
     */
    private Integer entityType;

	/**
	 *获取文件资源ID
	 */
	public Long getId(){
		return id;
	}

	/**
	 *设置文件资源ID
	 */
	public void setId(Long id){
		this.id = id;
	}
	/**
	 *获取文件原始名称
	 */
	public String getOriginalFileName(){
		return originalFileName;
	}

	/**
	 *设置文件原始名称
	 */
	public void setOriginalFileName(String originalFileName){
		this.originalFileName = originalFileName;
	}
	/**
	 *获取文件在服务器上的实际名称
	 */
	public String getStoredFileName(){
		return storedFileName;
	}

	/**
	 *设置文件在服务器上的实际名称
	 */
	public void setStoredFileName(String storedFileName){
		this.storedFileName = storedFileName;
	}
	/**
	 *获取文件在服务器上的相对路径
	 */
	public String getActualStoragePath(){
		return actualStoragePath;
	}

	/**
	 *设置文件在服务器上的相对路径
	 */
	public void setActualStoragePath(String actualStoragePath){
		this.actualStoragePath = actualStoragePath;
	}
	/**
	 *获取客户端将用于请求的路径
	 */
	public List<String> getLogicalPath(){
		return logicalPaths;
	}

	/**
	 *设置客户端将用于请求的路径
	 */
	public void setLogicalPath(List<String> logicalPaths){
		this.logicalPaths = logicalPaths;
	}
	/**
	 *获取内容类型,图片，视频，文件
	 */
	public String getContentType(){
		return contentType;
	}

	/**
	 *设置内容类型,图片，视频，文件
	 */
	public void setContentType(String contentType){
		this.contentType = contentType;
	}
	/**
	 *获取上传用户ID
	 */
	public Long getOwnerUserId(){
		return ownerUserId;
	}

	/**
	 *设置上传用户ID
	 */
	public void setOwnerUserId(Long ownerUserId){
		this.ownerUserId = ownerUserId;
	}
	/**
	 *获取关联实体(文章)id，当entity_type为1时
	 */
	public String getRelatedEntityId(){
		return relatedEntityId;
	}

	/**
	 *设置关联实体(文章)id，当entity_type为1时
	 */
	public void setRelatedEntityId(String relatedEntityId){
		this.relatedEntityId = relatedEntityId;
	}
	/**
	 *获取访问级别(PUBLIC, AUTHENTICATED, PRIVATE)
	 */
	public String getAccessLevel(){
		return accessLevel;
	}

	/**
	 *设置访问级别(PUBLIC, AUTHENTICATED, PRIVATE)
	 */
	public void setAccessLevel(String accessLevel){
		this.accessLevel = accessLevel;
	}
	/**
	 *获取可用true/false
	 */
	public Integer getIsAvailable(){
		return isAvailable;
	}

	/**
	 *设置可用true/false
	 */
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	/**
	 *获取实体类型，文章，轮播图，头像等
	 */
	public Integer getEntityType(){
		return entityType;
	}

	/**
	 *设置实体类型，文章，轮播图，头像等
	 */
	public void setEntityType(Integer entityType){
		this.entityType = entityType;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("originalFileName", getOriginalFileName())
            .append("storedFileName", getStoredFileName())
            .append("actualStoragePath", getActualStoragePath())
            .append("logicalPath", getLogicalPath())
            .append("contentType", getContentType())
            .append("ownerUserId", getOwnerUserId())
            .append("relatedEntityId", getRelatedEntityId())
            .append("accessLevel", getAccessLevel())
            .append("isAvailable", getIsAvailable())
            .append("entityType", getEntityType())
            .toString();
    }
}
