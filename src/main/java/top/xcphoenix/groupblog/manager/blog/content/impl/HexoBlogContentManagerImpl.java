package top.xcphoenix.groupblog.manager.blog.content.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.manager.blog.content.BlogContentManager;

/**
 * @author      xuanc
 * @date        2020/1/12 上午12:41
 * @version     1.0
 */
@Slf4j
@Service("content-hexo")
public class HexoBlogContentManagerImpl implements BlogContentManager {


    @Override
    public Blog getBlog(String url, Blog blog) throws Exception {
        return null;
    }

}
