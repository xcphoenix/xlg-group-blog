package top.xcphoenix.groupblog.manager.dao.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mapper.BlogTypeMapper;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.manager.dao.BlogTypeManager;

/**
 * @author      xuanc
 * @date        2020/1/18 下午4:26
 * @version     1.0
 */
@Service
public class BlogTypeManagerImpl implements BlogTypeManager {

    private BlogTypeMapper blogTypeMapper;

    public BlogTypeManagerImpl(BlogTypeMapper blogTypeMapper) {
        this.blogTypeMapper = blogTypeMapper;
    }

    @Override
    public BlogType getBlogType(int typeId) {
        return blogTypeMapper.getBlogType(typeId);
    }

}
