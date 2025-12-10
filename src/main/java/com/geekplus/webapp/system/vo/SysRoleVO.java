package com.geekplus.webapp.system.vo;

import java.io.Serializable;

/**
 * author     : geekplus
 * email      :
 * date       : 9/21/25 7:23 PM
 * description: //TODO
 */
public class SysRoleVO {

    /**
     * 系统角色表 系统角色表
     */
    private Long roleId;

    /**
     * 系统角色表 系统角色表
     */
    private String roleName;

    /**
     * 系统角色表 系统角色表
     */
    private String roleKey;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }
}
