package top.xcphoenix.groupblog.model.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/10 下午6:13
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogType {

    private int typeId;
    private String typeName;
    private String overviewRule;
    private String blogPageRule;
    private String feedRule;
    /**
     * split by ','
     */
    private String needArg;
    private String beanName;

}
