package top.xcphoenix.groupblog.model.dao;

import lombok.Getter;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/4/23 下午6:41
 */
@Getter
public enum Auth {
    /**
     * 普通用户
     */
    USER(1),
    /**
     * 管理员
     */
    ADMIN(2),
    /**
     * root 用户
     */
    ROOT(3);

    private final int val;

    Auth(int val) {
        this.val = val;
    }

}