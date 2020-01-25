package top.xcphoenix.groupblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.vo.*;
import top.xcphoenix.groupblog.service.view.*;

import javax.servlet.http.HttpServletRequest;
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
    private AboutService aboutService;
    private PaginationService paginationService;
    private LinkGeneratorService linkGeneratorService;

    public IndexController(SiteService siteService, BlogDataService blogDataService, AboutService aboutService, PaginationService paginationService, LinkGeneratorService linkGeneratorService) {
        this.siteService = siteService;
        this.blogDataService = blogDataService;
        this.aboutService = aboutService;
        this.paginationService = paginationService;
        this.linkGeneratorService = linkGeneratorService;
    }

    @GetMapping("/")
    public String index(Map<String, Object> map,
                        HttpServletRequest request,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                int pageSize,
                        @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                                int pageNum) {
        SiteSchema siteSchema = siteService.getSiteSchema();
        List<BlogData> blogDataList = blogDataService.getBlogDataForIndex(pageNum, pageSize);
        Pagination pagination = paginationService.getPagination(pageNum, pageSize,
                linkGeneratorService.getIndexLinkPrefix());

        map.put("siteSchema", siteSchema);
        map.put("blogDataList", blogDataList);
        if (blogDataList.size() != 0) {
            map.put("page", pagination);
        }
        map.put("pageType", request.getParameterMap().size() == 0 ?
                    PageType.INDEX :
                    PageType.OVERVIEW);

        return "index";
    }

    @GetMapping("/about")
    public String about(Map<String, Object> map) {
        SiteSchema siteSchema = siteService.getSiteSchema();
        PostData postData = aboutService.getAbout();

        map.put("siteSchema", siteSchema);
        map.put("postData", postData);
        map.put("pageType", PageType.ABOUT);

        return "about";
    }

    @GetMapping("/test")
    public String test(Map<String, Object> map) {
        map.put("pageType", PageType.ABOUT);
        return "test";
    }

}
