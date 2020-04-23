package top.xcphoenix.groupblog.controller.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xcphoenix.groupblog.annotation.AdminAuth;
import top.xcphoenix.groupblog.model.Result;

/**
 * @author      xuanc
 * @date        2020/3/1 下午10:41
 * @version     1.0
 */
@AdminAuth
@RestController
@RequestMapping("/api/sync")
public class SyncBlogController {

    @PutMapping("/blog/{blogId}")
    public Result<Void> syncBlog(@PathVariable("blogId") long blogId) {
        return null;
    }

}
