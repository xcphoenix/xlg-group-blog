package top.xcphoenix.groupblog.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Repeatable;
import java.sql.Timestamp;

/**
 * TODO
 *   - 个人设置中心
 *   - 邮箱（使用第三方接口获取头像）
 *
 * @author      xuanc
 * @date        2020/1/10 下午7:09
 * @version     1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    Long uid;
    String username;
    String password;
    String qq;
    String signature;
    Integer blogType;
    /**
     * use json str
     */
    String blogArg;
    Timestamp lastPubTime;
    Integer categoryId;
    Auth authority;
    String avatarUrl;
    // 添加成员时使用
    String grade;
}
