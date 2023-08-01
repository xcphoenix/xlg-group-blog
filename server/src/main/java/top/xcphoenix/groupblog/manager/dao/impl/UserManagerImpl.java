package top.xcphoenix.groupblog.manager.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mybatis.mapper.UserMapper;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.model.dto.UserSummary;
import top.xcphoenix.groupblog.utils.UrlUtil;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/17 下午7:56
 * @version     1.0
 */
@Service
@Slf4j
@PropertySource(value = "${config-dir}/processor.properties",encoding = "utf-8")
public class UserManagerImpl implements UserManager {

    private UserMapper userMapper;
    @Value("${picture.service-name}")
    private String serviceName;
    public UserManagerImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void createNewUser(User user) {
        userMapper.createNewUser(user);
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
        String avatar = userMapper.selectUserAvatar(uid);
        if(avatar != null) {
            avatar = "https://"+serviceName+avatar;
            log.info(avatar);
            if (UrlUtil.imgUrlValidate(avatar)) {
                return avatar;
            } else {
                log.info("https请求发送失败：头像无法找到。");
                avatar = avatar.replace("https:", "http:");
                if (UrlUtil.imgUrlValidate(avatar)) {
                    return avatar;
                }
                log.info("http请求发送失败：头像无法找到。");
            }
        }
        log.info("使用qq头像");
        String qq = userMapper.getUserQQ(uid);
        if (qq == null) {
            return "/images/anonymous.svg";
        }
        return "https://q1.qlogo.cn/g?b=qq&nk=" + qq + "&s=100";
    }

    @Override
    public List<User> getUsersInfo(@Param("cid") long cid){
        return userMapper.getUsersInfo(cid);
    }

    @Override
    public UserSummary getUserSummaryByUid(long uid) {
        return userMapper.getUserSummaryByUid(uid);
    }

    @Override
    public User checkUser(long uid) {
        return userMapper.checkUser(uid);
    }


}
