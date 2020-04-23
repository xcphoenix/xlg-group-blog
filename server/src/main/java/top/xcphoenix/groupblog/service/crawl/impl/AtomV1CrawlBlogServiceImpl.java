package top.xcphoenix.groupblog.service.crawl.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mybatis.mapper.BlogTypeMapper;
import top.xcphoenix.groupblog.mybatis.mapper.UserMapper;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.service.blog.BlogService;
import top.xcphoenix.groupblog.service.crawl.CrawlBlogService;

/**
 * @author      xuanc
 * @date        2020/1/15 下午3:24
 * @version     1.0
 */
@Service("crawl-atomv1")
class AtomV1CrawlBlogServiceImpl implements CrawlBlogService {

    private BlogService blogService;
    private UserMapper userMapper;
    private BlogTypeMapper blogTypeMapper;

    public AtomV1CrawlBlogServiceImpl(@Qualifier("service-atom") BlogService blogManager,
                                      UserMapper userMapper, BlogTypeMapper blogTypeMapper) {
        this.blogService = blogManager;
        this.userMapper = userMapper;
        this.blogTypeMapper = blogTypeMapper;
    }

    @Override
    public void crawlAll(long uid) {
        User user = userMapper.getUserBlogArgs(uid);
        BlogType blogType = blogTypeMapper.getBlogType(user.getBlogType());
        blogService.execFull(user, blogType);
    }

    @Override
    public void crawlIncrement(long uid) {
        User user = userMapper.getUserBlogArgs(uid);
        BlogType blogType = blogTypeMapper.getBlogType(user.getBlogType());
        blogService.execIncrement(user, blogType);
    }

}
