package top.xcphoenix.groupblog.service.api;

import top.xcphoenix.groupblog.model.dao.User;

/**
 * @author      xuanc
 * @date        2020/1/28 下午1:53
 * @version     1.0
 */ 
public interface LoginService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 用户id、权限信息
     */
    User login(String username, String password);

}
