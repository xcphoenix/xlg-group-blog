package top.xcphoenix.groupblog;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;

/**
 * @author      xuanc
 * @date        2020/1/16 下午9:33
 * @version     1.0
 */
@SpringBootTest
public class RssTest {

    @Test
    void romeTest() throws IOException, FeedException {
        String rssUrl = "https://blog.csdn.net/xuancbm/rss/list";
        URL url = new URL(rssUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        System.out.println(feed);
    }

}
