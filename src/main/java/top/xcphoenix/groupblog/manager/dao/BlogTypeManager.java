package top.xcphoenix.groupblog.manager.dao;

import top.xcphoenix.groupblog.model.dao.BlogType;

/**
 * @author      xuanc
 * @date        2020/1/18 下午4:23
 * @version     1.0
 */ 
public interface BlogTypeManager {

    /**
     * 获取博客类型信息
     *
     * @param typeId 类型id
     * @return 类型数据
     */
    BlogType getBlogType(int typeId);

}
