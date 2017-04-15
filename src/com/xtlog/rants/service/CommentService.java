package com.xtlog.rants.service;

import com.xtlog.rants.pojo.Comment;

/**
 * Created by admin on 2017/4/15.
 */
public interface CommentService {
    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}
