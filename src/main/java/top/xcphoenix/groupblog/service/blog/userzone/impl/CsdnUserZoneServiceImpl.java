package top.xcphoenix.groupblog.service.blog.userzone.impl;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.processor.Processor;
import top.xcphoenix.groupblog.service.blog.userzone.UserZoneService;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/13 下午3:50
 * @version     1.0
 */
@Slf4j
@Service("zone-csdn")
@PropertySource(value = "classpath:config/userzone/csdnBlogRule.properties", encoding = "utf-8")
public class CsdnUserZoneServiceImpl implements UserZoneService {

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

    @Override
    public List<Blog> getPageBlogUrls(String userZoneUrl) throws ParseException {
        List<Blog> blogs = new ArrayList<>();

        String webContent = processor.processor(userZoneUrl);
        Document document = Jsoup.parse(webContent);
        List<Element> elements = document.getElementsByClass(blogTagRule);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        boolean hasData = document.select(noDataFlag).size() == 0;

        for (Element element : elements) {
            Blog blog = new Blog();
            // blog.setTitle(element.select(titleRule).first().text());
            String originalLink = element.select(linkRule).first().attr("href");
            blog.setOriginalLink(originalLink);
            blog.setSummary(element.select(summaryRule).first().text());
            blog.setBlogId(Long.parseLong(originalLink.substring(originalLink.lastIndexOf("/") + 1)));
            blog.setPubTime(
                    new Timestamp(simpleDateFormat.parse(
                            element.select(dateRule).first().text()
                    ).getTime())
            );
            blogs.add(blog);
        }
        if (!hasData || elements.size() == 0) {
            return null;
        }
        return blogs;
    }

}
