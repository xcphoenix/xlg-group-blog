package top.xcphoenix.groupblog.mapper;

import org.apache.ibatis.annotations.Param;
import top.xcphoenix.groupblog.model.dao.User;

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

}
