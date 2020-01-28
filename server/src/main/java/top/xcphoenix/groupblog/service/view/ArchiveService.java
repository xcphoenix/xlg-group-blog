package top.xcphoenix.groupblog.service.view;

import top.xcphoenix.groupblog.model.dto.ArchiveBlogs;

import java.util.List;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/25 下午10:02
 */
public interface ArchiveService {

    /**
     * 获取总归档数据
     *
     * @param pageNum 页数
     * @param pageSize 大小
     * @return 归档数据
     */
    List<ArchiveBlogs> getArchive(int pageNum, int pageSize);

    /**
     * 获取用户归档数据
     *
     * @param uid 用户id
     * @param pageNum 页数
     * @param pageSize 大小
     * @return 归档数据
     */
    List<ArchiveBlogs> getArchiveAsUser(long uid, int pageNum, int pageSize);
}
