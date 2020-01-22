package top.xcphoenix.groupblog.manager.dao.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.manager.dao.CategoryManager;
import top.xcphoenix.groupblog.mapper.CategoryMapper;
import top.xcphoenix.groupblog.model.dao.Category;

/**
 * @author      xuanc
 * @date        2020/1/21 下午8:47
 * @version     1.0
 */
@Service
public class CategoryManagerImpl implements CategoryManager {

    private CategoryMapper categoryMapper;

    public CategoryManagerImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category getCategory(int categoryId) {
        return categoryMapper.getCategory(categoryId);
    }

}
