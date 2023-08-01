package top.xcphoenix.groupblog.service.crawl;

import top.xcphoenix.groupblog.expection.blog.BlogParseException;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/15 下午3:16
 */
public interface CrawlBlogService {

    /**
     * 爬取用户的所有博客
     *
     * @param uid 用户id
     * @throws Exception 抓取产生的异常
     */
    void crawlAll(long uid) throws Exception;

    /**
     * 增量获取用户博客
     *
     * @param uid 用户id
     */
    void crawlIncrement(long uid);

}
