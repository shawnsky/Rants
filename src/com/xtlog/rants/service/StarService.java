package com.xtlog.rants.service;

import com.xtlog.rants.pojo.Star;

import java.util.List;

/**
 * Created by admin on 2017/4/19.
 */
public interface StarService {
    int deleteByPrimaryKey(Integer starId);

    int insert(Star record);

    Star selectByPrimaryKey(Integer starId);

    List<Star> selectByRantId(Integer rantId);

    List<Star> selectByUserId(Integer userId);

    List<Star> selectAll();

    int updateByPrimaryKey(Star record);
}

