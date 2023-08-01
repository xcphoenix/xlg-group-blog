package top.xcphoenix.groupblog.service.blog.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import top.xcphoenix.groupblog.expection.blog.BlogArgException;
import top.xcphoenix.groupblog.expection.blog.BlogParseException;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;
import top.xcphoenix.groupblog.manager.blog.content.BlogContentManager;
import top.xcphoenix.groupblog.manager.blog.overview.BlogOverviewManager;
import top.xcphoenix.groupblog.manager.dao.BlogManager;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.model.dto.PageBlogs;
import top.xcphoenix.groupblog.service.blog.BlogService;
import top.xcphoenix.groupblog.utils.UrlUtil;

import java.sql.Timestamp;

@Slf4j
@Component("service-rss")
public class RssBlogServiceImpl implements BlogService {
    private BlogContentManager blogContentManager;
    private BlogOverviewManager rssZoneService;
    private BlogManager blogManager;
    private UserManager userManager;

    public RssBlogServiceImpl(@Qualifier("content-rss-atom-feed") BlogContentManager blogContentManager,
                              @Qualifier("rss") BlogOverviewManager rssZoneService,
                              BlogManager blogManager,
                              UserManager userManager) {
        this.blogContentManager = blogContentManager;
        this.rssZoneService = rssZoneService;
        this.blogManager = blogManager;
        this.userManager = userManager;
    }

    // blog类型无法确定
    @Override
    public void execFull(User user, BlogType blogType) {
        execIncrement(user,blogType);
    }

    @Override
    public void execIncrement(User user, BlogType blogType) {
        log.info("exec increment rss-blog catch...");

        UrlUtil urlUtil = new UrlUtil(user.getBlogArg(), blogType);
        String rssUrl;

        try {
            rssUrl = urlUtil.getFeedUrl();
        } catch (BlogArgException bae) {
            log.warn("error happened in exec increment, user: " + user.getUid(), bae);
            return;
        }

        log.info("begin get blogs from url: " + rssUrl);

        Timestamp lastPubTime = userManager.getLastPubTime(user.getUid());
        PageBlogs pageBlogs;

        try {
            pageBlogs = rssZoneService.getPageBlogUrls(rssUrl);
        } catch (ProcessorException | BlogParseException ex) {
            log.warn("get overview blogs error, overview url: " + rssUrl, ex);
            return;
        }

        if (lastPubTime.getTime() >= pageBlogs.getLastTime().getTime()) {
            log.info("no new blog");
        }else if (lastPubTime.getTime() < pageBlogs.getLastTime().getTime()) {
            log.info("some blogs in rss need be crawled");

            for (Blog blog : pageBlogs.getBlogs()) {
                if (blog.getPubTime().getTime() < lastPubTime.getTime()) {
                    continue;
                }
                addValidBlog(blog, user.getUid());
            }
        }
    }

    private void addValidBlog(Blog blog, Long uid) {
        if (blogManager.exists(blog.getSourceId())) {
            log.warn("blog had been crawled, jump");
            if (blog.getPubTime() != null) {
                userManager.updateLastPubTime(uid, blog.getPubTime());
            }
            return;
        }
        try {
            blog = blogContentManager.getBlog(blog.getOriginalLink(), blog);
        } catch (ProcessorException | BlogParseException ex) {
            log.warn("get blog from page error, page: " + blog.getOriginalLink(), ex);
        }
        blog.setUid(uid);
        blogManager.addBlog(blog);
    }
}
