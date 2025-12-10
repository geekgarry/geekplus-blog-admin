package com.geekplus.webapp.system.vo;

import java.io.Serializable;

/**
 * author     : geekplus
 * email      :
 * date       : 9/21/25 7:24 PM
 * description: //TODO
 */
public class SysUserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户的基本信息
    private Long userId;
    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String avatar;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
