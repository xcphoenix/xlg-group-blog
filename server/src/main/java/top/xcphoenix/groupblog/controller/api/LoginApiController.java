package top.xcphoenix.groupblog.controller.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import top.xcphoenix.groupblog.model.Result;
import top.xcphoenix.groupblog.service.api.LoginService;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/28 下午2:00
 * @version     1.0
 */
@RestController
@RequestMapping("/api")
public class LoginApiController {

    private LoginService loginService;

    public LoginApiController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody JSONObject jsonObject, HttpSession session) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        if (username == null || username.length() == 0) {
            return Result.error(-1, "用户名不能为空");
        }
        if (password == null || password.length() == 0) {
            return Result.error(-1, "密码不能为空");
        }

        Long uid = loginService.login(username, password);
        if (uid == null) {
            return Result.error(-1, "用户名或密码输入错误");
        } else {
            session.setAttribute("user", uid);
            return Result.success("登录成功", uid);
        }
    }

}
