package top.xcphoenix.groupblog.service.view.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.manager.dao.BlogManager;
import top.xcphoenix.groupblog.manager.dao.StaticsNumsManager;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.model.dto.NumStatics;
import top.xcphoenix.groupblog.model.vo.AuthorSchema;
import top.xcphoenix.groupblog.model.vo.SiteSchema;
import top.xcphoenix.groupblog.service.blog.BlogService;
import top.xcphoenix.groupblog.service.view.SiteService;

/**
 * @author      xuanc
 * @date        2020/1/20 上午10:23
 * @version     1.0
 */
@Service
public class SiteServiceImpl implements SiteService {

    private StaticsNumsManager staticsNumsManager;
    private UserManager userManager;
    /**
     * 共用
     */
    private SiteSchema commonSiteSchema;

    public SiteServiceImpl(StaticsNumsManager staticsNumsManager,
                           UserManager userManager, SiteSchema commonSiteSchema) {
        this.staticsNumsManager = staticsNumsManager;
        this.userManager = userManager;
        this.commonSiteSchema = commonSiteSchema;
    }

    @Override
    public SiteSchema getSiteSchema() {
        NumStatics numStatics = staticsNumsManager.getSiteStaticsNum();
        commonSiteSchema.setNumStatics(numStatics);
        return commonSiteSchema;
    }

    @Override
    public SiteSchema getSiteSchemaWithUser(long uid) throws CloneNotSupportedException {
        SiteSchema siteSchema = commonSiteSchema.clone();
        siteSchema.setInAuthor(true);

        int blogNum = staticsNumsManager.getUserStaticsNum(uid);
        NumStatics numStatics = new NumStatics();
        numStatics.setBlogNum(blogNum);
        siteSchema.setNumStatics(numStatics);

        User user = userManager.getUserDesc(uid);
        siteSchema.setAuthorSchema(new AuthorSchema(user.getUsername(), user.getSignature()));

        // 以后加
        siteSchema.setFriendLinks(null);
        return siteSchema;
    }

}
