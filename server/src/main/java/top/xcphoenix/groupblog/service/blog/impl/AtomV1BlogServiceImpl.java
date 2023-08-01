package top.xcphoenix.groupblog.service.blog.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
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
import java.util.ListIterator;

/**
 * @author      xuanc
 * @date        2020/1/30 下午2:36
 * @version     1.0
 */
@Slf4j
@Service("service-atom")
public class AtomV1BlogServiceImpl implements BlogService {
    private BlogContentManager blogContentManager;
    private BlogOverviewManager blogOverviewManager;
    private BlogManager blogManager;
    private UserManager userManager;

    public AtomV1BlogServiceImpl(@Qualifier("content-rss-atom-feed") BlogContentManager blogContentManager,
                                 @Qualifier("atom-v1") BlogOverviewManager blogOverviewManager,
                                 BlogManager blogManager,
                                 UserManager userManager) {
        this.blogContentManager = blogContentManager;
        this.blogOverviewManager = blogOverviewManager;
        this.blogManager = blogManager;
        this.userManager = userManager;
    }

    @Override
    public void execFull(User user, BlogType blogType){
        log.warn("not define full task, use increment task");
        execIncrement(user, blogType);
    }

    @Override
    public void execIncrement(User user, BlogType blogType) {

        log.info("Exec increment task, blogType: [AtomV1.0]");

        String atomUrl;
        UrlUtil urlUtil = new UrlUtil(user.getBlogArg(), blogType);

        try {
            atomUrl = urlUtil.getFeedUrl();
        } catch (BlogArgException e) {
            e.printStackTrace();
            return;
        }

        PageBlogs pageBlogs;
        Timestamp lastPubTime = userManager.getLastPubTime(user.getUid());

        log.info("begin get blogs from url: " + atomUrl);
        try {
            pageBlogs = blogOverviewManager.getPageBlogUrls(atomUrl);
        } catch (BlogParseException | ProcessorException e) {
            log.warn("get overview blogs error, overview url: " + atomUrl, e);
            return;
        }

        if (lastPubTime.getTime() >= pageBlogs.getLastTime().getTime()) {
            log.info("no new blog");
        }
        else  {
            log.info("some blogs in atom need be crawled");
            ListIterator<Blog> iterator = pageBlogs.getBlogs().listIterator(pageBlogs.getBlogs().size());
            while (iterator.hasPrevious()) {
                Blog blog = iterator.previous();
//                if (blog.getPubTime().getTime() <= lastPubTime.getTime()) {
//                    continue;
//                }
                if (blogManager.exists(blog.getSourceId())) {
                    log.warn("blog had been crawled, jump");
                    /**
                     * 防止在时间错误的情况下，不更新博客前时间无法修正
                     */
                    if (blog.getPubTime() != null) {
                        userManager.updateLastPubTime(user.getUid(), blog.getPubTime());
                    }
                    continue;
                }
                if(blog.getContent() == null){
                    if(blog.getOriginalLink() != null){
                        try {
                            blog = blogContentManager.getBlog(blog.getOriginalLink(), blog);
                        } catch (ProcessorException | BlogParseException ex) {
                            log.warn("get blog from page error, page: " + blog.getOriginalLink(), ex);
                            continue;
                        }
                    }else{
                        log.warn("error: blog is not crawled.");
                        continue;
                    }
                }
                blog.setUid(user.getUid());
                blogManager.addBlog(blog);
            }
        }

    }


}
