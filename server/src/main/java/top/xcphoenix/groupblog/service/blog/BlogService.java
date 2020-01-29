package top.xcphoenix.groupblog.service.blog;

import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;

import java.text.ParseException;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/15 下午3:25
 */
public interface BlogService {

    /**
     * 全量更新
     *
     * @param user 用户信息
     * @param blogType 博客类型
     */
    void execFull(User user, BlogType blogType);

    /**
     * 增量更新
     *
     * @param user 用户信息
     * @param blogType 博客类型
     */
    void execIncrement(User user, BlogType blogType);

}
