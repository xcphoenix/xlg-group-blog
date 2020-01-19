package top.xcphoenix.groupblog.service.crawl.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.service.blog.BlogService;
import top.xcphoenix.groupblog.mapper.BlogTypeMapper;
import top.xcphoenix.groupblog.mapper.UserMapper;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.service.crawl.CrawlBlogService;

/**
 * @author      xuanc
 * @date        2020/1/15 下午3:24
 * @version     1.0
 */
@Service("crawl-csdn")
class CsdnCrawlBlogServiceImpl implements CrawlBlogService {

    private BlogService blogManager;
    private UserMapper userMapper;
    private BlogTypeMapper blogTypeMapper;

    public CsdnCrawlBlogServiceImpl(@Qualifier("service-csdn") BlogService blogManager,
                                    UserMapper userMapper, BlogTypeMapper blogTypeMapper) {
        this.blogManager = blogManager;
        this.userMapper = userMapper;
        this.blogTypeMapper = blogTypeMapper;
    }

    @Override
    public void crawlAll(long uid) throws Exception {
        User user = userMapper.getUserBlogArgs(uid);
        BlogType blogType = blogTypeMapper.getBlogType(user.getBlogType());
        blogManager.execFull(user, blogType);
    }

    @Override
    public void crawlIncrement(long uid) throws Exception {
        User user = userMapper.getUserBlogArgs(uid);
        BlogType blogType = blogTypeMapper.getBlogType(user.getBlogType());
        blogManager.execIncrement(user, blogType);
    }

}
