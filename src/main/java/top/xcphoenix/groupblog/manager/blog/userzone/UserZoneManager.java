package top.xcphoenix.groupblog.manager.blog.userzone;

import top.xcphoenix.groupblog.expection.blog.BlogParseException;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;
import top.xcphoenix.groupblog.model.dto.PageBlogs;

/**
 * @author      xuanc
 * @date        2020/1/13 下午3:47
 * @version     1.0
 */ 
public interface UserZoneManager {

    /**
     * 获取用户中心博客 url
     *
     * @param userZoneUrl 用户博客主页
     * @return blog url
     * @throws ProcessorException 页面获取异常
     * @throws BlogParseException 页面解析异常
     */
    PageBlogs getPageBlogUrls(String userZoneUrl) throws BlogParseException, ProcessorException;

}
