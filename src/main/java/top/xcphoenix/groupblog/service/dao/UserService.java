package top.xcphoenix.groupblog.service.dao;

import top.xcphoenix.groupblog.model.dao.User;

import java.sql.Timestamp;

/**
 * @author      xuanc
 * @date        2020/1/17 下午7:49
 * @version     1.0
 */ 
public interface UserService {
    /**
     * 获取用户博客参数
     *
     * @param uid 用户id
     * @return 博客参数
     */
    User getUserBlogArgs(long uid);

    /**
     * 更新最新发布的博客时间
     * @param uid 用户id
     * @param timestamp 时间
     */
    void updateLastPubTime(long uid, Timestamp timestamp);

    /**
     * 获取用户博客的最新更新时间
     *
     * @param uid 用户id
     * @return 最近博客的推送时间
     */
    Timestamp getLastPubTime(long uid);

}
