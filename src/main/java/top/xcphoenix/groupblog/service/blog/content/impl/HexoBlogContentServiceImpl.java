package top.xcphoenix.groupblog.service.blog.content.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.model.dao.Blog;
import top.xcphoenix.groupblog.service.blog.content.BlogContentService;

import java.text.ParseException;

/**
 * @author      xuanc
 * @date        2020/1/12 上午12:41
 * @version     1.0
 */
@Slf4j
@Service("content-hexo")
public class HexoBlogContentServiceImpl implements BlogContentService {

    @Override
    public Blog getBlogFromHtml(String webContent) {
        return null;
    }

    @Override
    public void getBlogFromHtml(String webContent, Blog blog) throws ParseException {

    }

}
