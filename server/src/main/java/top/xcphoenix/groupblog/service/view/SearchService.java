package top.xcphoenix.groupblog.service.view;

import top.xcphoenix.groupblog.model.vo.SearchData;

/**
 * @author      xuanc
 * @date        2020/1/26 下午8:00
 * @version     1.0
 */ 
public interface SearchService {

    /**
     * 根据关键字搜索博客数据
     *
     * @param keyword 关键字
     * @param pageSize 页大小
     * @param pageOffset 页偏移量
     * @return 博客数据
     */
    SearchData searchAsKeyword(String keyword, int pageSize, int pageOffset);

}
