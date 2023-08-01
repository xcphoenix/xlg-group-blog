package top.xcphoenix.groupblog.service.blog.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.manager.dao.BlogManager;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.service.blog.BlogService;

@Service("service-md")
@Slf4j
public class DefaultServiceImpl implements BlogService {
    private BlogManager blogManager;
    private UserManager userManager;

    public DefaultServiceImpl(BlogManager blogManager, UserManager userManager) {
        this.blogManager = blogManager;
        this.userManager = userManager;
    }
    @Override
    public void execFull(User user, BlogType blogType) {
        execIncrement(user,blogType);
    }

    //todo：你问我为什么没写？因为用户没提供，下一个!!!（现在他是属于noBlog的）
    @Override
    public void execIncrement(User user, BlogType blogType) {
        log.info("start noBlog");
        // 根据用户提供信息查询下级页面（即获取文件夹信息）

        // 获取md（或直接从页面获取html）

        // md转html
    }
}
