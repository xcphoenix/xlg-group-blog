package top.xcphoenix.groupblog.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.xcphoenix.groupblog.model.vo.PageType;
import top.xcphoenix.groupblog.model.vo.Pagination;
import top.xcphoenix.groupblog.model.vo.SearchData;
import top.xcphoenix.groupblog.model.vo.SiteSchema;
import top.xcphoenix.groupblog.service.view.LinkGeneratorService;
import top.xcphoenix.groupblog.service.view.PaginationService;
import top.xcphoenix.groupblog.service.view.SearchService;
import top.xcphoenix.groupblog.service.view.SiteService;

import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/26 下午8:06
 * @version     1.0
 */
@Controller
public class SearchController {

    private SiteService siteService;
    private SearchService searchService;
    private PaginationService paginationService;
    private LinkGeneratorService linkGeneratorService;

    public SearchController(SiteService siteService, SearchService searchService, PaginationService paginationService, LinkGeneratorService linkGeneratorService) {
        this.siteService = siteService;
        this.searchService = searchService;
        this.paginationService = paginationService;
        this.linkGeneratorService = linkGeneratorService;
    }

    @GetMapping("/search")
    public String searchIndex(Map<String, Object> map,
                              @RequestParam("keyword") String keyword,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                          int pageSize,
                              @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                                          int pageNum) {
        SiteSchema siteSchema = siteService.getSiteSchema();
        SearchData searchData = searchService.searchAsKeyword(keyword, pageSize, (pageNum - 1) * pageSize);
        Pagination pagination = paginationService.getPaginationAsSearch(pageNum, pageSize,
                linkGeneratorService.getSearchLink(keyword), keyword);

        map.put("siteSchema", siteSchema);
        map.put("searchData", searchData);
        map.put("page", pagination);
        map.put("pageType", PageType.SEARCH);

        return "search";
    }


}
