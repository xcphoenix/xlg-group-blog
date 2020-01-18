package top.xcphoenix.groupblog.service.dao.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mapper.BlogMapper;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.service.dao.BlogService;

/**
 * @author      xuanc
 * @date        2020/1/17 下午6:20
 * @version     1.0
 */
@Service
public class BlogServiceImpl implements BlogService {

    private BlogMapper blogMapper;

    public BlogServiceImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public void addBlog(Blog blog) {
        blogMapper.addBlog(blog);
    }

    @Override
    public boolean exists(long blogId) {
        return blogMapper.exists(blogId);
    }

}
