package top.xcphoenix.groupblog.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.xcphoenix.groupblog.model.dto.ArchiveBlogs;
import top.xcphoenix.groupblog.model.vo.PageType;
import top.xcphoenix.groupblog.model.vo.Pagination;
import top.xcphoenix.groupblog.model.vo.SiteSchema;
import top.xcphoenix.groupblog.service.view.ArchiveService;
import top.xcphoenix.groupblog.service.view.LinkGeneratorService;
import top.xcphoenix.groupblog.service.view.PaginationService;
import top.xcphoenix.groupblog.service.view.SiteService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/25 下午10:48
 */
@Controller
public class ArchiveController {

    private SiteService siteService;
    private ArchiveService archiveService;
    private PaginationService paginationService;
    private LinkGeneratorService linkGeneratorService;

    public ArchiveController(SiteService siteService, ArchiveService archiveService, PaginationService paginationService, LinkGeneratorService linkGeneratorService) {
        this.siteService = siteService;
        this.archiveService = archiveService;
        this.paginationService = paginationService;
        this.linkGeneratorService = linkGeneratorService;
    }

    @GetMapping("/archive")
    public String archive(Map<String, Object> map,
                          HttpServletRequest request,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                  int pageSize,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                                  int pageNum) {
        SiteSchema siteSchema = siteService.getSiteSchema();
        List<ArchiveBlogs> archiveBlogs = archiveService.getArchive(pageNum, pageSize);
        Pagination pagination = paginationService.getPagination(pageNum, pageSize,
                linkGeneratorService.getArchiveLinkPrefix());

        map.put("siteSchema", siteSchema);
        map.put("archiveData", archiveBlogs);
        map.put("page", pagination);
        map.put("pageType", PageType.ARCHIVE);

        return "archive";
    }

    @GetMapping("/archive/user/{uid}")
    public String archive(Map<String, Object> map,
                          @PathVariable("uid") long uid,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                  int pageSize,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                                  int pageNum) throws CloneNotSupportedException {
        SiteSchema siteSchema = siteService.getSiteSchemaWithUser(uid);
        List<ArchiveBlogs> archiveBlogs = archiveService.getArchiveAsUser(uid, pageNum, pageSize);
        Pagination pagination = paginationService.getPaginationAsUser(pageNum, pageSize,
                linkGeneratorService.getArchiveLinkAsUser(uid), uid);

        map.put("siteSchema", siteSchema);
        map.put("archiveData", archiveBlogs);
        map.put("archiveHiddenUser", true);
        map.put("page", pagination);
        map.put("pageType", PageType.ARCHIVE);

        return "archive";
    }

}
