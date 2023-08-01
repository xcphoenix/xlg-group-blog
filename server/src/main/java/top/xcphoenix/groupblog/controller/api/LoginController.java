package top.xcphoenix.groupblog.controller.api;

import com.alibaba.fastjson.JSONObject;
import org.openqa.selenium.remote.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import top.xcphoenix.groupblog.annotation.UserAuth;
import top.xcphoenix.groupblog.model.Result;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.service.api.LoginService;
import top.xcphoenix.groupblog.utils.TokenUtil;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/28 下午2:00
 * @version     1.0
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        if (username == null || username.length() == 0) {
            return Result.error(-1, "用户名不能为空");
        }
        if (password == null || password.length() == 0) {
            return Result.error(-1, "密码不能为空");
        }

        User user = loginService.login(username, password);
        if (user == null) {
            return Result.error(-1, "用户名或密码输入错误");
        } else {
            user.setPassword(password);
            String token = TokenUtil.getToken(user);
            return Result.success("登录成功", token);
        }
    }

    @UserAuth
    @PostMapping("/logout")
    public Result<Void> logout(HttpRequest request) {
        //todo: 清空token
        return Result.success("注销成功", null);
    }

}
