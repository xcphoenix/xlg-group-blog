package top.xcphoenix.groupblog.mapper;

import org.apache.ibatis.annotations.Param;
import top.xcphoenix.groupblog.model.dao.Category;

/**
 * @author      xuanc
 * @date        2020/1/21 下午8:37
 * @version     1.0
 */ 
public interface CategoryMapper {

    /**
     * 获取分类信息
     *
     * @param categoryId 分类id
     * @return 分类信息
     */
    Category getCategory(@Param("categoryId") int categoryId);

}
