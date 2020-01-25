package top.xcphoenix.groupblog.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.xcphoenix.groupblog.model.dao.Category;
import top.xcphoenix.groupblog.model.dao.User;

/**
 * @author      xuanc
 * @date        2020/1/25 下午1:25
 * @version     1.0
 */
@Data
@NoArgsConstructor
public class CategoryData {

    private User user;
    private Category category;

    private String userLink;
    private String categoryLink;

}
