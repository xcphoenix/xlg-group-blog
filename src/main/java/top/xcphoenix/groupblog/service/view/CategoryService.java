package top.xcphoenix.groupblog.service.view;

import top.xcphoenix.groupblog.model.vo.CategoryData;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/25 下午4:02
 * @version     1.0
 */ 
public interface CategoryService {

    /**
     * 获取分类数据
     * @return 分类数据
     */
    List<CategoryData> getCategoryData();

}
