package com.xtlog.rants.service.serviceImpl;

import com.xtlog.rants.mapper.UserMapper;
import com.xtlog.rants.pojo.User;
import com.xtlog.rants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by admin on 2017/4/15.
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer userId) {
        userMapper.deleteByPrimaryKey(userId);
        return 0;
    }

    @Override
    public int insert(User record) {
        userMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(User record) {
        userMapper.insertSelective(record);
        return 0;
    }

    @Override
    public User selectByPrimaryKey(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        userMapper.updateByPrimaryKeySelective(record);
        return 0;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        userMapper.updateByPrimaryKey(record);
        return 0;
    }
}
