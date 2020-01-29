package top.xcphoenix.groupblog.service.blog.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import top.xcphoenix.groupblog.expection.blog.BlogArgException;
import top.xcphoenix.groupblog.expection.blog.BlogParseException;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;
import top.xcphoenix.groupblog.manager.dao.BlogManager;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.model.dto.PageBlogs;
import top.xcphoenix.groupblog.manager.blog.content.BlogContentManager;
import top.xcphoenix.groupblog.manager.blog.overview.BlogOverviewManager;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.service.blog.BlogService;
import top.xcphoenix.groupblog.utils.UrlUtil;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/13 下午5:40
 * @version     1.0
 */
@Slf4j
@Component("service-csdn")
public class CsdnServiceImpl implements BlogService {

    private BlogContentManager blogContentManager;
    private BlogOverviewManager seleniumUserZoneService;
    private BlogOverviewManager rssZoneService;
    private BlogManager blogManager;
    private UserManager userManager;

    public CsdnServiceImpl(@Qualifier("content-csdn") BlogContentManager blogContentManager,
                           @Qualifier("zone-csdn") BlogOverviewManager seleniumUserZoneService,
                           @Qualifier("rss-csdn") BlogOverviewManager rssZoneService,
                           BlogManager blogManager, UserManager userManager) {

        this.blogContentManager = blogContentManager;
        this.seleniumUserZoneService = seleniumUserZoneService;
        this.rssZoneService = rssZoneService;
        this.blogManager = blogManager;
        this.userManager = userManager;
    }

    @Override
    public void execFull(User user, BlogType blogType) {
        log.info("exec full blog catch...");

        UrlUtil urlUtil = new UrlUtil(user.getBlogArg(), blogType);
        String userzoneUrl;

        try {
            userzoneUrl = urlUtil.getUserZoneUrl();
        } catch (BlogArgException bae) {
            log.warn("error happened in exec full, user: " + user.getUid(), bae);
            return;
        }

        UrlBuilder urlBuilder = new UrlBuilder(userzoneUrl);

        log.info("begin get blogs from url: " + urlBuilder);
        addBlogs(urlBuilder, user.getUid());
        log.info("get blogs end");
    }

    @Override
    public void execIncrement(User user, BlogType blogType) {
        log.info("exec increment blog catch...");

        UrlUtil urlUtil = new UrlUtil(user.getBlogArg(), blogType);
        UrlBuilder urlBuilder;
        String rssUrl;

        try {
            urlBuilder = new UrlBuilder(urlUtil.getUserZoneUrl());
            rssUrl = urlUtil.getRssUrl();
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
        }
        else if (lastPubTime.getTime() >= pageBlogs.getOldTime().getTime() &&
            lastPubTime.getTime() < pageBlogs.getLastTime().getTime()) {
            log.info("some blogs in rss need be crawled");

            for (Blog blog : pageBlogs.getBlogs()) {
                // 时间相同的博客可能会被忽略
                if (blog.getPubTime().getTime() <= lastPubTime.getTime()) {
                    continue;
                }
                addInvalidBlog(blog, user.getUid());
            }
        }
        else {
            log.info("there are blogs that not in rss should be crawled");
            addBlogs(urlBuilder, user.getUid(), lastPubTime);
        }
    }

    private Blog getBlogContent(Blog blog) {
        if (blogManager.exists(blog.getSourceId())) {
            log.warn("blog exists, jump");
            /*
             * 防止在时间错误的情况下，不更新博客前时间无法修正
             */
            if (blog.getPubTime() != null) {
                userManager.updateLastPubTime(blog.getUid(), blog.getPubTime());
            }
            return null;
        }

        try {
            blog = blogContentManager.getBlog(blog.getOriginalLink(), blog);
        } catch (ProcessorException | BlogParseException ex) {
            log.warn("get blog from page error, page: " + blog.getOriginalLink(), ex);
            return null;
        }

        return blog;
    }

    private void addInvalidBlog(Blog blog, long uid) {
        blog.setUid(uid);
        blog = getBlogContent(blog);

        if (blog != null) {
            blogManager.addBlog(blog);
        }
    }

    private void addBlogs(UrlBuilder urlBuilder, long uid, Timestamp lastPubTime) {
        while (true) {
            String url = urlBuilder.nextUrl();
            PageBlogs pageBlogs;

            try {
                pageBlogs = seleniumUserZoneService.getPageBlogUrls(url);
            } catch (ProcessorException | BlogParseException ex) {
                log.warn("get overview blogs error, overview url: " + url, ex);
                return;
            }

            if (pageBlogs == null || lastPubTime.getTime() >= pageBlogs.getLastTime().getTime()) {
                return;
            }

            List<Blog> blogList = pageBlogs.getBlogs();
            for (Blog blog : blogList) {
                addInvalidBlog(blog, uid);
            }
            log.debug("blogs >> " + JSON.toJSONString(blogList, SerializerFeature.PrettyFormat));
        }
    }

    private void addBlogs(UrlBuilder urlBuilder, long uid) {
        addBlogs(urlBuilder, uid, new Timestamp(0));
    }

}

@PropertySource(value = "classpath:config/manager/csdnManager.properties", encoding = "utf-8")
class UrlBuilder {

    private String initUrl;
    private int initPageNum = 1;

    /*
     * props
     */

    @Value("${manager.original.only:false}")
    private boolean onlyOriginal;
    @Value("${manager.original.arg}")
    private String originalArg;
    @Value("${manager.original.value}")
    private String originalVal;

    UrlBuilder(String initUrl) {
        String separator = "/";
        if (initUrl.endsWith(separator)) {
            this.initUrl = initUrl;
        } else {
            this.initUrl = initUrl + separator;
        }
    }

    String nextUrl() {
        String url = initUrl + initPageNum;
        if (onlyOriginal) {
            url += "?" + originalArg + "=" + originalVal;
        }
        initPageNum ++;
        return url;
    }

    @Override
    public String toString() {
        return initUrl;
    }

}