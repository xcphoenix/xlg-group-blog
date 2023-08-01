package top.xcphoenix.groupblog.service.crawl.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.mybatis.mapper.BlogTypeMapper;
import top.xcphoenix.groupblog.mybatis.mapper.UserMapper;
import top.xcphoenix.groupblog.service.blog.BlogService;
import top.xcphoenix.groupblog.service.crawl.CrawlBlogService;

// rss,除开csdn
@Service("crawl-rss")
public class RssCrawBlogImpl implements CrawlBlogService {
    private BlogService blogService;
    private UserMapper userMapper;
    private BlogTypeMapper blogTypeMapper;

    public RssCrawBlogImpl(@Qualifier("service-rss") BlogService blogManager,
                                    UserMapper userMapper, BlogTypeMapper blogTypeMapper) {
        this.blogService = blogManager;
        this.userMapper = userMapper;
        this.blogTypeMapper = blogTypeMapper;
    }

    // rss无法实现全部爬取:根据不同博客类型，博客页面也不一致，无法爬取
    @Override
    public void crawlAll(long uid) {
        User user = userMapper.getUserBlogArgs(uid);
        BlogType blogType = blogTypeMapper.getBlogTypeByTid(user.getBlogType());
        blogService.execFull(user, blogType);
    }

    @Override
    public void crawlIncrement(long uid) {
        User user = userMapper.getUserBlogArgs(uid);
        BlogType blogType = blogTypeMapper.getBlogTypeByTid(user.getBlogType());
        blogService.execIncrement(user, blogType);
    }
}
