package top.xcphoenix.groupblog.service.api;

import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dto.BlogTypeParam;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/27 下午5:46
 * @version     1.0
 */
public interface BlogTypeService {

    /**
     * 获取指定类型需要的参数、id
     * @param typeId 类型id
     * @return 博客所需参数、说明
     */
    String getParams(int typeId);

    /**
     * 校验参数信息
     *
     * @param uid 用户id
     * @param blogTypeParam 更新的参数
     * @param needParams 需要的参数
     * @return 用户博客参数，null 表示数据不合法
     */
    String validateParams(long uid, BlogTypeParam blogTypeParam, String needParams);

    /**
     * 获取博客类型
     * @return 博客类型
     */
    List<BlogType> getType();
}
