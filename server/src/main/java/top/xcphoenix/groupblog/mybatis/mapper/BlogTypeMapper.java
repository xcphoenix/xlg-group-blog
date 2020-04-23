package top.xcphoenix.groupblog.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import top.xcphoenix.groupblog.model.dao.BlogType;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/15 下午1:40
 */
public interface BlogTypeMapper {

    /**
     * 获取博客类型参数
     *
     * @param typeId 类型id
     * @return 博客类型
     */
    BlogType getBlogType(@Param("typeId") int typeId);

    /**
     * 获取博客类型对应执行抓取任务的 bean
     *
     * @param typeId 类型id
     * @return bean name
     */
    String getBlogCrawlBean(@Param("typeId") int typeId);

}
