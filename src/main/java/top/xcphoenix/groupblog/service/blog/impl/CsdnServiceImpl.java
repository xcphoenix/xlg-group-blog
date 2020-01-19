package top.xcphoenix.groupblog.service.blog.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import top.xcphoenix.groupblog.manager.dao.BlogManager;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.model.dto.PageBlogs;
import top.xcphoenix.groupblog.manager.blog.content.BlogContentManager;
import top.xcphoenix.groupblog.manager.blog.userzone.UserZoneManager;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.service.blog.BlogService;
import top.xcphoenix.groupblog.utils.UrlUtil;

import java.sql.Timestamp;
import java.util.List;

/**
 * TODO
 *  - manager, service 层遵循 Alibaba 规范
 * @author      xuanc
 * @date        2020/1/13 下午5:40
 * @version     1.0
 */
@Slf4j
@Component("service-csdn")
public class CsdnServiceImpl implements BlogService {

    private BlogContentManager blogContentManager;
    private UserZoneManager seleniumUserZoneService;
    private UserZoneManager rssZoneService;
    private BlogManager blogManager;
    private UserManager userManager;

    public CsdnServiceImpl(@Qualifier("content-csdn") BlogContentManager blogContentManager,
                           @Qualifier("zone-csdn") UserZoneManager seleniumUserZoneService,
                           @Qualifier("rss-csdn") UserZoneManager rssZoneService,
                           BlogManager blogManager, UserManager userManager) {

        this.blogContentManager = blogContentManager;
        this.seleniumUserZoneService = seleniumUserZoneService;
        this.rssZoneService = rssZoneService;
        this.blogManager = blogManager;
        this.userManager = userManager;
    }

    @Override
    public void execFull(User user, BlogType blogType) throws Exception {
        log.info("exec full blog catch...");

        UrlUtil urlUtil = new UrlUtil(user.getBlogArg(), blogType);
        UrlBuilder urlBuilder = new UrlBuilder(urlUtil.getUserZoneUrl());

        log.info("begin get blogs from url: " + urlBuilder);
        while (true) {
            String url = urlBuilder.nextUrl();
            PageBlogs pageBlogs = seleniumUserZoneService.getPageBlogUrls(url);
            if (pageBlogs == null) {
                break;
            }

            List<Blog> blogList = pageBlogs.getBlogs();
            for (Blog blog : blogList) {
                if (blogManager.exists(blog.getBlogId())) {
                    log.warn("blog exists, jump");
                    continue;
                }

                blog.setUid(user.getUid());
                blogManager.addBlog(blogContentManager.getBlog(blog.getOriginalLink(), blog));
            }
            log.debug("blogs >> " + JSON.toJSONString(blogList, SerializerFeature.PrettyFormat));
        }
        log.info("get blogs end");
    }

    @Override
    public void execIncrement(User user, BlogType blogType) throws Exception {
        log.info("exec increment blog catch...");

        UrlUtil urlUtil = new UrlUtil(user.getBlogArg(), blogType);
        UrlBuilder urlBuilder = new UrlBuilder(urlUtil.getUserZoneUrl());

        log.info("begin get blogs from url: " + urlBuilder);

        Timestamp lastPubTime = userManager.getLastPubTime(user.getUid());
        PageBlogs pageBlogs = rssZoneService.getPageBlogUrls(urlUtil.getRssUrl());

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
                if (blogManager.exists(blog.getBlogId())) {
                    log.warn("blog exists, jump");
                    continue;
                }

                blog.setUid(user.getUid());
                blogManager.addBlog(blogContentManager.getBlog(blog.getOriginalLink(), blog));
            }
        }
        else {
            log.info("there are blogs that not in rss should be crawled");

            while (true) {
                String url = urlBuilder.nextUrl();
                PageBlogs seleniumPageBlogs = seleniumUserZoneService.getPageBlogUrls(url);

                if (seleniumPageBlogs == null ||
                        lastPubTime.getTime() >= seleniumPageBlogs.getLastTime().getTime()) {
                    break;
                }

                List<Blog> blogList = seleniumPageBlogs.getBlogs();
                for (Blog blog : blogList) {
                    if (blogManager.exists(blog.getBlogId())) {
                        log.warn("blog exists, jump");
                        continue;
                    }

                    blog.setUid(user.getUid());
                    blogManager.addBlog(blogContentManager.getBlog(blog.getOriginalLink(), blog));
                }
                log.debug("blogs >> " + JSON.toJSONString(blogList, SerializerFeature.PrettyFormat));
            }
        }

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
        this.initUrl = initUrl;
    }

    String nextUrl() {
        String url = initUrl + "/" + initPageNum;
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