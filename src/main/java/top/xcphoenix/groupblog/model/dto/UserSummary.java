package top.xcphoenix.groupblog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author      xuanc
 * @date        2020/1/19 下午3:05
 * @version     1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummary {

    private long uid;
    private String beanName;

}
