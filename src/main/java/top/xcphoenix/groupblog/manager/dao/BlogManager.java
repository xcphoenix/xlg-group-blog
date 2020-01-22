package top.xcphoenix.groupblog.manager.dao;

import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.vo.BlogData;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/17 下午6:17
 * @version     1.0
 */ 
public interface BlogManager {

    /**
     * 添加博客
     *
     * @param blog 博客信息
     */
    void addBlog(Blog blog);

    /**
     * 博客是否存在
     *
     * @param blogId 博客id
     * @return 博客存在否
     */
    boolean exists(long blogId);

    /**
     * 依据时间获取博客摘要信息
     *
     * @param pageSize 页大小
     * @param pageOffset 偏移量
     * @return 博客数据
     */
    List<BlogData> getBlogSummaries(int pageSize, int pageOffset);

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
     * 获取附近时间的博客
     *
     * @param time 时间
     * @return 上一篇和下一篇的博客
     */
    List<Blog> getNearbyBlogs(Timestamp time);

}
