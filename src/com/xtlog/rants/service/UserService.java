package com.xtlog.rants.service;

import com.xtlog.rants.pojo.User;

import java.util.List;

/**
 * Created by admin on 2017/4/15.
 */
public interface UserService {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    List<User> selectAll();

    User selectByUserName(String userName);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
//    updateByPrimaryKey对你注入的字段全部更新（不判断是否为Null）
//    updateByPrimaryKeySelective会对字段进行判断再更新(如果为Null就忽略更新)
}
