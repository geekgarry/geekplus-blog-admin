package com.geekplus.webapp.function.entity;

import com.geekplus.common.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 功能：在线音乐 对象:gp_music
 *
 * @author CodeGenerator
 * @date 2023/09/29
 */
public class GpMusic extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /**
     * 在线音乐 在线音乐
     */
    private Integer id;

    /**
     * 在线音乐 在线音乐
     */
    private String name;

    /**
     * 在线音乐 在线音乐
     */
    private String artist;

    /**
     * 在线音乐 在线音乐
     */
    private String url;

    /**
     * 在线音乐 在线音乐
     */
    private String cover;

    /**
     * 在线音乐 在线音乐
     */
    private String lrc;

	/**
	 * 顺序序号
	 */
	private Integer orderId;

	/**
	 * 是否显示
	 */
	private String isDisplay;

    /**
     * 在线音乐 在线音乐
     */
    private Date createTime;

    /**
     * 在线音乐 在线音乐
     */
    private Date updateTime;

	/**
	 *获取主键ID
	 */
	public Integer getId(){
		return id;
	}

	/**
	 *设置主键ID
	 */
	public void setId(Integer id){
		this.id = id;
	}
	/**
	 *获取歌曲名称
	 */
	public String getName(){
		return name;
	}

	/**
	 *设置歌曲名称
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 *获取歌曲专辑
	 */
	public String getArtist(){
		return artist;
	}

	/**
	 *设置歌曲专辑
	 */
	public void setArtist(String artist){
		this.artist = artist;
	}
	/**
	 *获取歌曲地址
	 */
	public String getUrl(){
		return url;
	}

	/**
	 *设置歌曲地址
	 */
	public void setUrl(String url){
		this.url = url;
	}
	/**
	 *获取歌曲封面
	 */
	public String getCover(){
		return cover;
	}

	/**
	 *设置歌曲封面
	 */
	public void setCover(String cover){
		this.cover = cover;
	}
	/**
	 *获取歌曲歌词
	 */
	public String getLrc(){
		return lrc;
	}

	/**
	 *设置歌曲歌词
	 */
	public void setLrc(String lrc){
		this.lrc = lrc;
	}

	/**
	 *获取顺序序号
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 *设置顺序序号
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 *获取是否显示
	 */
	public String getIsDisplay() {
		return isDisplay;
	}

	/**
	 *设置是否显示
	 */
	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("artist", getArtist())
            .append("url", getUrl())
            .append("cover", getCover())
            .append("lrc", getLrc())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
