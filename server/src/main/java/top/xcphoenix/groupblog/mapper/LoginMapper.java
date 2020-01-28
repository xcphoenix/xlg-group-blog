package top.xcphoenix.groupblog.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/28 下午1:50
 */
public interface LoginMapper {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户 uid
     */
    Long login(@Param("username") String username, @Param("password") String password);

}
