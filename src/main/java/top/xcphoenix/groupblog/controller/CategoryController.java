package top.xcphoenix.groupblog.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.xcphoenix.groupblog.model.vo.*;
import top.xcphoenix.groupblog.service.view.AboutService;
import top.xcphoenix.groupblog.service.view.BlogDataService;
import top.xcphoenix.groupblog.service.view.CategoryService;
import top.xcphoenix.groupblog.service.view.SiteService;

import java.util.List;
import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/23 下午3:37
 * @version     1.0
 */
@Controller
public class CategoryController {

    private SiteService siteService;
    private CategoryService categoryService;

    public CategoryController(SiteService siteService, CategoryService categoryService) {
        this.siteService = siteService;
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String category(Map<String, Object> map) {
        SiteSchema siteSchema = siteService.getSiteSchema();
        List<CategoryData> categoryDataList = categoryService.getCategoryData();
        PostData<List<CategoryData>> postData = new PostData();
        postData.setPostTitle("分类");
        postData.setPostMeta(ImmutableMap.of("description",
                "目前共有" + siteSchema.getNumStatics().getCategoryNum() + "个分类"
        ));
        postData.setPostBody(categoryDataList);

        map.put("siteSchema", siteSchema);
        map.put("postData", postData);
        map.put("pageType", PageType.CATEGORY);

        return "category";
    }

}
