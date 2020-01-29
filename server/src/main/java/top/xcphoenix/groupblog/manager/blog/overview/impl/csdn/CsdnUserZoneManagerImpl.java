package top.xcphoenix.groupblog.manager.blog.overview.impl.csdn;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.expection.blog.BlogParseException;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.dto.PageBlogs;
import top.xcphoenix.groupblog.processor.Processor;
import top.xcphoenix.groupblog.manager.blog.overview.BlogOverviewManager;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/13 下午3:50
 */
@Slf4j
@Service("zone-csdn")
@PropertySource(value = "file:${config-dir}/overview/csdnBlogRule.properties", encoding = "utf-8")
public class CsdnUserZoneManagerImpl implements BlogOverviewManager {

    @Resource(name = "selenium")
    private Processor processor;

    @Value("${zone.blog.tag.rule}")
    private String blogTagRule;
    @Value("${zone.blog.title.rule}")
    private String titleRule;
    @Value("${zone.blog.link.rule}")
    private String linkRule;
    @Value("${zone.blog.summary.rule}")
    private String summaryRule;
    @Value("${zone.blog.date.rule}")
    private String dateRule;
    @Value("${zone.blog.date.format}")
    private String dateFormat;
    @Value("${zone.blog.nodata.flag}")
    private String noDataFlag;
    @Value("${zone.blog.filter.class}")
    private String filterFlag;

    @Override
    public PageBlogs getPageBlogUrls(String userZoneUrl) throws ProcessorException, BlogParseException {
        List<Blog> blogs = new ArrayList<>();
        Timestamp oldTime = new Timestamp(System.currentTimeMillis());
        Timestamp newTime = new Timestamp(0L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        boolean hasData;
        List<Element> elements;

        // use selenium
        String webContent = (String) processor.processor(userZoneUrl);

        try {
            Document document = Jsoup.parse(webContent);
            elements = document.getElementsByClass(blogTagRule);

            hasData = document.select(noDataFlag).size() == 0;

            for (Element element : elements) {
                // 移除过滤标签
                // if (element.getElementsByClass(filterFlag).size() != 0) {
                //     continue;
                // }

                Blog blog = new Blog();
                String originalLink = element.select(linkRule).first().attr("href");
                blog.setOriginalLink(originalLink);
                blog.setSummary(element.select(summaryRule).first().text());
                blog.setSourceId(originalLink.substring(originalLink.lastIndexOf("/") + 1));

                Timestamp currentTime = new Timestamp(
                        simpleDateFormat.parse(
                                element.select(dateRule).first().text()
                        ).getTime()
                );
                blog.setPubTime(currentTime);

                long timeValue = currentTime.getTime();
                if (timeValue > newTime.getTime()) {
                    newTime = currentTime;
                }
                if (timeValue < oldTime.getTime()) {
                    oldTime = currentTime;
                }

                blogs.add(blog);
            }
        } catch (Exception ex) {
            throw new BlogParseException("blog parse error, userZone url: " + userZoneUrl, ex);
        }

        if (!hasData || elements.size() == 0) {
            return null;
        }
        return new PageBlogs(oldTime, newTime, blogs);
    }

}
