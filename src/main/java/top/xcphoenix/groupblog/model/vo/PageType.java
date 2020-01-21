package top.xcphoenix.groupblog.model.vo;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/21 下午3:34
 */
public enum PageType {

    /**
     * 首页
     */
    OVERVIEW(1),
    /**
     * 博客页面
     */
    POST(2),
    /**
     * 标签页
     */
    TAG(3),
    /**
     * 关于
     */
    ABOUT(4),
    /**
     * 分类
     */
    CATEGORY(5),
    /**
     * 归档
     */
    ARCHIVE(6);

    private int type;

    PageType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
