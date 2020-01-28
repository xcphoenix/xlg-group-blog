package top.xcphoenix.groupblog;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.manager.blog.userzone.UserZoneManager;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author      xuanc
 * @date        2020/1/16 下午9:33
 * @version     1.0
 */
@SpringBootTest
public class RssTest {

    @Resource(name = "rss-csdn")
    private UserZoneManager userZoneService;

    @Test
    void romeTest() throws IOException, FeedException {
        String rssUrl = "https://phoenixxc.gitee.io/atom.xml";
        URL url = new URL(rssUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        List<String> strings =  feed.getEntries().stream().map(i -> i.getPublishedDate()).map(sdf::format).collect(Collectors.toList());
        System.out.println(strings);
    }

    @Test
    void rssServiceTest() throws Exception {
        String userzone = "https://blog.csdn.net/xuancbm/rss/list";
        System.out.println(userZoneService.getPageBlogUrls(userzone));
    }

}
