package top.xcphoenix.groupblog.service.view.impl;

import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.service.view.LinkGeneratorService;

/**
 * @author      xuanc
 * @date        2020/1/25 下午1:35
 * @version     1.0
 */
@Service
public class LinkGeneratorServiceImpl implements LinkGeneratorService {

    private static final String INDEX_LINK_PREFIX = "/";
    private static final String BLOG_LINK_PREFIX = "/blog";
    private static final String USER_LINK_PREFIX = "/user";
    private static final String CATEGORY_LINK_PREFIX = "/category";
    private static final String ARCHIVE_LINK_PREFIX = "/archive";

    @Override
    public String getIndexLinkPrefix() {
        return INDEX_LINK_PREFIX;
    }

    @Override
    public String getBlogLink(long blogId) {
        return getBlogLink(BLOG_LINK_PREFIX, blogId);
    }

    @Override
    public String getBlogLink(String prefix, long blogId) {
        prefix = prefix == null ? BLOG_LINK_PREFIX : prefix;
        return prefix + "/" + blogId;
    }

    @Override
    public String getBlogPrefixLinkOnUser(long uid) {
        return USER_LINK_PREFIX + "/" + uid + BLOG_LINK_PREFIX;
    }

    @Override
    public String getUserLink(long uid) {
        return USER_LINK_PREFIX + "/" + uid;
    }

    @Override
    public String getCategoryLink(int categoryId) {
        return CATEGORY_LINK_PREFIX + "/" + categoryId;
    }

    @Override
    public String getArchiveLinkPrefix() {
        return ARCHIVE_LINK_PREFIX;
    }

    @Override
    public String getArchiveLinkAsUser(long uid) {
        return getArchiveLinkPrefix() + "/user/" + uid;
    }

}
