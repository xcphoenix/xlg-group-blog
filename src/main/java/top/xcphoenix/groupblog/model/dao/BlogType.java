package top.xcphoenix.groupblog.model.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/10 下午6:13
 */
@Getter
public class BlogType {

    private int typeId;
    private String typeName;
    private String userZoneRegex;
    private String blogPageRegex;

    private Pattern userZonePattern;
    private Pattern blogPagePattern;

    public BlogType(int typeId, String typeName, String userZoneRegex, String blogPageRegex) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.userZoneRegex = userZoneRegex;
        this.blogPageRegex = blogPageRegex;
        this.blogPagePattern = Pattern.compile(blogPageRegex);
        this.userZonePattern = Pattern.compile(userZoneRegex);
    }

}
