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
     * @param webContent web 页面内容
     * @return 博客信息
     * @throws ParseException 时间解析
     */
    Blog getBlogFromHtml(String webContent) throws ParseException;

}
