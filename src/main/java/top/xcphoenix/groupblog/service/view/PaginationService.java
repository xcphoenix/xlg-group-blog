package top.xcphoenix.groupblog.service.view;

import top.xcphoenix.groupblog.model.vo.Pagination;

/**
 * @author      xuanc
 * @date        2020/1/25 下午2:22
 * @version     1.0
 */ 
public interface PaginationService {

    /**
     * 获取分页数据
     *
     * @param pageNum 页数
     * @param pageSize　页大小
     * @param baseLink 基础链接
     * @return 分页数据
     */
    Pagination getPagination(int pageNum, int pageSize, String baseLink);

    /**
     * 获取分页数据
     *
     * @param pageNum 页数
     * @param pageSize　页大小
     * @param baseLink 基础链接
     * @param uid 用户id
     * @return 分页数据
     */
    Pagination getPaginationAsUser(int pageNum, int pageSize, String baseLink, long uid);
}
