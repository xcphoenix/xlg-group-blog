package top.xcphoenix.groupblog.manager.dao;

import top.xcphoenix.groupblog.model.vo.BlogData;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/26 下午7:13
 * @version     1.0
 */ 
public interface SearchManager {

    /**
     * 根据关键字搜索博客数据
     *
     * @param keyword 搜索关键字
     * @param pageSize 页大小
     * @param pageOffset 页偏移量
     * @return 博客数据
     */
     List<BlogData> searchBlogsAsKeywords(String keyword, int pageSize, int pageOffset);

    /**
     * 获取关键字检索到的数据量
     *
     * @param keyword 关键字
     * @return 数据量
     */
     int getSearchDataNum(String keyword);

}
