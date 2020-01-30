package top.xcphoenix.groupblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.bouncycastle.util.Times;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.model.dto.UserSummary;
import top.xcphoenix.groupblog.model.vo.UserItem;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/15 下午1:28
 */
public interface UserMapper {

    /**
     * 获取用户博客参数
     *
     * @param uid 用户id
     * @return 用户设置的博客参数
     */
    User getUserBlogArgs(@Param("uid") long uid);

    /**
     * 重设博客更新时间
     *
     * @param uid 用户id
     * @param resetTime 重设时间
     */
    void resetPubTime(@Param("uid") long uid, @Param("resetTime") Timestamp resetTime);

    /**
     * 更新最新博客时间
     *
     * @param uid         用户id
     * @param lastPubTime 最新博客
     */
    void updateLastPubTime(@Param("lastPubTime") Timestamp lastPubTime, @Param("uid") long uid);

    /**
     * 获取用户博客的最新更新时间
     *
     * @param uid 用户id
     * @return 最近博客的推送时间
     */
    Timestamp getLastPubTime(@Param("uid") long uid);

    /**
     * 获取用户概要
     *
     * @return 用户id, 用户对应博客的执行bean
     */
    List<UserSummary> getUsersSummary();

    /**
     * 获取用户描述：用户名、QQ、用户个性签名
     *
     * @param uid 用户id
     * @return 用户描述
     */
    User getUserDesc(@Param("uid") long uid);

    /**
     * 获取用户 QQ
     *
     * @param uid 用户id
     * @return 用户QQ
     */
    String getUserQQ(@Param("uid") long uid);

    /**
     * 获取分类用户数据元素
     *
     * @param categoryId 分类id
     * @return 用户数据
     */
    List<UserItem> getUserItem(@Param("categoryId") int categoryId);

    /**
     * 更新用户 qq、个性签名
     *
     * @param user 用户信息
     */
    void updateUserDesc(@Param("user") User user);

    /**
     * 更新博客参数
     *
     * @param uid        用户id
     * @param blogType   博客类型
     * @param blogParams 博客参数
     */
    void updateUserBlogParams(@Param("uid") long uid, @Param("blogType") int blogType,
                              @Param("blogParams") String blogParams);

    /**
     * 校验密码
     *
     * @param uid    用户id
     * @param passwd 密码
     * @return 密码是否正确
     */
    int checkPasswd(@Param("uid") long uid, @Param("passwd") String passwd);

    /**
     * 更新用户密码
     *
     * @param uid       用户id
     * @param newPasswd 新的密码
     */
    void updatePasswd(@Param("uid") long uid, @Param("newPasswd") String newPasswd);

}
