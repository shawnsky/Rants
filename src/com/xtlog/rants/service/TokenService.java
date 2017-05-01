package com.xtlog.rants.service;

import com.xtlog.rants.pojo.Token;

/**
 * Created by admin on 2017/4/30.
 */
public interface TokenService {
    int create(Token record);
    int deleteById(Integer id);
    int deleteByToken(String token);
    int update(Token record);
    int queryIdByToken(String token);
    String queryTokenById(Integer id);
}
