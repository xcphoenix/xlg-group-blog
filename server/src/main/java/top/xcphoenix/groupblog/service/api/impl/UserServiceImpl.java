package top.xcphoenix.groupblog.service.api.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mapper.UserMapper;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.service.api.UserService;

/**
 * @author      xuanc
 * @date        2020/1/28 下午2:52
 * @version     1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserData(long uid) {
        return userMapper.getUserDesc(uid);
    }

    @Override
    public void updateUserData(User user) {
        userMapper.updateUserDesc(user);
    }

    @Override
    public void updateUserBlogParams(long uid, int blogType, String blogParams) {
        userMapper.updateUserBlogParams(uid, blogType, blogParams);
    }
}
