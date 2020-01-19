package top.xcphoenix.groupblog.manager.dao;

import top.xcphoenix.groupblog.model.dao.Blog;

/**
 * @author      xuanc
 * @date        2020/1/17 下午6:17
 * @version     1.0
 */ 
public interface BlogManager {

    /**
     * 添加博客
     *
     * @param blog 博客信息
     */
    void addBlog(Blog blog);

    /**
     * 博客是否存在
     *
     * @param blogId 博客id
     * @return 博客存在否
     */
    boolean exists(long blogId);

}
