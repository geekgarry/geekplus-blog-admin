package com.geekplus.webapp.function.entity;

import com.geekplus.common.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 功能：用户信息表 对象:users
 *
 * @author CodeGenerator
 * @date 2024/09/20
 */
public class Users extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /**
     * 用户信息表 用户信息表
     */
    private Long userId;

    /**
     * 用户信息表 用户信息表
     */
    private String username;

    /**
     * 用户信息表 用户信息表
     */
    private String nickname;

    /**
     * 用户信息表 用户信息表
     */
    private Integer userLevel;

    /**
     * 用户信息表 用户信息表
     */
    private String email;

    /**
     * 用户信息表 用户信息表
     */
    private String phoneNumber;

    /**
     * 用户信息表 用户信息表
     */
    private Integer gender;

    /**
     * 用户信息表 用户信息表
     */
    private String avatar;

    /**
     * 用户信息表 用户信息表
     */
    private String password;

    /**
     * 用户信息表 用户信息表
     */
    private Integer status;

    /**
     * 用户信息表 用户信息表
     */
    private Integer delFlag;

    /**
     * 用户信息表 用户信息表
     */
    private String loginIp;

    /**
     * 用户信息表 用户信息表
     */
    private String loginAddress;

    /**
     * 用户信息表 用户信息表
     */
    private Date loginTime;

    /**
     * 用户信息表 用户信息表
     */
    private String createBy;

    /**
     * 用户信息表 用户信息表
     */
    private Date createTime;

    /**
     * 用户信息表 用户信息表
     */
    private String updateBy;

    /**
     * 用户信息表 用户信息表
     */
    private Date updateTime;

    /**
     * 用户信息表 用户信息表
     */
    private String remark;

	/**
	 * 用户登录平台方式，web或app
	 */
	private String loginType;

	private String tokenId;

	/**
	 *获取用户ID
	 */
	public Long getUserId(){
		return userId;
	}

	/**
	 *设置用户ID
	 */
	public void setUserId(Long userId){
		this.userId = userId;
	}
	/**
	 *获取用户账号
	 */
	public String getUsername(){
		return username;
	}

	/**
	 *设置用户账号
	 */
	public void setUsername(String userName){
		this.username = userName;
	}
	/**
	 *获取用户昵称
	 */
	public String getNickname(){
		return nickname;
	}

	/**
	 *设置用户昵称
	 */
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	/**
	 *获取默认为0，表示为普通用户（1为vip，2为svip）
	 */
	public Integer getUserLevel(){
		return userLevel;
	}

	/**
	 *设置默认为0，表示为普通用户（1为vip，2为svip）
	 */
	public void setUserLevel(Integer userLevel){
		this.userLevel = userLevel;
	}
	/**
	 *获取用户邮箱
	 */
	public String getEmail(){
		return email;
	}

	/**
	 *设置用户邮箱
	 */
	public void setEmail(String email){
		this.email = email;
	}
	/**
	 *获取手机号码
	 */
	public String getPhoneNumber(){
		return phoneNumber;
	}

	/**
	 *设置手机号码
	 */
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	/**
	 *获取用户性别（0男 1女 2未知）
	 */
	public Integer getGender(){
		return gender;
	}

	/**
	 *设置用户性别（0男 1女 2未知）
	 */
	public void setGender(Integer gender){
		this.gender = gender;
	}
	/**
	 *获取头像地址
	 */
	public String getAvatar(){
		return avatar;
	}

	/**
	 *设置头像地址
	 */
	public void setAvatar(String avatar){
		this.avatar = avatar;
	}
	/**
	 *获取密码
	 */
	public String getPassword(){
		return password;
	}

	/**
	 *设置密码
	 */
	public void setPassword(String password){
		this.password = password;
	}
	/**
	 *获取帐号状态（0正常 1停用）
	 */
	public Integer getStatus(){
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 *设置删除标志（0代表存在 2代表删除）
	 */

	/**
	 *获取最后登录IP
	 */
	public String getLoginIp(){
		return loginIp;
	}

	/**
	 *设置最后登录IP
	 */
	public void setLoginIp(String loginIp){
		this.loginIp = loginIp;
	}
	/**
	 *获取登录地址
	 */
	public String getLoginAddress(){
		return loginAddress;
	}

	/**
	 *设置登录地址
	 */
	public void setLoginAddress(String loginAddress){
		this.loginAddress = loginAddress;
	}
	/**
	 *获取最后登录时间
	 */
	public Date getLoginTime(){
		return loginTime;
	}

	/**
	 *设置最后登录时间
	 */
	public void setLoginTime(Date loginTime){
		this.loginTime = loginTime;
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

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("userName", getUsername())
            .append("nickName", getNickname())
            .append("userLevel", getUserLevel())
            .append("email", getEmail())
            .append("phoneNumber", getPhoneNumber())
            .append("gender", getGender())
            .append("avatar", getAvatar())
            .append("password", getPassword())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("loginIp", getLoginIp())
            .append("loginAddress", getLoginAddress())
            .append("loginTime", getLoginTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
