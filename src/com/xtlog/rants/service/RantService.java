package com.xtlog.rants.service;

import com.xtlog.rants.pojo.Rant;

import java.util.List;

/**
 * Created by admin on 2017/4/15.
 */
public interface RantService {
    int deleteByPrimaryKey(Integer rantId);

    int insert(Rant record);

    int insertSelective(Rant record);

    Rant selectByPrimaryKey(Integer rantId);

    List<Rant> selectAll();

    int updateByPrimaryKeySelective(Rant record);

    int updateByPrimaryKey(Rant record);
}
