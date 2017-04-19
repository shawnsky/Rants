package com.xtlog.rants.mapper;

import com.xtlog.rants.pojo.Rant;

import java.util.List;

public interface RantMapper {
    int deleteByPrimaryKey(Integer rantId);

    int insert(Rant record);

    int insertSelective(Rant record);

    Rant selectByPrimaryKey(Integer rantId);

    List<Rant> selectAll();

    List<Rant> selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(Rant record);

    int updateByPrimaryKey(Rant record);
}