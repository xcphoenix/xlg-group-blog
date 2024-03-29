package top.xcphoenix.groupblog.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/23 下午2:23
 * @version     1.0
 */
@Data
@NoArgsConstructor
public class PostData<T> {

    String postTitle;
    Map<String, Object> postMeta;
    T postBody;

}
