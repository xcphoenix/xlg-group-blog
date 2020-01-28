package top.xcphoenix.groupblog.manager.dao.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mapper.UserMapper;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.model.dto.UserSummary;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/17 下午7:56
 * @version     1.0
 */
@Service
public class UserManagerImpl implements UserManager {

    private UserMapper userMapper;

    public UserManagerImpl(UserMapper userMapper) {
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

    @Override
    public List<UserSummary> getUsersSummary() {
        return userMapper.getUsersSummary();
    }

    @Override
    public User getUserDesc(long uid) {
        return userMapper.getUserDesc(uid);
    }

    @Override
    public String getUserAvatar(long uid) {
        String qq = userMapper.getUserQQ(uid);
        if (qq == null) {
            return "/images/anonymous.svg";
        }
        return "http://q1.qlogo.cn/g?b=qq&nk=" + qq + "&s=100";
    }

}
