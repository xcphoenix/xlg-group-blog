package top.xcphoenix.groupblog.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import org.springframework.web.bind.annotation.*;
import top.xcphoenix.groupblog.annotation.UserAuth;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.model.Result;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.service.api.BlogTypeService;

import java.util.List;
import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/27 下午5:45
 * @version     1.0
 */
@UserAuth
@RestController
@RequestMapping("/api")
public class BlogTypeController {

    private BlogTypeService blogTypeService;
    private UserManager userManager;

    public BlogTypeController(BlogTypeService blogTypeService, UserManager userManager) {
        this.blogTypeService = blogTypeService;
        this.userManager = userManager;
    }

    @GetMapping("/type/{typeId}/args")
    public Result<JSONArray> getTypeNeedArgs(@RequestHeader("Authorization") String token,
                                  @PathVariable("typeId") int typeId) {
        token = token.substring(7);
        Long uid = JWT.decode(token).getClaim("uid").asLong();
        String params = blogTypeService.getParams(typeId);
        User userArgs = userManager.getUserBlogArgs(uid);

        if (params == null) {
            return Result.error(-1, "无效的博客类型");
        }
        JSONArray array = JSONArray.parseArray(params);

        if (userArgs.getBlogType() == typeId) {
            Map<String, Object> values = JSONObject.parseObject(userArgs.getBlogArg()).getInnerMap();

            for (int i = 0; i < array.size(); i++) {
                String param = array.getJSONObject(i).getString("param");
                array.getJSONObject(i).put("value", values.get(param));
            }
        }

        return Result.success(array);
    }

    @GetMapping("/type/blogArgs")
    public Result<List<BlogType>> getBlogType(){
        List<BlogType> params = blogTypeService.getType();
        if(params == null || params.isEmpty()){
            return Result.error(-1,"博客类型查询出错");
        }
        return Result.success(params);
    }
}
