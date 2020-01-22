package top.xcphoenix.groupblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.vo.BlogData;
import top.xcphoenix.groupblog.model.vo.PageType;
import top.xcphoenix.groupblog.model.vo.Pagination;
import top.xcphoenix.groupblog.model.vo.SiteSchema;
import top.xcphoenix.groupblog.service.view.BlogDataService;
import top.xcphoenix.groupblog.service.view.SiteService;

import java.util.List;
import java.util.Map;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/15 下午4:46
 */
@Controller
public class IndexController {

    private SiteService siteService;
    private BlogDataService blogDataService;

    public IndexController(SiteService siteService, BlogDataService blogDataService) {
        this.siteService = siteService;
        this.blogDataService = blogDataService;
    }

    @GetMapping("/")
    public String index(Map<String, Object> map,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                int pageSize,
                        @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                                int pageNum) {
        SiteSchema siteSchema = siteService.getSiteSchema();
        List<BlogData> blogDataList = blogDataService.getBlogDataForIndex(pageNum, pageSize);
        Pagination pagination = blogDataService.getPagination(pageNum, pageSize, "/");

        map.put("siteSchema", siteSchema);
        map.put("blogDataList", blogDataList);
        map.put("page", pagination);
        map.put("pageType", PageType.OVERVIEW);

        return "index";
    }

    @GetMapping("/user/{userId}")
    public String userZone(Map<String, Object> map,
                           @PathVariable("userId") long uid,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                   int pageSize,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                                   int pageNum) {
        return "userZone";
    }

    @GetMapping("/blog/{blogId}")
    public String blog(Map<String, Object> map, @PathVariable("blogId") long blogId) throws CloneNotSupportedException {
        BlogData blogData = blogDataService.getBlogById(blogId);
        List<Blog> nearbyBlogs = blogDataService.getNearbyBlogs(blogData.getPubTime());
        long uid = blogData.getUid();
        SiteSchema siteSchema = siteService.getSiteSchemaWithUser(uid);

        map.put("siteSchema", siteSchema);
        map.put("blogData", blogData);
        map.put("nearbyBlogs", nearbyBlogs);
        map.put("pageType", PageType.POST);

        return "post";
    }

    @GetMapping("/category/{categoryId}")
    public String category(Map<String, Object> map,
                           @PathVariable("categoryId") int categoryId,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                       int pageSize,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                                       int pageNum) {
        return "category";
    }

    @GetMapping("/tag/{tagId}")
    public String tag(Map<String, Object> map,
                      @PathVariable("tagId") int tagId,
                      @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                  int pageSize,
                      @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                                  int pageNum) {
        return null;
    }

}
