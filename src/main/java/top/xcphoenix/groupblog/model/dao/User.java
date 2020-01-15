package top.xcphoenix.groupblog.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author      xuanc
 * @date        2020/1/10 下午7:09
 * @version     1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    long uid;
    String username;
    String password;
    int blogType;
    /**
     * use json str
     */
    String blogArg;
    Timestamp lastPubTime;
    int categoryId;
    int authority;

}
