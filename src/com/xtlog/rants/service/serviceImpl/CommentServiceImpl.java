package com.xtlog.rants.service.serviceImpl;

import com.xtlog.rants.mapper.CommentMapper;
import com.xtlog.rants.pojo.Comment;
import com.xtlog.rants.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by admin on 2017/4/16.
 */
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public int deleteByPrimaryKey(Integer commentId) {
        commentMapper.deleteByPrimaryKey(commentId);
        return 0;
    }

    @Override
    public int insert(Comment record) {
        commentMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(Comment record) {
        commentMapper.insertSelective(record);
        return 0;
    }

    @Override
    public Comment selectByPrimaryKey(Integer commentId) {
        return commentMapper.selectByPrimaryKey(commentId);
    }

    @Override
    public List<Comment> selectAllByRantId(Integer rantId) {
        return commentMapper.selectAllByRantId(rantId);
    }


    @Override
    public int updateByPrimaryKeySelective(Comment record) {
        commentMapper.updateByPrimaryKeySelective(record);
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Comment record) {
        commentMapper.updateByPrimaryKey(record);
        return 0;
    }
}
