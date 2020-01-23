package top.xcphoenix.groupblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.xcphoenix.groupblog.service.view.AboutService;
import top.xcphoenix.groupblog.service.view.BlogDataService;
import top.xcphoenix.groupblog.service.view.SiteService;

import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/23 下午3:36
 * @version     1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private SiteService siteService;
    private BlogDataService blogDataService;
    private AboutService aboutService;

    public UserController(SiteService siteService, BlogDataService blogDataService, AboutService aboutService) {
        this.siteService = siteService;
        this.blogDataService = blogDataService;
        this.aboutService = aboutService;
    }

    @GetMapping("/{userId}")
    public String userZone(Map<String, Object> map,
                           @PathVariable("userId") long uid,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                   int pageSize,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                                   int pageNum) {
        return "userZone";
    }

}
