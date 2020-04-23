package top.xcphoenix.groupblog.manager.dao.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.manager.dao.StaticsNumsManager;
import top.xcphoenix.groupblog.mybatis.mapper.StaticsNumsMapper;
import top.xcphoenix.groupblog.model.dto.NumStatics;

/**
 * @author      xuanc
 * @date        2020/1/20 上午11:32
 * @version     1.0
 */
@Service
public class StaticsNumsManagerImpl implements StaticsNumsManager {

    private StaticsNumsMapper staticsNumsMapper;

    public StaticsNumsManagerImpl(StaticsNumsMapper staticsNumsMapper) {
        this.staticsNumsMapper = staticsNumsMapper;
    }

    @Override
    public NumStatics getSiteStaticsNum() {
        return staticsNumsMapper.getSiteStaticsNum();
    }

    @Override
    public int getUserStaticsNum(long uid) {
        return staticsNumsMapper.getUserStaticsNum(uid);
    }

}
