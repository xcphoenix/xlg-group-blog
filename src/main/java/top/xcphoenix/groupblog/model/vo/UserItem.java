package top.xcphoenix.groupblog.model.vo;

import lombok.Data;
import top.xcphoenix.groupblog.model.dao.User;

/**
 * @author      xuanc
 * @date        2020/1/25 下午3:21
 * @version     1.0
 */
@Data
public class UserItem {

    private User user;
    private String userLink;

    public String getUserName() {
        return user.getUsername();
    }

    public String getUserLink() {
        return userLink;
    }

}