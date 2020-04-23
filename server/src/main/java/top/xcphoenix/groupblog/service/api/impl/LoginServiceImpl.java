package top.xcphoenix.groupblog.service.api.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.mybatis.mapper.LoginMapper;
import top.xcphoenix.groupblog.service.api.LoginService;

/**
 * @author      xuanc
 * @date        2020/1/28 下午1:59
 * @version     1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    private LoginMapper loginMapper;

    public LoginServiceImpl(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    public User login(String username, String password) {
        return loginMapper.login(username, password);
    }

}
