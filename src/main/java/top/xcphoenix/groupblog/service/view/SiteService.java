package top.xcphoenix.groupblog.service.view;

import top.xcphoenix.groupblog.model.vo.SiteSchema;

/**
 * @author      xuanc
 * @date        2020/1/20 上午10:19
 * @version     1.0
 */ 
public interface SiteService {

    /**
     * 获取站点信息
     *
     * @return 站点信息
     */
    SiteSchema getSiteSchema();

    /**
     * 获取指定用户后的站点信息
     *
     * @param uid 用户id
     * @return 站点信息
     * @throws CloneNotSupportedException clone 异常
     */
    SiteSchema getSiteSchemaWithUser(long uid) throws CloneNotSupportedException;

}
