package com.xtlog.rants.service.serviceImpl;

import com.xtlog.rants.mapper.StarMapper;
import com.xtlog.rants.pojo.Star;
import com.xtlog.rants.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by admin on 2017/4/19.
 */
public class StarServiceImpl implements StarService {
    @Autowired
    private StarMapper starMapper;
    @Override
    public int deleteByPrimaryKey(Integer starId) {
        return starMapper.deleteByPrimaryKey(starId);
    }

    @Override
    public int insert(Star record) {
        return starMapper.insert(record);
    }

    @Override
    public Star selectByPrimaryKey(Integer starId) {
        return starMapper.selectByPrimaryKey(starId);
    }

    @Override
    public List<Star> selectByRantId(Integer rantId) {
        return starMapper.selectByRantId(rantId);
    }

    @Override
    public List<Star> selectByUserId(Integer userId) {
        return starMapper.selectByUserId(userId);
    }

    @Override
    public List<Star> selectAll() {
        return starMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Star record) {
        return starMapper.updateByPrimaryKey(record);
    }
}
