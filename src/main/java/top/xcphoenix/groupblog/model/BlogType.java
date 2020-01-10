package top.xcphoenix.groupblog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/10 下午6:13
 */
@Getter
@AllArgsConstructor
public class BlogType {

    private long typeId;
    private String typeName;
    private Pattern userZonePattern;
    private Pattern blogPagePattern;

    public BlogType(String name, String userZoneRegex, String blogPageRegex) {
        this.typeName = name;
        this.userZonePattern = Pattern.compile(userZoneRegex);
        this.blogPagePattern = Pattern.compile(blogPageRegex);
    }

}
