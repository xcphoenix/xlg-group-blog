package top.xcphoenix.groupblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.xcphoenix.groupblog.model.vo.SiteSchema;
import top.xcphoenix.groupblog.service.view.SiteService;

import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/15 下午4:46
 * @version     1.0
 */
@Controller
public class HelloController {

    private SiteService siteService;

    public HelloController(SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping("/")
    public String hello(Map<String, Object> map) throws CloneNotSupportedException {
        // SiteSchema siteSchema = siteService.getSiteSchemaWithUser(10074L);
        SiteSchema siteSchema = siteService.getSiteSchema();
        map.put("siteSchema", siteSchema);
        return "index";
    }

}
