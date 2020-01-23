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
 * @date        2020/1/23 下午3:37
 * @version     1.0
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @GetMapping("/{categoryId}")
    public String category(Map<String, Object> map,
                           @PathVariable("categoryId") int categoryId,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                                   int pageSize,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                                   int pageNum) {
        return "category";
    }

}
