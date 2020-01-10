package top.xcphoenix.groupblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author      xuanc
 * @date        2020/1/10 下午7:09
 * @version     1.0
 */
@Data
@AllArgsConstructor
public class User {

    long uid;
    String username;
    String password;
    long blogType;
    String blogUrl;
    long lastPubBlogTime;
    int category;
    int auth;

}
