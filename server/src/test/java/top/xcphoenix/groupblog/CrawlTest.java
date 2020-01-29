package top.xcphoenix.groupblog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.expection.blog.BlogParseException;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;
import top.xcphoenix.groupblog.manager.blog.content.BlogContentManager;
import top.xcphoenix.groupblog.manager.blog.overview.BlogOverviewManager;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.dto.PageBlogs;
import top.xcphoenix.groupblog.service.crawl.CrawlBlogService;
import top.xcphoenix.groupblog.service.dispatch.ScheduleCrawlService;

/**
 * @author      xuanc
 * @date        2020/1/15 下午3:44
 * @version     1.0
 */
@SpringBootTest
@Slf4j
public class CrawlTest {

    @Autowired
    private ScheduleCrawlService scheduleCrawlService;

    @Autowired
    private CrawlBlogService crawlBlogService;

    @Autowired
    @Qualifier("content-csdn")
    private BlogContentManager blogContentManager;

    @Autowired
    @Qualifier("zone-csdn")
    private BlogOverviewManager blogOverviewManager;

    @Test
    @Ignore
    void testScheduleCrawlIncr() {
        scheduleCrawlService.crawlIncr();
    }

    @Test
    void testCrawlUser() {
        crawlBlogService.crawlIncrement(10080);
    }

    @Test
    void testBlogParse() throws ProcessorException, BlogParseException {
        Blog blog = blogContentManager.getBlog("https://blog.csdn.net/details/103097703", null);
        log.info(JSON.toJSONString(blog));
    }

    @Test
    void testCsdnSetTop() throws ProcessorException, BlogParseException {
        PageBlogs pageBlogs = blogOverviewManager.getPageBlogUrls("https://blog.csdn.net/weixin_43574962/article/list/");
        log.info(JSON.toJSONString(pageBlogs, SerializerFeature.PrettyFormat));
    }

}
