package com.geekplus.webapp.system.mapper;

import com.geekplus.webapp.system.entity.SysMenu;
import com.geekplus.webapp.system.entity.SysRole;
import com.geekplus.webapp.system.entity.SysUser;

import java.util.List;
import java.util.Set;

/**
 * 系统用户表 系统用户表
 * Created by CodeGenerator on 2023/06/18.
 */

public interface SysUserMapper {

    /**
    * 增加
    * @param sysUser
    * @return 系统用户表
    */
    Integer insertSysUser(SysUser sysUser);

    /**
    * 批量增加
    * @param sysUserList
    * @return
    */
    public int batchInsertSysUserList(List<SysUser> sysUserList);

    /**
    * 删除
    * @param userId
    */
    Integer deleteSysUserById(Long userId);

    /**
     * 逻辑删除
     * @param userId
     */
    Integer updateDelFlagById(Long userId);

    /**
    * 批量删除
    */
    Integer deleteSysUserByIds(Long[] userIds);

    /**
     * 批量逻辑删除
     */
    Integer updateDelFlagByIds(Long[] userIds);

    /**
    * 修改
    * @param sysUser
    */
    Integer updateSysUser(SysUser sysUser);

    Integer updateSysUserByUsername(SysUser sysUser);

    /**
    * 批量修改魔偶几个字段
    * @param userIds
    */
    Integer batchUpdateSysUserList(Long[] userIds);

    /**
    * 查询全部
    */
    List<SysUser> selectSysUserList(SysUser sysUser);

    /**
     * 查询全部
     */
    SysUser selectSysUserByPassword(SysUser sysUser);

    /**
    * 查询全部,联合查询使用
    */
    List<SysUser> selectUnionSysUserList(SysUser sysUser);

    /**
    * 根据Id查询单条数据
    */
    SysUser selectSysUserById(Long userId);

    /** 根据用户名查询用户信息及所属部门*/
    SysUser sysUserLoginBy(String userName);

    /** 根据用户名查询用户信息和所属角色*/
    SysUser getSysUserInfoBy(String userName);

    /** 根据手机查询用户信息和所属角色*/
    SysUser getSysUserInfoByPhone(String phoneNumber);

    /** 根据邮箱查询用户信息和所属角色*/
    SysUser getSysUserInfoByEmail(String email);

    List<SysRole> selectUserRoles(Long userId);

    List<String> selectUserMenus(Long userId);

    Integer updateUserAvatar(SysUser sysUser);

    Integer selectCountByUsername(String username);

    Integer selectCountByEmail(String email);

    Integer selectCountByPhone(String phoneNumber);
}
