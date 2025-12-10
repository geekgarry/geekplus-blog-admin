package com.geekplus.webapp.function.service;

import com.geekplus.webapp.function.entity.Users;
//import com.geekplus.core.Service;
import java.util.List;


/**
 * 用户信息表 用户信息表
 * Created by CodeGenerator on 2024/09/20.
 */
public interface UsersService {

    /**
    * 增加
    * @param users
    * @return 用户信息表
    */
    public Integer insertUsers(Users users);

    /**
    * 批量增加
    * @param usersList
    * @return 用户信息表
    */
    public Integer batchInsertUsersList(List<Users> usersList);

    /**
    * 删除
    * @param userId
    */
    public Integer deleteUsersById(Long userId);

    /**
    * 批量删除某几个字段
    */
    public Integer deleteUsersByIds(Long[] userIds);

    /**
    * 修改
    * @param users
    */
    public Integer updateUsers(Users users);

    /**
    * 批量修改
    * @param userIds
    */
    public Integer batchUpdateUsersList(Long[] userIds);

    /**
    * 查询全部
    */
    public List<Users> selectUsersList(Users users);

    /**
    * 查询全部，用作联合查询使用(在基础上修改即可)
    */
    public List<Users> selectUnionUsersList(Users users);

    /**
     * 用作客户端用户登录
     */
    public Users selectUsersForLogin(Users users);

    /**
    * 根据Id查询单条数据
    */
    public Users selectUsersById(Long userId);
}
