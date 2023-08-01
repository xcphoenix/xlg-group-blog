package top.xcphoenix.groupblog.manager.blog.overview.impl;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.expection.blog.BlogParseException;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.dto.PageBlogs;
import top.xcphoenix.groupblog.processor.Processor;
import top.xcphoenix.groupblog.manager.blog.overview.BlogOverviewManager;
import top.xcphoenix.groupblog.utils.UrlUtil;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/17 下午1:30
 */
@Service("rss")
public class RssManagerImpl implements BlogOverviewManager {

    @Resource(name = "feed")
    private Processor processor;
    @Override
    public PageBlogs getPageBlogUrls(String overviewUrl) throws BlogParseException, ProcessorException {
        Channel feed = (Channel) processor.processor(overviewUrl);
        List<Blog> blogs = new ArrayList<>();

        Timestamp oldTime = new Timestamp(System.currentTimeMillis());
        Timestamp newTime = new Timestamp(0L);

        try {
            for (Item item : feed.getItems()) {
                Blog blog = new Blog();
                blog.setAuthor(item.getAuthor());
                if (blog.getAuthor() == null || "".equals(blog.getAuthor())){
                    blog.setAuthor(UrlUtil.getAuthor(feed.getLink()));
                }
                String link = item.getLink();
                blog.setOriginalLink(link);
                String sourceId = link.substring(link.lastIndexOf("/") + 1);
                if(!"csdn".equals(UrlUtil.getAuthor(overviewUrl))){
                    if("".equals(sourceId)){
                        sourceId = link.substring(0,link.lastIndexOf("/"));
                        sourceId = sourceId.substring(sourceId.lastIndexOf("/"));
                    }
                    sourceId = blog.getAuthor()+"-"+sourceId;
                }
                blog.setSourceId(sourceId);

                Timestamp currentTime = new Timestamp(item.getPubDate().getTime());
                blog.setPubTime(currentTime);
                long timeValue = currentTime.getTime();
                if (timeValue > newTime.getTime()) {
                    newTime = currentTime;
                }
                if (timeValue < oldTime.getTime()) {
                    oldTime = currentTime;
                }

                String desc = item.getDescription().getValue();
                if (desc != null) {
                    String[] strings = desc.split("<div[^>]*?>[\\s\\S]*?</div>");
                    if (strings.length > 0) {
                        blog.setSummary(strings[0].trim());
                    }
                }
                blogs.add(blog);
            }
        } catch (Exception ex) {
            throw new BlogParseException("blog parse error", ex);
        }

        return new PageBlogs(oldTime, newTime, blogs);
    }

}
