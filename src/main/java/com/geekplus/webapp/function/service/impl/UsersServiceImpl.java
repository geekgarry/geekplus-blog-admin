package com.geekplus.webapp.function.service.impl;

import com.geekplus.webapp.function.mapper.UsersMapper;
import com.geekplus.webapp.function.entity.Users;
import com.geekplus.webapp.function.service.UsersService;
//import com.geekplus.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2024/09/20.
 */
@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Resource
    private UsersMapper usersMapper;

    /**
    * 增加
    * @param users
    * @return 用户信息表
    */
    public Integer insertUsers(Users users){
        return usersMapper.insertUsers(users);
    }

    /**
    * 批量增加
    * @param usersList
    * @return 用户信息表
    */
    public Integer batchInsertUsersList(List<Users> usersList){
        return usersMapper.batchInsertUsersList(usersList);
    }

    /**
    * 删除
    * @param userId
    */
    public Integer deleteUsersById(Long userId){
        return usersMapper.deleteUsersById(userId);
    }

    /**
    * 批量删除
    */
    public Integer deleteUsersByIds(Long[] userIds){
        return usersMapper.deleteUsersByIds(userIds);
    }

    /**
    * 修改
    * @param users
    */
    public Integer updateUsers(Users users){
        return usersMapper.updateUsers(users);
    }

    /**
    * 批量修改某几个字段
    * @param userIds
    */
    public Integer batchUpdateUsersList(Long[] userIds){
        return usersMapper.batchUpdateUsersList(userIds);
    }

    /**
    * 查询全部
    */
    public List<Users> selectUsersList(Users users){
        return usersMapper.selectUsersList(users);
    }

    /**
    * 查询全部,用于联合查询，在此基础做自己的定制改动
    */
    public List<Users> selectUnionUsersList(Users users){
        return usersMapper.selectUnionUsersList(users);
    }

    /**
     * 用作客户端用户登录
     */
    @Override
    public Users selectUsersForLogin(Users users) {
        return usersMapper.selectUsersForLogin(users);
    }

    /**
    * 根据Id查询单条数据
    */
    public Users selectUsersById(Long userId){
        return usersMapper.selectUsersById(userId);
    }
}
