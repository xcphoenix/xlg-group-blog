package top.xcphoenix.groupblog.service.blog.userzone;

import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.model.dto.PageBlogs;

import java.text.ParseException;
import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/13 下午3:47
 * @version     1.0
 */ 
public interface UserZoneService {

    /**
     * 获取用户中心博客 url
     *
     * @param userZoneUrl 用户博客主页
     * @return blog url
     * @throws Exception 时间解析异常
     */
    PageBlogs getPageBlogUrls(String userZoneUrl) throws Exception;

}
