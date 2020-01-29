package top.xcphoenix.groupblog.model.dao;

import lombok.Data;

/**
 * 友情链接
 *
 * @author      xuanc
 * @date        2020/1/20 上午10:50
 * @version     1.0
 */
@Data
public class Links {

    private Long linkId;
    private String desc;
    private String link;
    /**
     * in fontawesome
     */
    private String iconClass;

}
