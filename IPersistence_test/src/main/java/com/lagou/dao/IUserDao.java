package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/25
 * Time: 5:08 PM
 *
 * @author kevliu3
 */
public interface IUserDao {

    //查询所有用户
    List<User> findAll() throws Exception;

    //根据条件进行查询
    User findByCondition(User user) throws Exception;


    //根据条件进行查询
    void saveUser(User user) throws Exception;

    void updateUser(User user) throws Exception;

    void deleteUser(User user) throws Exception;


}