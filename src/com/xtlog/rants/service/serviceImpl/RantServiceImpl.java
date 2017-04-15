package com.xtlog.rants.service.serviceImpl;

import com.xtlog.rants.mapper.RantMapper;
import com.xtlog.rants.pojo.Rant;
import com.xtlog.rants.service.RantService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by admin on 2017/4/16.
 */
public class RantServiceImpl implements RantService {
    @Autowired
    private RantMapper rantMapper;
    @Override
    public int deleteByPrimaryKey(Integer rantId) {
        rantMapper.deleteByPrimaryKey(rantId);
        return 0;
    }

    @Override
    public int insert(Rant record) {
        rantMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(Rant record) {
        rantMapper.insertSelective(record);
        return 0;
    }

    @Override
    public Rant selectByPrimaryKey(Integer rantId) {
        return rantMapper.selectByPrimaryKey(rantId);
    }

    @Override
    public int updateByPrimaryKeySelective(Rant record) {
        rantMapper.updateByPrimaryKeySelective(record);
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Rant record) {
        rantMapper.updateByPrimaryKey(record);
        return 0;
    }
}
