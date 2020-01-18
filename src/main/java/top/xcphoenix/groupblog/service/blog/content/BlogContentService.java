package top.xcphoenix.groupblog.service.blog.content;

import top.xcphoenix.groupblog.model.dao.Blog;

import java.text.ParseException;

/**
 * @author      xuanc
 * @date        2020/1/12 上午12:40
 * @version     1.0
 */ 
public interface BlogContentService {

    /**
     * 获取博客信息
     *
     * @param url 博客url
     * @param blog 博客初始信息
     *             若blog不为空，保留原有数据
     *             若blog==null，返回页面解析数据
     * @return 博客信息
     * @throws Exception 时间解析
     */
    Blog getBlog(String url, Blog blog) throws Exception;

}
