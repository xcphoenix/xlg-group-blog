package top.xcphoenix.groupblog.service.blog.content.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.service.blog.content.BlogContentService;
import top.xcphoenix.groupblog.utils.HtmlUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/12 上午12:41
 */
@Service("content-csdn")
@Slf4j
@PropertySource(value = "classpath:config/content/csdnBlogRule.properties", encoding = "UTF-8")
public class CsdnBlogContentServiceImpl implements BlogContentService {

    @Value("${blog.rule.title}")
    private String titleRule;
    @Value("${blog.rule.isOriginal}")
    private String isOriginalRule;
    @Value("${blog.original.flag}")
    private String originalFlag;
    @Value("${blog.rule.author}")
    private String authorRule;
    @Value("${blog.rule.pubTime}")
    private String pubTimeRule;
    @Value("${blog.pubTime.format}")
    private String pubTimeFormat;
    @Value("${blog.rule.content}")
    private String contentRule;
    @Value("${blog.summary.word.limit:200}")
    private int summaryWordLimit;

    @Override
    public Blog getBlogFromHtml(String webContent) throws ParseException {

        Blog blog = new Blog();
        this.getBlogFromHtml(webContent, blog);

        return blog;
    }

    @Override
    public void getBlogFromHtml(String webContent, Blog blog) throws ParseException {

        log.info("get blog data from web content");

        if (blog == null) {
            blog = new Blog();
        }
        Document document = Jsoup.parse(webContent);

        SimpleDateFormat pubDateFormat = new SimpleDateFormat(pubTimeFormat);

        if (blog.getTitle() == null) {
            blog.setTitle(document.select(titleRule).first().text());
        }
        if (blog.getAuthor() == null) {
            blog.setAuthor(document.select(authorRule).first().text());
        }
        if (blog.getPubTime() == null) {
            String timeStr = document.select(pubTimeRule).first().text();
            blog.setPubTime(new Timestamp(pubDateFormat.parse(timeStr).getTime()));
        }

        String content = document.select(contentRule).first().html();
        blog.setContent(content);

        if (blog.getSummary() == null) {
            blog.setSummary(
                    HtmlUtil.delSpace(
                            HtmlUtil.delHtmlTag(content)
                    ).substring(0, summaryWordLimit)
            );
        }

        blog.setOriginal(originalFlag.equals(document.select(isOriginalRule).first().text()));

        log.debug(JSON.toJSONString(blog));
    }

}
