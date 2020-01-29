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

    /**
     * 正则校验密码是否符合要求
     *
     * @param passwdArray 密码数组
     * @return 是否合法
     */
    boolean validatePasswd(String ... passwdArray);

    /**
     * 检查密码是否匹配
     *
     * @param uid 用户id
     * @param passwd 用户密码
     * @return 是否匹配
     */
    boolean checkPasswd(long uid, String passwd);

    /**
     * 更新用户密码
     *
     * @param uid 用户id
     * @param newPasswd 新密码
     */
    void updatePasswd(long uid, String newPasswd);

}
