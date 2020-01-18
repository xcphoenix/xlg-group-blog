package top.xcphoenix.groupblog.service.dao.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mapper.UserMapper;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.service.dao.UserService;

import java.sql.Timestamp;

/**
 * @author      xuanc
 * @date        2020/1/17 下午7:56
 * @version     1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserBlogArgs(long uid) {
        return userMapper.getUserBlogArgs(uid);
    }

    @Override
    public void updateLastPubTime(long uid, Timestamp timestamp) {
        userMapper.updateLastPubTime(timestamp, uid);
    }

    @Override
    public Timestamp getLastPubTime(long uid) {
        return userMapper.getLastPubTime(uid);
    }
}
