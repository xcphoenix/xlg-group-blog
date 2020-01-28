package top.xcphoenix.groupblog.service.api;

import top.xcphoenix.groupblog.model.dao.User;

/**
 * @author      xuanc
 * @date        2020/1/28 下午2:48
 * @version     1.0
 */
public interface UserService {

    /**
     * 获取用户基本数据
     *
     * @param uid 用户id
     * @return 用户名、QQ号、个性签名、博客类型、博客参数
     */
    User getUserData(long uid);

    /**
     * 更新用户信息（QQ、个性签名）
     *
     * @param user 用户信息
     */
    void updateUserData(User user);

    /**
     * 更新用户博客参数
     *
     * @param uid 用户id
     * @param blogType 博客类型
     * @param blogParams 博客参数
     */
    void updateUserBlogParams(long uid, int blogType, String blogParams);

}
