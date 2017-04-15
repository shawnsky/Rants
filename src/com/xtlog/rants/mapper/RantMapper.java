package com.xtlog.rants.mapper;

import com.xtlog.rants.pojo.Rant;

public interface RantMapper {
    int deleteByPrimaryKey(Integer rantId);

    int insert(Rant record);

    int insertSelective(Rant record);

    Rant selectByPrimaryKey(Integer rantId);

    int updateByPrimaryKeySelective(Rant record);

    int updateByPrimaryKey(Rant record);
}