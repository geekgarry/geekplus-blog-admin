package com.geekplus.webapp.function.mapper;

import com.geekplus.webapp.function.entity.Users;
import java.util.List;

/**
 * 用户信息表 用户信息表
 * Created by CodeGenerator on 2024/09/20.
 */
public interface UsersMapper {

    /**
    * 增加
    * @param users
    * @return 用户信息表
    */
    Integer insertUsers(Users users);

    /**
    * 批量增加
    * @param usersList
    * @return
    */
    public int batchInsertUsersList(List<Users> usersList);

    /**
    * 删除
    * @param userId
    */
    Integer deleteUsersById(Long userId);

    /**
    * 批量删除
    */
    Integer deleteUsersByIds(Long[] userIds);

    /**
    * 修改
    * @param users
    */
    Integer updateUsers(Users users);

    /**
    * 批量修改魔偶几个字段
    * @param userIds
    */
    Integer batchUpdateUsersList(Long[] userIds);

    /**
    * 查询全部
    */
    List<Users> selectUsersList(Users users);

    /**
    * 查询全部,联合查询使用
    */
    List<Users> selectUnionUsersList(Users users);

    /**
    * 用作客户端用户登录
    */
    Users selectUsersForLogin(Users users);

    /**
    * 根据Id查询单条数据
    */
    Users selectUsersById(Long userId);
}
