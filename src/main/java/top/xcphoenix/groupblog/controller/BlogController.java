package top.xcphoenix.groupblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.vo.BlogData;
import top.xcphoenix.groupblog.model.vo.PageType;
import top.xcphoenix.groupblog.model.vo.SiteSchema;
import top.xcphoenix.groupblog.service.view.AboutService;
import top.xcphoenix.groupblog.service.view.BlogDataService;
import top.xcphoenix.groupblog.service.view.SiteService;

import java.util.List;
import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/23 下午3:34
 * @version     1.0
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    private SiteService siteService;
    private BlogDataService blogDataService;

    public BlogController(SiteService siteService, BlogDataService blogDataService) {
        this.siteService = siteService;
        this.blogDataService = blogDataService;
    }

    @GetMapping("/{blogId}")
    public String blog(Map<String, Object> map, @PathVariable("blogId") long blogId) throws CloneNotSupportedException {
        BlogData blogData = blogDataService.getBlogById(blogId);
        List<Blog> nearbyBlogs = blogDataService.getNearbyBlogs(blogData.getPubTime());
        SiteSchema siteSchema = siteService.getSiteSchemaWithUser(blogData.getUid());

        map.put("siteSchema", siteSchema);
        map.put("blogData", blogData);
        map.put("nearbyBlogs", nearbyBlogs);
        map.put("pageType", PageType.POST);

        return "post";
    }

}
