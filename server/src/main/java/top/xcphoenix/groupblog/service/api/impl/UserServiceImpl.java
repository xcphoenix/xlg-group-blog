package top.xcphoenix.groupblog.service.api.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mybatis.mapper.BlogMapper;
import top.xcphoenix.groupblog.mybatis.mapper.UserMapper;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.service.api.UserService;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author      xuanc
 * @date        2020/1/28 下午2:52
 * @version     1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private BlogMapper blogMapper;
    private Pattern pattern = Pattern.compile("^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$");

    public UserServiceImpl(UserMapper userMapper, BlogMapper blogMapper) {
        this.userMapper = userMapper;
        this.blogMapper = blogMapper;
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
        User user = userMapper.getUserBlogArgs(uid);
        if (user.getBlogType() != blogType || !user.getBlogArg().equals(blogParams)) {
            // 重设时间
            userMapper.resetPubTime(uid, new Timestamp(0));
            userMapper.updateUserBlogParams(uid, blogType, blogParams);
        }
    }

    @Override
    public boolean validatePasswd(String ... passwdArray) {
        Matcher matcher;
        for (String passwd : passwdArray) {
            matcher = pattern.matcher(passwd);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkPasswd(long uid, String passwd) {
        return userMapper.checkPasswd(uid, passwd) == 1;
    }

    @Override
    public void updatePasswd(long uid, String newPasswd) {
        userMapper.updatePasswd(uid, newPasswd);
    }

}
