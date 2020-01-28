package top.xcphoenix.groupblog.mapper;

import org.apache.ibatis.annotations.Param;
import top.xcphoenix.groupblog.model.vo.BlogData;

import java.util.List;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/26 下午5:49
 */
public interface SearchMapper {

    /**
     * 全文搜索
     *
     * @param keyword    关键字
     * @param pageSize   页大小
     * @param pageOffset 页偏移量
     * @return 搜索结果
     */
    List<BlogData> searchBlogsAsKeyword(@Param("keyword") String keyword,
                                        @Param("pageSize") int pageSize,
                                        @Param("pageOffset") int pageOffset);

    /**
     * 获取关键字搜索数目
     *
     * @param keyword 关键字
     * @return 数据条数
     */
    int getSearchNum(@Param("keyword") String keyword);

}
