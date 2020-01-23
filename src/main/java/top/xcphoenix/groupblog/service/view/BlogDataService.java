package top.xcphoenix.groupblog.service.view;

import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.vo.BlogData;
import top.xcphoenix.groupblog.model.vo.Pagination;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/21 下午5:21
 */
public interface BlogDataService {

    /**
     * 获取分页数据
     *
     * @param pageNum 页数
     * @param pageSize　页大小
     * @param baseLink 基础链接
     * @return 分页数据
     */
    Pagination getPagination(int pageNum, int pageSize, String baseLink);

    /**
     * 获取附近的博客数据
     *
     * @param time 博客时间
     * @return 博客数据
     */
    List<Blog> getNearbyBlogs(Timestamp time);

    /**
     * 获取指定用户附近的博客数据
     *
     * @param time 博客时间
     * @param uid 用户id
     * @return 博客数据
     */
    List<Blog> getNearbyBlogsAsUser(Timestamp time, long uid);

    /**
     * 通过id获取博客数据
     *
     * @param blogId 博客id
     * @return 博客数据
     */
    BlogData getBlogById(long blogId);

    /**
     * 获取首页博客列表数据
     *
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return 博客列表数据
     */
    List<BlogData> getBlogDataForIndex(int pageNum, int pageSize);

    /**
     * 获取用户主页博客列表数据
     *
     * @param uid 用户id
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return 博客列表数据
     */
    List<BlogData> getBlogDataByUser(long uid, int pageNum, int pageSize);

    /**
     * 获取指定标签博客列表数据
     *
     * @param tagId 标签id
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return 博客列表数据
     */
    List<BlogData> getBlogDataByTag(int tagId, long pageNum, int pageSize);

    /**
     * 获取指定分类博客列表数据
     *
     * @param categoryId 分类id
     * @param pageNum 页数
     * @param pageSize 页大小
     * @return 博客列表数据
     */
    List<BlogData> getBlogDataByCategory(int categoryId, long pageNum, int pageSize);

}
