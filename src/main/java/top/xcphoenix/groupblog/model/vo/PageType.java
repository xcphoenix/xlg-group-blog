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
    INDEX(0),
    /**
     * 关于
     */
    ABOUT(1),
    /**
     * 标签页
     */
    TAG(2),
    /**
     * 分类
     */
    CATEGORY(3),
    /**
     * 归档
     */
    ARCHIVE(4),
    /**
     * 总览页面
     */
    OVERVIEW(5),
    /**
     * 博客页面
     */
    POST(6);

    private int type;

    PageType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
