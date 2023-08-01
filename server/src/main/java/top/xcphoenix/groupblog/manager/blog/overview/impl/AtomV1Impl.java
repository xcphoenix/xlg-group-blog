package top.xcphoenix.groupblog.manager.blog.overview.impl;

import com.rometools.rome.feed.atom.*;
import com.rometools.rome.feed.synd.SyndPerson;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.expection.blog.BlogParseException;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;
import top.xcphoenix.groupblog.manager.blog.content.BlogContentManager;
import top.xcphoenix.groupblog.manager.blog.overview.BlogOverviewManager;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.dto.PageBlogs;
import top.xcphoenix.groupblog.processor.Processor;
import top.xcphoenix.groupblog.service.picture.CrawPictureService;
import top.xcphoenix.groupblog.utils.HtmlUtil;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 基于 atom1.0 规范:
 *    https://validator.w3.org/feed/docs/atom.html#sampleFeed
 *
 * @author      xuanc
 * @date        2020/1/29 下午5:31
 * @version     1.0
 */
@Slf4j
@Service("atom-v1")
public class AtomV1Impl implements BlogOverviewManager {
    private Processor feedProcessor;
    /**
     * summary 字数最小阀值
     */
    protected int summaryContentThreshold = 150;
    protected double summaryFactory = 0.4;

    CrawPictureService crawPictureService;

    public AtomV1Impl(@Qualifier("craw-picture") CrawPictureService crawPictureService,
                      @Qualifier("feed") Processor feedProcessor) {
        this.crawPictureService = crawPictureService;
        this.feedProcessor = feedProcessor;
    }

    @Override
    public PageBlogs getPageBlogUrls(String overviewUrl) throws BlogParseException, ProcessorException {
        Feed atomFeed = (Feed) feedProcessor.processor(overviewUrl);

        List<Blog> blogs = new ArrayList<>();
        PageTime pageTime = PageTime.init();

        String authorBk = null;
        // recommend
        if (atomFeed.getAuthors().size() > 0) {
            authorBk = atomFeed.getAuthors().get(0).getName();
        }

        try {
            for (Entry entry : atomFeed.getEntries()) {
                Blog blog = new Blog();

                // required
                blog.setSourceId(entry.getId());
                blog.setTitle(entry.getTitle());
                Date updatedTime = entry.getUpdated();

                // recommend
                blog.setAuthor(getAuthor(entry, authorBk));
                blog.setOriginalLink(getLink(entry));
                blog.setContent(getContent(entry,blog.getOriginalLink()));
                blog.setSummary(getSummary(entry));

                // optional
                blog.setTags(getTags(entry));
                Timestamp pubTime = getPublishedTime(entry, updatedTime);
                blog.setPubTime(pubTime);

                pageTime.update(pubTime);

                blogs.add(blog);
            }
        } catch (Exception ex) {
            throw new BlogParseException("blog parse error", ex);
        }

        return new PageBlogs(pageTime.getOldTime(), pageTime.getNewTime(), blogs);
    }

    protected String getAuthor(Entry entry, String authorBk) {
        List<SyndPerson> authors =  entry.getAuthors();
        if (authors.size() > 0) {
            return authors.get(0).getName();
        }
        return authorBk;
    }

    protected String getContent(Entry entry,String url) throws IOException {
        List<Content> contents = entry.getContents();
        if (contents.size() == 0) {
            return null;
        }
        // 可能包含 xml 等内容
        if(url == null){
            return contents.get(0).getValue();
        }
        Element element = Jsoup.parse(contents.get(0).getValue());
        return crawPictureService.downPicture(element,url);
    }

    protected String getLink(Entry entry) {
        List<Link> links = entry.getAlternateLinks();
        if (links.size() == 0) {
            return null;
        }
        return links.get(0).getHref();
    }

    protected String getSummary(Entry entry) throws IOException {
        Content contentSummary = entry.getSummary();
        String summary;
        if (contentSummary == null) {
            return null;
        }
        if (!contentSummary.getType().equals(Content.TEXT)) {
            summary = HtmlUtil.htmlToCompressedText(contentSummary.getValue());
        } else {
            summary = contentSummary.getValue();
        }
        if (summary.length() < summaryFactory * summaryContentThreshold) {
            String content = getContent(entry,null);
            if (content != null) {
                summary = HtmlUtil.htmlToCompressedText(content)
                        .substring(0, summaryContentThreshold)
                        + "...";
            }
        }
        return summary;
    }

    protected String getTags(Entry entry) {
        List<Category> categories = entry.getCategories();
        String[] tags = new String[categories.size()];

        if (categories.size() == 0) {
            return null;
        }
        for (int i = 0; i < categories.size(); i++) {
            tags[i] = categories.get(i).getTerm();
        }
        return StringUtils.join(tags, ',');
    }

    protected Timestamp getPublishedTime(Entry entry, Date updatedTime) {
        if (entry.getPublished() == null) {
            return new Timestamp(updatedTime.getTime());
        }
        return new Timestamp(entry.getPublished().getTime());
    }

}

@Getter
class PageTime {

    Timestamp oldTime;
    Timestamp newTime;

    private PageTime(long oldTime, long newTime) {
        this.oldTime = new Timestamp(oldTime);
        this.newTime = new Timestamp(newTime);
    }

    public static PageTime init() {
        return new PageTime(System.currentTimeMillis(), 0L);
    }

    public void update(Timestamp currentTime) {
        long timeValue = currentTime.getTime();
        if (timeValue > this.newTime.getTime()) {
            this.newTime = currentTime;
        }
        if (timeValue < this.oldTime.getTime()) {
            this.oldTime = currentTime;
        }
    }

}