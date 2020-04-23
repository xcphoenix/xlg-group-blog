package top.xcphoenix.groupblog.service.view.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mybatis.mapper.CategoryMapper;
import top.xcphoenix.groupblog.model.vo.CategoryData;
import top.xcphoenix.groupblog.model.vo.UserItem;
import top.xcphoenix.groupblog.service.view.CategoryService;
import top.xcphoenix.groupblog.service.view.LinkGeneratorService;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/25 下午4:02
 * @version     1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryMapper categoryMapper;
    private LinkGeneratorService linkGeneratorService;

    public CategoryServiceImpl(CategoryMapper categoryMapper, LinkGeneratorService linkGeneratorService) {
        this.categoryMapper = categoryMapper;
        this.linkGeneratorService = linkGeneratorService;
    }

    @Override
    public List<CategoryData> getCategoryData() {
        List<CategoryData> categoryDataList = categoryMapper.getAllCategory();

        for (CategoryData data : categoryDataList) {
            data.setCategoryLink(linkGeneratorService.getCategoryLink(data.getCategoryId()));
            for (UserItem item : data.getUserItemList()) {
                item.setUserLink(linkGeneratorService.getUserLink(
                        item.getUser().getUid()
                ));
            }
        }

        return categoryDataList;
    }

}
