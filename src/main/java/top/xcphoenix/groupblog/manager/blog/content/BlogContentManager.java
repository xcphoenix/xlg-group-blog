package top.xcphoenix.groupblog.manager.blog.content;

import top.xcphoenix.groupblog.expection.blog.BlogParseException;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;
import top.xcphoenix.groupblog.model.dao.Blog;

/**
 * @author      xuanc
 * @date        2020/1/12 上午12:40
 * @version     1.0
 */
@FunctionalInterface
public interface BlogContentManager {

    /**
     * 获取博客信息
     *
     * @param url 博客url
     * @param blog 博客初始信息
     *             若blog不为空，保留原有数据
     *             若blog==null，返回页面解析数据
     * @return 博客信息
     * @throws ProcessorException 页面获取异常
     * @throws BlogParseException 页面解析异常
     */
    Blog getBlog(String url, Blog blog) throws ProcessorException, BlogParseException;

}
