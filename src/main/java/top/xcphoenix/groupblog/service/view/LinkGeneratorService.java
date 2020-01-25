package top.xcphoenix.groupblog.service.view;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/25 下午1:34
 */
public interface LinkGeneratorService {

    /**
     * 首页链接
     *
     * @return 首页url
     */
    String getIndexLinkPrefix();

    /**
     * 获取博客链接
     *
     * @param blogId 博客id
     * @return 博客链接
     */
    String getBlogLink(long blogId);

    /**
     * 获取博客链接
     *
     * @param prefix 前缀
     * @param blogId 博客id
     * @return 博客链接
     */
    String getBlogLink(String prefix, long blogId);

    /**
     * 获取指定用户下的博客前缀
     *
     * @param uid 用户id
     * @return 指定用户下的博客前缀
     */
    String getBlogPrefixLinkOnUser(long uid);

    /**
     * 获取用户链接
     *
     * @param uid 用户id
     * @return 用户链接
     */
    String getUserLink(long uid);

    /**
     * 获取分类链接
     *
     * @param categoryId 分类id
     * @return 分类链接
     */
    String getCategoryLink(int categoryId);

    String getArchiveLinkPrefix();
}
