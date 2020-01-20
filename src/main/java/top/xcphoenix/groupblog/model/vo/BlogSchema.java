package top.xcphoenix.groupblog.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author      xuanc
 * @date        2020/1/19 下午7:46
 * @version     1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogSchema {

    private String title;
    private String blogLink;
    private boolean isOriginal;
    private String flagDesc;
    private String pubTime;
    private String author;
    private String authorLink;
    private String category;
    private String categoryLink;
    private String summary;

}

