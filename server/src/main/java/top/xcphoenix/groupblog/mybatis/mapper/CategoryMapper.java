package top.xcphoenix.groupblog.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.model.dao.Category;
import top.xcphoenix.groupblog.model.vo.CategoryData;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/21 下午8:37
 * @version     1.0
 */ 
@Service
public interface CategoryMapper {

    /**
     * 获取分类信息
     *
     * @param categoryId 分类id
     * @return 分类信息
     */
    Category getCategory(@Param("categoryId") int categoryId);

    /**
     * 获取所有的分类信息
     *
     * @return 分类信息
     */
    List<CategoryData> getAllCategory();

    List<String> getGrade();

    Long getIdByGrade(@Param("grade") String grade);

    Long createNewGrade(@Param("grade") String grade);
}
