package top.xcphoenix.groupblog.mapper;

import org.apache.ibatis.annotations.Param;
import top.xcphoenix.groupblog.model.dto.NumStatics;

import java.util.List;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/20 上午11:17
 */
public interface StaticsNumsMapper {

    /**
     * 获取站点数据统计，博客数、分类数、标签数
     *
     * @return 站点数据
     */
    NumStatics getSiteStaticsNum();

    /**
     * 获取用户统计数据，博客数
     *
     * @param uid 用户uid
     * @return 用户统计数据
     */
    int getUserStaticsNum(@Param("uid") long uid);

}
