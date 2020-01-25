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
     * 获取用户描述：用户名、用户个性签名
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

}
