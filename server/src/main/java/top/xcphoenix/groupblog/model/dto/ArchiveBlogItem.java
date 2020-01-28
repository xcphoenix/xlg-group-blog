package top.xcphoenix.groupblog.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author xuanc
 */
@Data
@NoArgsConstructor
public class ArchiveBlogItem {

    private String monthDay;
    private String title;
    private Long uid;
    private String username;
    private long blogId;
    private String blogLink;
    private String userLink;

}
