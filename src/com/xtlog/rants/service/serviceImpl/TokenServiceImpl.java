package com.xtlog.rants.service.serviceImpl;

import com.xtlog.rants.mapper.TokenMapper;
import com.xtlog.rants.pojo.Token;
import com.xtlog.rants.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by admin on 2017/4/30.
 */
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenMapper tokenMapper;

    @Override
    public int create(Token record) {
        return tokenMapper.create(record);
    }

    @Override
    public int deleteById(Integer id) {
        return tokenMapper.deleteById(id);
    }

    @Override
    public int deleteByToken(String token) {
        return tokenMapper.deleteByToken(token);
    }

    @Override
    public int update(Token record) {
        return tokenMapper.update(record);
    }

    @Override
    public int queryIdByToken(String token) {
        return tokenMapper.queryIdByToken(token);
    }

    @Override
    public String queryTokenById(Integer id) {
        return tokenMapper.queryTokenById(id);
    }
}
