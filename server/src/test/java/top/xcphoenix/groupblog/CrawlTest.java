package top.xcphoenix.groupblog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Ignore;
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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
    @Qualifier("crawl-csdn")
    private CrawlBlogService crawlBlogService;

    @Autowired
    @Qualifier("crawl-rss")
    private CrawlBlogService RssBlogService;

    @Autowired
    @Qualifier("crawl-atomv1")
    private CrawlBlogService AtomBlogService;

    @Autowired
    @Qualifier("content-csdn")
    private BlogContentManager blogContentManager;

    @Autowired
    @Qualifier("content-rss-atom-feed")
    private BlogContentManager articleBlogContent;

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
        crawlBlogService.crawlIncrement(10140);
//        RssBlogService.crawlIncrement(10159);
//        AtomBlogService.crawlIncrement(10108);
    }

    @Test
    void testBlogParse() throws ProcessorException, BlogParseException {
        Blog blog = blogContentManager.getBlog("https://blog.csdn.net/weixin_74056357/article/details/131767466", null);
        System.out.println(JSON.toJSONString(blog));
    }

    @Test
    void testCsdnSetTop() throws ProcessorException, BlogParseException {
        PageBlogs pageBlogs = blogOverviewManager.getPageBlogUrls("https://blog.csdn.net/weixin_43574962/article/list/");
        System.out.println(JSON.toJSONString(pageBlogs, SerializerFeature.PrettyFormat));
    }

    @Test
    void testCsdnBlogExec() throws Exception {
        crawlBlogService.crawlAll(10143);
    }

    @Test
    void test1() throws IOException {
        // 访问目标网页
        Document doc = Jsoup.connect("https://daz-3ux.github.io/posts/golang-%E7%9A%84%E6%8E%A5%E5%8F%A3%E5%9E%8B%E5%87%BD%E6%95%B0/").get();
        // 查找包含时间信息的元素
        Elements timeElements = doc.select("time");

        // 获取第一个包含时间信息的元素的时间值
        String time;
        if (timeElements != null){
            time = timeElements.first().attr("datetime");
        }else {
            time = "0";
        }
        // 输出时间值
        System.out.println("Time: " + time);
    }
    @Test
    void test2() throws ProcessorException, BlogParseException {
        Blog blog = articleBlogContent.getBlog("https://juejin.cn/post/7197721294019543099", null);
        System.out.println(JSON.toJSONString(blog));
        System.out.println(blog.getPubTime());
    }
}
