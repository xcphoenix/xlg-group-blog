package top.xcphoenix.groupblog.manager.blog.content.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.config.MixPropertySourceFactory;
import top.xcphoenix.groupblog.expection.blog.BlogParseException;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;
import top.xcphoenix.groupblog.manager.blog.content.BlogContentManager;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.processor.Processor;
import top.xcphoenix.groupblog.service.picture.CrawPictureService;
import top.xcphoenix.groupblog.utils.HtmlUtil;
import top.xcphoenix.groupblog.utils.UrlUtil;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service("content-rss-atom-feed")
@Slf4j
@PropertySource(value = "${config-dir}/content/articleBlogRule.yml", encoding = "UTF-8", factory = MixPropertySourceFactory.class)
@ConfigurationProperties(prefix = "blog")
@Data
public class ArticleBlogContentManagerImpl implements BlogContentManager {
    private String titleRule;
    private String pubTimeRule;
    public List<String> pubTimeFormat;
    private String contentRule;
    private int summaryWordLimit;

    @Autowired
    @Qualifier("craw-picture")
    CrawPictureService crawPictureService;
    @Autowired
    @Qualifier("selenium")
    private Processor processor;

    public ArticleBlogContentManagerImpl() {}

    @Override
    public Blog getBlog(String url, Blog blog) throws ProcessorException, BlogParseException {
        log.info("get blog data from web content");

        String webContent = (String) processor.processor(url);
        blog = blog == null ? new Blog() : blog;

        try {
            Document document = Jsoup.parse(webContent);

            if (blog.getTitle() == null) {
                Elements titles = document.select(titleRule);
                for(Element title : titles){
                    if(title.text() == null){
                        continue;
                    }
                    blog.setTitle(title.text());
                }
                if(blog.getTitle() == null){
                    blog.setTitle(url.substring(0, url.lastIndexOf("/")).substring(url.lastIndexOf("/")+1));
                }
            }
            if (blog.getAuthor() == null) {
                blog.setAuthor(UrlUtil.getAuthor(url));
            }
            if (blog.getPubTime() == null) {
                // 获取时间
                String time = getTime(document,url);
                if(time != null){
                    //删除除时间外的字
                    time = time.replaceAll("[\u4e00-\u9fa5]", "");
                    time = time.replaceAll("/","-");
                }
                SimpleDateFormat pubDateFormat;
                long current = 0;
                for (String timeFormat : pubTimeFormat) {
                    try{
                        pubDateFormat = new SimpleDateFormat(timeFormat, Locale.ENGLISH);
                        current = pubDateFormat.parse(time).getTime();
                    }catch (Exception ignore){}
                }

                blog.setPubTime(new Timestamp(current));
            }

            Element content = document.select(contentRule).first();
            String contentHtml = crawPictureService.downPicture(content,url);
            blog.setContent(contentHtml);

            if (blog.getSummary() == null) {
                blog.setSummary(
                        HtmlUtil.delSpace(
                                HtmlUtil.htmlToText(contentHtml)
                        ).substring(0, summaryWordLimit)
                );
            }

            blog.setOriginal(true);
        } catch (Exception ex) {
            throw new BlogParseException("blog parse error", ex);
        }

        return blog;
    }

    private String getTime(Document document,String url) {
        String time;
        String idTime = null;
        String classTime = null;
        // 获取id为time的文本内容
        Element idElement = document.selectFirst("#time");
        if(idElement != null){
            idTime = idElement.text();
        }

        // 获取class为time的文本内容
        Element classElement = document.selectFirst(".time");
        if (classElement != null) {
            classTime = classElement.text();
        }

        Elements timeElements = document.select(pubTimeRule);
        if (timeElements != null && timeElements.size() > 0){
            try{
                time = timeElements.first().attr("datetime");
            }catch (Exception e){
                try{
                    return timeElements.first().attr("title");
                }catch (Exception ignore){
                    return timeElements.first().text();
                }
            }
        }else {
            if (idTime != null){
                return idTime;
            }
            if (classTime != null){
                return classTime;
            }
            // 特殊情况
            timeElements = document.select(".post-meta");
            try {
                if(timeElements != null){
                    time = timeElements.first().text();
                }else{
                    time = getCreateTime(url);
                }
            }catch (Exception e){
                return "0";
            }
        }
        return time;
    }

    private String getCreateTime(String url) throws IOException {
        String time;
        try{
            Document doc = Jsoup.connect(url).get();
            Element meta = doc.select("meta[property=article:published_time]").first();
            time = meta.attr("content");
        }catch (Exception e){
            try{
                URL Url = new URL(url);
                URLConnection conn = Url.openConnection();
                conn.setConnectTimeout(5000);
                long lastModified = conn.getLastModified();
                Date date = new Date(lastModified);
                time = date.toString();
                return time;
            }catch (Exception ignore){
                return "0";
            }
        }

        return time;
    }
}
