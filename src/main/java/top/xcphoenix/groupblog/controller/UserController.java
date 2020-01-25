package top.xcphoenix.groupblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.vo.BlogData;
import top.xcphoenix.groupblog.model.vo.PageType;
import top.xcphoenix.groupblog.model.vo.Pagination;
import top.xcphoenix.groupblog.model.vo.SiteSchema;
import top.xcphoenix.groupblog.service.view.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
    private PaginationService paginationService;
    private LinkGeneratorService linkGeneratorService;

    public UserController(SiteService siteService, BlogDataService blogDataService, PaginationService paginationService, LinkGeneratorService linkGeneratorService) {
        this.siteService = siteService;
        this.blogDataService = blogDataService;
        this.paginationService = paginationService;
        this.linkGeneratorService = linkGeneratorService;
    }

    @GetMapping("/{userId}")
    public String userZone(Map<String, Object> map,
                           @PathVariable("userId") long uid,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                   int pageSize,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                                   int pageNum) throws CloneNotSupportedException {
        SiteSchema siteSchema = siteService.getSiteSchemaWithUser(uid);
        List<BlogData> blogDataList = blogDataService.getBlogDataByUser(uid, pageNum, pageSize);
        Pagination pagination = paginationService.getPaginationAsUser(pageNum, pageSize,
                linkGeneratorService.getUserLink(uid), uid);

        map.put("siteSchema", siteSchema);
        map.put("blogDataList", blogDataList);
        map.put("page", pagination);
        map.put("pageType", PageType.OVERVIEW);

        return "index";
    }

    @GetMapping("/{userId}/blog/{blogId}")
    public String blog(Map<String, Object> map,
                       @PathVariable("userId") long userId,
                       @PathVariable("blogId") long blogId) throws CloneNotSupportedException {
        BlogData blogData = blogDataService.getBlogById(blogId);
        List<Blog> nearbyBlogs = blogDataService.getNearbyBlogsAsUser(blogData.getPubTime(), userId);
        long uid = blogData.getUid();
        SiteSchema siteSchema = siteService.getSiteSchemaWithUser(uid);

        map.put("siteSchema", siteSchema);
        map.put("blogData", blogData);
        map.put("nearbyBlogs", nearbyBlogs);
        map.put("pageType", PageType.POST);

        return "post";
    }

}
