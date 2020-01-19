package top.xcphoenix.groupblog.service.dao.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mapper.BlogMapper;
import top.xcphoenix.groupblog.mapper.UserMapper;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.service.dao.BlogService;

import java.sql.Timestamp;

/**
 * @author      xuanc
 * @date        2020/1/17 下午6:20
 * @version     1.0
 */
@Service
public class BlogServiceImpl implements BlogService {

    private BlogMapper blogMapper;
    private UserMapper userMapper;

    public BlogServiceImpl(BlogMapper blogMapper, UserMapper userMapper) {
        this.blogMapper = blogMapper;
        this.userMapper = userMapper;
    }

    @Override
    public void addBlog(Blog blog) {
        if (blogMapper.addBlog(blog) != 0) {
            Timestamp lastPubTime = userMapper.getLastPubTime(blog.getUid());
            if (lastPubTime.getTime() < blog.getPubTime().getTime()) {
                userMapper.updateLastPubTime(blog.getPubTime(), blog.getUid());
            }
        }
    }

    @Override
    public boolean exists(long blogId) {
        return blogMapper.exists(blogId);
    }

}
