package top.xcphoenix.groupblog.service.view.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.manager.dao.BlogManager;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.vo.BlogData;
import top.xcphoenix.groupblog.service.view.BlogDataService;
import top.xcphoenix.groupblog.service.view.LinkGeneratorService;

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
    private LinkGeneratorService linkGeneratorService;

    private static final Map<Boolean, String> ORIGINAL_FLAG = ImmutableMap.of(true, "原", false, "转");

    public BlogDataServiceImpl(BlogManager blogManager, LinkGeneratorService linkGeneratorService) {
        this.blogManager = blogManager;
        this.linkGeneratorService = linkGeneratorService;
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
        blogData.setUserLink(linkGeneratorService.getUserLink(blogData.getUid()));
        blogData.setCategoryLink(linkGeneratorService.getCategoryLink(blogData.getCategoryId()));
        return blogData;
    }

    @Override
    public List<BlogData> getBlogDataForIndex(int pageNum, int pageSize) {
        pageNum = Math.max(pageNum, 1);
        List<BlogData> blogDataList = blogManager.getBlogSummaries(pageSize, pageSize * (pageNum - 1));

        return generateBlogDataLists(blogDataList, null);
    }

    @Override
    public List<BlogData> getBlogDataByUser(long uid, int pageNum, int pageSize) {
        pageNum = Math.max(pageNum, 1);
        List<BlogData> blogDataList = blogManager.getBlogSummariesAsUser(pageSize, pageSize * (pageNum - 1), uid);

        return generateBlogDataLists(blogDataList, linkGeneratorService.getBlogPrefixLinkOnUser(uid));
    }

    @Override
    public List<BlogData> getBlogDataByTag(int tagId, long pageNum, int pageSize) {
        return null;
    }

    @Override
    public List<BlogData> getBlogDataByCategory(int categoryId, long pageNum, int pageSize) {
        return null;
    }

    @Override
    public List<BlogData> generateBlogDataLists(List<BlogData> blogDataList, String prefix) {
        for (BlogData blogData : blogDataList) {
            blogData.setBlogLink(linkGeneratorService.getBlogLink(prefix, blogData.getBlogId()));
            blogData.setUserLink(linkGeneratorService.getUserLink(blogData.getUid()));
            blogData.setCategoryLink(linkGeneratorService.getCategoryLink(blogData.getCategoryId()));
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
            } else {
                blogs.add(null);
            }
        }
        return blogs;
    }

}
