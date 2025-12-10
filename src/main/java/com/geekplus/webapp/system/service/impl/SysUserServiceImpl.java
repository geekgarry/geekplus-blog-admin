package com.geekplus.webapp.system.service.impl;

import com.geekplus.framework.web.exception.BusinessException;
import com.geekplus.common.util.encrypt.EncryptUtil;
import com.geekplus.common.util.string.StringUtils;
import com.geekplus.webapp.system.entity.SysMenu;
import com.geekplus.webapp.system.mapper.SysUserMapper;
import com.geekplus.webapp.system.entity.SysUser;
import com.geekplus.webapp.system.service.SysUserService;
//import com.geekplus.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2023/06/18.
 */
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    /**
    * 增加
    * @param sysUser
    * @return 系统用户表
    */
    @Override
    @Transactional
    public Integer insertSysUser(SysUser sysUser){
        boolean hasUserInfo=sysUserMapper.selectSysUserByPassword(sysUser)!=null?true:false;
        if(hasUserInfo){
            throw new BusinessException("当前用户名已经存在！");
        }
        return sysUserMapper.insertSysUser(sysUser);
    }

    @Override
    @Transactional
    public Integer insertSysUserEnCodePwd(SysUser sysUser) {
        sysUser.setPassword(EncryptUtil.md5EncryptPwd(sysUser.getPassword()));
        boolean hasUserInfo=sysUserMapper.selectSysUserByPassword(sysUser)!=null?true:false;
        if(hasUserInfo){
            throw new BusinessException("当前用户名已经存在！");
        }
        return sysUserMapper.insertSysUser(sysUser);
    }

    /**
    * 批量增加
    * @param sysUserList
    * @return 系统用户表
    */
    @Override
    public Integer batchInsertSysUserList(List<SysUser> sysUserList){
        return sysUserMapper.batchInsertSysUserList(sysUserList);
    }

    /**
    * 删除
    * @param userId
    */
    @Override
    public Integer deleteSysUserById(Long userId){
        return sysUserMapper.deleteSysUserById(userId);
    }

    /**
     * 逻辑删除
     * @param userId
     */
    @Override
    public Integer updateDelFlagById(Long userId) {
        return sysUserMapper.updateDelFlagById(userId);
    }

    /**
    * 批量删除
    */
    @Override
    public Integer deleteSysUserByIds(Long[] userIds){
        return sysUserMapper.deleteSysUserByIds(userIds);
    }

    /**
     * 批量逻辑删除
     */
    @Override
    public Integer updateDelFlagByIds(Long[] userIds) {
        return sysUserMapper.updateDelFlagByIds(userIds);
    }

    /**
    * 修改
    * @param sysUser
    */
    public Integer updateSysUser(SysUser sysUser){
        return sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    public Integer updateSysUserPwd(SysUser sysUser) {
        sysUser.setPassword(EncryptUtil.md5EncryptPwd(sysUser.getPassword()));
        return sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    @Transactional
    public boolean updateUserAvatar(String username, String avatar) {
        SysUser sysUser=new SysUser();
        sysUser.setUsername(username);
        sysUser.setAvatar(avatar);
        return sysUserMapper.updateUserAvatar(sysUser) > 0;
    }

    /**
    * 批量修改某几个字段
    * @param userIds
    */
    public Integer batchUpdateSysUserList(Long[] userIds){
        return sysUserMapper.batchUpdateSysUserList(userIds);
    }

    /**
    * 查询全部
    */
    public List<SysUser> selectSysUserList(SysUser sysUser){
        return sysUserMapper.selectSysUserList(sysUser);
    }

    @Override
    public SysUser selectSysUserByPassword(SysUser sysUser) {
        sysUser.setPassword(EncryptUtil.md5EncryptPwd(sysUser.getPassword()));
        return sysUserMapper.selectSysUserByPassword(sysUser);
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    public List<SysUser> selectUnionSysUserList(SysUser sysUser){
        return sysUserMapper.selectUnionSysUserList(sysUser);
    }

    /**
    * 根据Id查询单条数据
    */
    @Override
    public SysUser selectSysUserById(Long userId){
        return sysUserMapper.selectSysUserById(userId);
    }

    /**
     *通过username查询用户信息和部门，不含角色
     */
    @Override
    public SysUser sysUserLoginBy(String username) {
        return sysUserMapper.sysUserLoginBy(username);
    }

    /**
     *通过username查询用户信息和部门以及角色
     */
    @Override
    public SysUser getSysUserInfoBy(String username) {
        return sysUserMapper.getSysUserInfoBy(username);
    }

    @Override
    public Set<String> getSysUserMenuPerms(Long userId) {
        List<String> perms = sysUserMapper.selectUserMenus(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public int updateSysUserByUsername(String username, String loginIp) {
        SysUser sysUser=new SysUser();
        sysUser.setLoginTime(new Date());
        sysUser.setUsername(username);
        sysUser.setLoginIp(loginIp);
        return sysUserMapper.updateSysUserByUsername(sysUser);
    }

    @Override
    public int selectCountByUsername(String username) {
        return sysUserMapper.selectCountByUsername(username);
    }

    @Override
    public int selectCountByEmail(String email) {
        return sysUserMapper.selectCountByEmail(email);
    }

    @Override
    public int selectCountByPhone(String phoneNumber) {
        return sysUserMapper.selectCountByPhone(phoneNumber);
    }
}
