package top.xcphoenix.groupblog.manager.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mybatis.mapper.BlogMapper;
import top.xcphoenix.groupblog.mybatis.mapper.UserMapper;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.manager.dao.BlogManager;
import top.xcphoenix.groupblog.model.vo.BlogData;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/17 下午6:20
 * @version     1.0
 */
@Slf4j
@Service
public class BlogManagerImpl implements BlogManager {

    private BlogMapper blogMapper;
    private UserMapper userMapper;

    public BlogManagerImpl(BlogMapper blogMapper, UserMapper userMapper) {
        this.blogMapper = blogMapper;
        this.userMapper = userMapper;
    }

    @Override
    public void addBlog(Blog blog) {
        log.info("add blog");

        if (blogMapper.addBlog(blog) != 0) {
            Timestamp lastPubTime = userMapper.getLastPubTime(blog.getUid());
            if (lastPubTime.getTime() < blog.getPubTime().getTime()) {
                log.info("update user pubTime");
                userMapper.updateLastPubTime(blog.getPubTime(), blog.getUid());
            }
        }
    }

    @Override
    public boolean exists(String sourceId) {
        return blogMapper.exists(sourceId);
    }

    @Override
    public List<BlogData> getBlogSummaries(int pageSize, int pageOffset) {
        return blogMapper.getBlogSummaryAsTime(pageSize, pageOffset, null);
    }

    @Override
    public List<BlogData> getBlogSummariesAsUser(int pageSize, int pageOffset, long uid) {
        return blogMapper.getBlogSummaryAsTime(pageSize, pageOffset, uid);
    }

    @Override
    public BlogData getBlog(long blogId) {
        return blogMapper.getBlog(blogId);
    }

    @Override
    public List<Blog> getNearbyBlogs(Timestamp time) {
        return blogMapper.getNearbyBlogs(time, null);
    }

    @Override
    public List<Blog> getNearbyBlogsAsUser(Timestamp time, long uid) {
        return blogMapper.getNearbyBlogs(time, uid);
    }

}
