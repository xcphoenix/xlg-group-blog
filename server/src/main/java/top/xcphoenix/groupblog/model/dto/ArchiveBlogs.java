package top.xcphoenix.groupblog.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/25 下午9:23
 * @version     1.0
 */
@Data
@NoArgsConstructor
public class ArchiveBlogs {

    private String year;
    private String blogIds;
    private List<ArchiveBlogItem> blogItems;

}

