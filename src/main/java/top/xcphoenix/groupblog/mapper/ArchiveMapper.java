package top.xcphoenix.groupblog.mapper;

import org.apache.ibatis.annotations.Param;
import top.xcphoenix.groupblog.model.dto.ArchiveBlogs;
import top.xcphoenix.groupblog.model.dto.ArchiveBlogItem;

import java.util.List;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/25 下午7:24
 */
public interface ArchiveMapper {

    /**
     * 获取归档博客id数据
     *
     * @param pageSize   分页大小
     * @param pageOffset 分页偏移量
     * @return 归档数据
     */
    List<ArchiveBlogs> getBaseBlogIds(@Param("pageSize") int pageSize, @Param("pageOffset") int pageOffset);

    /**
     * 获取归档博客id数据
     *
     * @param pageSize   分页大小
     * @param pageOffset 分页偏移量
     * @param uid        用户id
     * @return 归档数据
     */
    // List<ArchiveBlogs> getBaseBlogIdsAsUser(@Param("pageSize") int pageSize,
    //                                         @Param("pageOffset") int pageOffset,
    //                                         @Param("uid") long uid);

    /**
     * 获取归档显示博客数据
     *
     * @param blogIds 博客id
     * @return 博客数据
     */
    List<ArchiveBlogItem> getBlogsFromGroup(@Param("blogIds") String blogIds);

}
