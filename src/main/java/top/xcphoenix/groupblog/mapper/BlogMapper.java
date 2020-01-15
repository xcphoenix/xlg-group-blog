package top.xcphoenix.groupblog.mapper;

import org.apache.ibatis.annotations.Param;
import top.xcphoenix.groupblog.model.dao.Blog;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/13 下午9:56
 */
public interface BlogMapper {

    /**
     * 添加博客
     *
     * @param blog 博客数据
     */
    void addBlog(@Param("blog") Blog blog);

    /**
     * 检索博客是否存在
     *
     * @param blogId 博客id
     * @return 博客是否存在
     */
    boolean exists(@Param("blogId") long blogId);

}
