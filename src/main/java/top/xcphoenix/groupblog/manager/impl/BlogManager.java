package top.xcphoenix.groupblog.manager.impl;

import java.text.ParseException;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/15 下午3:25
 */
public interface BlogManager {

    /**
     * 设置用户id
     *
     * @param uid 用户id
     */
    void setUid(long uid);

    /**
     * 设置url
     *
     * @param url 链接
     */
    void setUrl(String url);

    /**
     * 执行任务
     *
     * @throws Exception 产生的异常
     */
    void exec() throws Exception;

}
