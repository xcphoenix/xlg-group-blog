package top.xcphoenix.groupblog.service.view.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.manager.dao.BlogManager;
import top.xcphoenix.groupblog.manager.dao.CategoryManager;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.vo.BlogData;
import top.xcphoenix.groupblog.model.vo.Pagination;
import top.xcphoenix.groupblog.service.view.BlogDataService;
import top.xcphoenix.groupblog.service.view.SiteService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/21 下午8:04
 * @version     1.0
 */
@Service
public class BlogDataServiceImpl implements BlogDataService {

    private BlogManager blogManager;
    private CategoryManager categoryManager;
    private SiteService siteService;

    private static final String BLOG_LINK_PREFIX = "/blog";
    private static final String USER_LINK_PREFIX = "/user";
    private static final String CATEGORY_LINK_PREFIX = "/category";
    private static final Map<Boolean, String> ORIGINAL_FLAG = ImmutableMap.of(true, "原", false, "转");

    public BlogDataServiceImpl(BlogManager blogManager, CategoryManager categoryManager, SiteService siteService) {
        this.blogManager = blogManager;
        this.categoryManager = categoryManager;
        this.siteService = siteService;
    }

    @Override
    public Pagination getPagination(int pageNum, int pageSize, String baseLink) {
        long blogNums = blogManager.getBlogNum();
        int pageTotal = (int)(blogNums / pageSize);
        return new Pagination(pageTotal, pageNum, pageSize, baseLink);
    }

    @Override
    public Pagination getPaginationAsUser(int pageNum, int pageSize, String baseLink, long uid) {
        long blogNums = blogManager.getBlogNumAsUser(uid);
        int pageTotal = (int)(blogNums / pageSize);
        return new Pagination(pageTotal, pageNum, pageSize, baseLink);
    }

    @Override
    public List<Blog> getNearbyBlogs(Timestamp time) {
        List<Blog> blogs = blogManager.getNearbyBlogs(time);

        return generateNearbyBlogs(blogs, time);
    }

    @Override
    public List<Blog> getNearbyBlogsAsUser(Timestamp time, long uid) {
        List<Blog> blogs = blogManager.getNearbyBlogsAsUser(time, uid);

        return generateNearbyBlogs(blogs, time);
    }

    @Override
    public BlogData getBlogById(long blogId) {
        BlogData blogData = blogManager.getBlog(blogId);
        blogData.setFlagDesc(ORIGINAL_FLAG.get(blogData.isOriginal()));
        return blogData;
    }

    @Override
    public List<BlogData> getBlogDataForIndex(int pageNum, int pageSize) {
        pageNum = Math.max(pageNum, 1);
        List<BlogData> blogDataList = blogManager.getBlogSummaries(pageSize, pageSize * (pageNum - 1));

        return generateBlogDataLists(blogDataList, BLOG_LINK_PREFIX);
    }

    @Override
    public List<BlogData> getBlogDataByUser(long uid, int pageNum, int pageSize) {
        pageNum = Math.max(pageNum, 1);
        List<BlogData> blogDataList = blogManager.getBlogSummariesAsUser(pageSize, pageSize * (pageNum - 1), uid);

        return generateBlogDataLists(blogDataList, blogPrefixLinkOnUser(uid));
    }

    @Override
    public List<BlogData> getBlogDataByTag(int tagId, long pageNum, int pageSize) {
        return null;
    }

    @Override
    public List<BlogData> getBlogDataByCategory(int categoryId, long pageNum, int pageSize) {
        return null;
    }

    private List<BlogData> generateBlogDataLists(List<BlogData> blogDataList, String prefix) {
        for (BlogData blogData : blogDataList) {
            blogData.setBlogLink(blogLink(prefix, blogData.getBlogId()));
            blogData.setUserLink(userLink(blogData.getUid()));
            blogData.setCategoryLink(categoryLink(blogData.getCategoryId()));
            blogData.setFlagDesc(ORIGINAL_FLAG.get(blogData.isOriginal()));
        }

        return blogDataList;
    }

    private List<Blog> generateNearbyBlogs(List<Blog> blogs, Timestamp time) {
        if (blogs.size() == 0) {
            return null;
        }
        else if (blogs.size() == 1) {
            if (blogs.get(0).getPubTime().getTime() < time.getTime()) {
                blogs.add(blogs.get(0));
                blogs.set(0, null);
            }
        }

        return blogs;
    }

    private String blogLink(String prefix, long blogId) {
        return prefix + "/" + blogId;
    }

    private String blogPrefixLinkOnUser(long uid) {
        return USER_LINK_PREFIX + "/" + uid + BLOG_LINK_PREFIX;
    }

    private String userLink(long uid) {
        return USER_LINK_PREFIX + "/" + uid;
    }

    private String categoryLink(int categoryId) {
        return CATEGORY_LINK_PREFIX + "/" + categoryId;
    }

}
