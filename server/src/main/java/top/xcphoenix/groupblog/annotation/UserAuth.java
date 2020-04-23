package top.xcphoenix.groupblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 普通用户权限
 *
 * @author      xuanc
 * @date        2020/4/23 下午6:03
 * @version     1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAuth {

}
