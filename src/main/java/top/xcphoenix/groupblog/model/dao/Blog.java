package top.xcphoenix.groupblog.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author      xuanc
 * @date        2020/1/10 下午6:13
 * @version     1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    private long blogId;
    private long uid;
    private String title;
    private String author;
    private boolean isOriginal;
    private Timestamp pubTime;
    private String summary;
    private String content;
    private String originalLink;
    private String tags;

}
