package top.xcphoenix.groupblog.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author      xuanc
 * @date        2020/1/20 上午9:17
 * @version     1.0
 */
@Data
@NoArgsConstructor
public class SidebarLinks {

    private String indexUrl;
    private String aboutUrl;
    private String tagUrl;
    private String categoryUrl;
    private String archiveUrl;

}
