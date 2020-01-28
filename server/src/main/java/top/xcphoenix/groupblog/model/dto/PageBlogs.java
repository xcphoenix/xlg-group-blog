package top.xcphoenix.groupblog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.xcphoenix.groupblog.model.dao.Blog;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/17 下午6:32
 * @version     1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBlogs {

    private Timestamp oldTime;
    private Timestamp lastTime;

    private List<Blog> blogs;

}
