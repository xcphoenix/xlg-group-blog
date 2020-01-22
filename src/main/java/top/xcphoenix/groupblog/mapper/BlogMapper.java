package top.xcphoenix.groupblog.mapper;

import org.apache.ibatis.annotations.Param;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.vo.BlogData;

import java.sql.Timestamp;
import java.util.List;

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
     * @return 影响的行数
     */
    int addBlog(@Param("blog") Blog blog);

    /**
     * 检索博客是否存在
     *
     * @param blogId 博客id
     * @return 博客是否存在
     */
    boolean exists(@Param("blogId") long blogId);

    /**
     * 依据时间获取博客摘要信息
     *
     * @param pageSize 大小
     * @param pageOffset 偏移量
     * @return 博客数据
     */
    List<BlogData> getBlogSummaryAsTime(@Param("pageSize") int pageSize, @Param("pageOffset") int pageOffset);

    /**
     * 获取指定博客的数据
     *
     * @param blogId 博客id
     * @return 博客数据
     */
    BlogData getBlog(long blogId);

    /**
     * 获取博客总数
     *
     * @return 博客总数
     */
    long getBlogNum();

    /**
     * 获取附近博客数据
     *
     * TODO 自定义字段、值与过滤条件
     *
     * @param time 博客的时间
     * @return 上一篇和下一篇博客信息
     */
    List<Blog> getNearbyBlogs(Timestamp time);

}
