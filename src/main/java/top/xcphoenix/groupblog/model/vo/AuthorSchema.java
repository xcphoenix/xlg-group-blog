package top.xcphoenix.groupblog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户数据
 * no url
 *
 * @author      xuanc
 * @date        2020/1/20 上午9:24
 * @version     1.0
 */
@Data
@AllArgsConstructor
public class AuthorSchema {

    String author;
    /**
     * 用户：个性签名，转义Html
     * 站点：不转义html
     */
    String signature;

}
