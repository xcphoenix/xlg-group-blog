package top.xcphoenix.groupblog.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import top.xcphoenix.groupblog.annotation.UserAuth;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.model.Result;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.service.api.BlogTypeService;

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
    public Result<JSONArray> getTypeNeedArgs(@SessionAttribute("user") User user,
                                  @PathVariable("typeId") int typeId) {
        String params = blogTypeService.getParams(typeId);
        User userArgs = userManager.getUserBlogArgs(user.getUid());

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


}
