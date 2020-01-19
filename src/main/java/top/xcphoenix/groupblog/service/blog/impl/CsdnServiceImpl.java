package top.xcphoenix.groupblog.service.blog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.model.dto.PageBlogs;
import top.xcphoenix.groupblog.manager.blog.content.BlogContentService;
import top.xcphoenix.groupblog.manager.blog.userzone.UserZoneService;
import top.xcphoenix.groupblog.manager.dao.UserService;
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
@Component("manager-csdn")
public class CsdnServiceImpl implements BlogService {

    private BlogContentService blogContentService;
    private UserZoneService seleniumUserZoneService;
    private UserZoneService rssZoneService;
    private top.xcphoenix.groupblog.manager.dao.BlogService blogService;
    private UserService userService;

    public CsdnServiceImpl(@Qualifier("content-csdn") BlogContentService blogContentService,
                           @Qualifier("zone-csdn") UserZoneService seleniumUserZoneService,
                           @Qualifier("rss-csdn") UserZoneService rssZoneService,
                           top.xcphoenix.groupblog.manager.dao.BlogService blogService, UserService userService) {

        this.blogContentService = blogContentService;
        this.seleniumUserZoneService = seleniumUserZoneService;
        this.rssZoneService = rssZoneService;
        this.blogService = blogService;
        this.userService = userService;
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
                if (blogService.exists(blog.getBlogId())) {
                    log.warn("blog exists, jump");
                    continue;
                }

                blog.setUid(user.getUid());
                blogService.addBlog(blogContentService.getBlog(blog.getOriginalLink(), blog));
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

        Timestamp lastPubTime = userService.getLastPubTime(user.getUid());
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
                if (blogService.exists(blog.getBlogId())) {
                    log.warn("blog exists, jump");
                    continue;
                }

                blog.setUid(user.getUid());
                blogService.addBlog(blogContentService.getBlog(blog.getOriginalLink(), blog));
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
                    if (blogService.exists(blog.getBlogId())) {
                        log.warn("blog exists, jump");
                        continue;
                    }

                    blog.setUid(user.getUid());
                    blogService.addBlog(blogContentService.getBlog(blog.getOriginalLink(), blog));
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