package top.xcphoenix.groupblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.bouncycastle.util.Times;
import top.xcphoenix.groupblog.model.dao.User;

import java.sql.Timestamp;

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

}
