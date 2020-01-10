package top.xcphoenix.groupblog.processor;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/10 下午7:06
 */
public interface Processor {

    /**
     * 处理页面
     *
     * @param url 待处理的页面
     * @return result data
     */
    String processor(String url);

}
