package top.xcphoenix.groupblog.service.blog.userzone.impl;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import org.bouncycastle.util.Times;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.processor.Processor;
import top.xcphoenix.groupblog.service.blog.userzone.UserZoneService;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/17 下午1:30
 * @version     1.0
 */
@Service("rss-csdn")
public class CsdnRssServiceImpl implements UserZoneService {

    @Resource(name = "rss")
    private Processor processor;

    @Override
    public List<Blog> getPageBlogUrls(String userZoneUrl) throws Exception {
        SyndFeed feed = (SyndFeed) processor.processor(userZoneUrl);
        List<Blog> blogs = new ArrayList<>();

        for (SyndEntry entry : feed.getEntries()) {
            Blog blog = new Blog();
            blog.setAuthor(entry.getAuthor());
            String link = entry.getLink();
            blog.setOriginalLink(link);
            // csdn 开启 其他不开启
            blog.setBlogId(Long.parseLong(link.substring(link.lastIndexOf("/") + 1)));
            blog.setPubTime(new Timestamp(entry.getPublishedDate().getTime()));
            String desc = entry.getDescription().getValue();
            if (desc != null) {
                String[] strings = desc.split("<div[^>]*?>[\\s\\S]*?</div>");
                if (strings.length > 0) {
                    blog.setSummary(strings[0].trim());
                }
            }
            blogs.add(blog);
        }

        return blogs;
    }

}
