package com.xtlog.rants.mapper;

import com.xtlog.rants.pojo.Comment;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer commentId);

    List<Comment> selectAllByRantId(Integer rantId);

    List<Comment> selectAllByUserId(Integer userId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectAll();
}