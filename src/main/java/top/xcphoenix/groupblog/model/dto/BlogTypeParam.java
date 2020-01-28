package top.xcphoenix.groupblog.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/28 下午9:55
 * @version     1.0
 */
@Data
@NoArgsConstructor
public class BlogTypeParam {

    int blogType = 1;
    Map<String, String> params;

}
