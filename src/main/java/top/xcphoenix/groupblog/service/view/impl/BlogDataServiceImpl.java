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
    public List<Blog> getNearbyBlogs(Timestamp time) {
        List<Blog> blogs = blogManager.getNearbyBlogs(time);
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

        for (BlogData blogData : blogDataList) {
            blogData.setBlogLink(blogLink(blogData.getBlogId()));
            blogData.setUserLink(userLink(blogData.getUid()));
            blogData.setCategoryLink(categoryLink(blogData.getCategoryId()));
            blogData.setFlagDesc(ORIGINAL_FLAG.get(blogData.isOriginal()));
        }

        return blogDataList;
    }

    @Override
    public List<BlogData> getBlogDataByUser(long uid, long pageNum, int pageSize) {
        return null;
    }

    @Override
    public List<BlogData> getBlogDataByTag(int tagId, long pageNum, int pageSize) {
        return null;
    }

    @Override
    public List<BlogData> getBlogDataByCategory(int categoryId, long pageNum, int pageSize) {
        return null;
    }

    private String blogLink(long blogId) {
        return BLOG_LINK_PREFIX + "/" + blogId;
    }

    private String userLink(long uid) {
        return USER_LINK_PREFIX + "/" + uid;
    }

    private String categoryLink(int categoryId) {
        return CATEGORY_LINK_PREFIX + "/" + categoryId;
    }

}
