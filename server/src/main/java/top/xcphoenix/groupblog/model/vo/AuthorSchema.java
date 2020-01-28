package top.xcphoenix.groupblog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户数据
 * no url
 *
 * @author      xuanc
 * @date        2020/1/20 上午9:24
 * @version     1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSchema {

    private String author;
    /**
     * 用户：个性签名，转义Html
     * 站点：不转义html
     */
    private String signature;

}
