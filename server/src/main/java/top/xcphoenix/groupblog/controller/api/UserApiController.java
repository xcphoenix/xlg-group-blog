package top.xcphoenix.groupblog.controller.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.model.Result;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.model.dto.BlogTypeParam;
import top.xcphoenix.groupblog.service.api.BlogTypeService;
import top.xcphoenix.groupblog.service.api.LoginService;
import top.xcphoenix.groupblog.service.api.UserService;

import java.lang.reflect.Array;

/**
 * @author      xuanc
 * @date        2020/1/28 下午2:42
 * @version     1.0
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private UserService userService;
    private UserManager userManager;
    private BlogTypeService blogTypeService;

    public UserApiController(UserService userService, UserManager userManager, BlogTypeService blogTypeService) {
        this.userService = userService;
        this.userManager = userManager;
        this.blogTypeService = blogTypeService;
    }

    @GetMapping("/data")
    public Result<User> getUserData(@SessionAttribute("user") long uid) {
        User user = userService.getUserData(uid);
        if (user == null) {
            return Result.error(-1, "用户不存在");
        }
        return Result.success(null, user);
    }

    @PutMapping("/data")
    public Result<Void> updateUserData(@SessionAttribute("user") long uid,
                                 @RequestBody User user) {
        user.setUid(uid);
        if (user.getSignature() != null && user.getSignature().length() >= 25) {
            return Result.error(-1, "个性签名超出长度限制");
        }
        userService.updateUserData(user);
        return Result.success("修改成功", null);
    }

    @GetMapping("/blog/params")
    public Result<User> getUserBlogTypeData(@SessionAttribute("user") long uid) {
        User user = userManager.getUserBlogArgs(uid);
        return Result.success(user);
    }

    @PutMapping("/blog/params")
    public Result<Void> updateUserBlogParam(@SessionAttribute("user") long uid,
                                      @RequestBody BlogTypeParam blogTypeParam) {

        String needParams = blogTypeService.getParams(blogTypeParam.getBlogType());
        if (needParams == null) {
            return Result.error(-1, "无效的博客类型");
        }

        String userBlogParams = blogTypeService.validateParams(uid, blogTypeParam, needParams);
        if (userBlogParams == null) {
            return Result.error(-1, "缺少必要的参数");
        }
        userService.updateUserBlogParams(uid, blogTypeParam.getBlogType(), userBlogParams);
        return Result.success("修改成功", null);
    }

    @PutMapping("/passwd")
    public Result<Void> updateUserPasswd(@SessionAttribute("user") long uid,
                                   @RequestBody JSONObject jsonObject) {
        String currPasswd = jsonObject.getString("currentPasswd");
        String newPasswd = jsonObject.getString("newPasswd");

        if (currPasswd == null || newPasswd == null) {
            return Result.error(-1, "缺少必要的数据");
        }
        if (!userService.validatePasswd(newPasswd)) {
            return Result.error(-1, "密码不符合要求");
        }

        if (userService.checkPasswd(uid, currPasswd)) {
            userService.updatePasswd(uid, newPasswd);
            return Result.success("修改成功", null);
        } else {
            return Result.error(-1, "密码错误");
        }

    }

}
