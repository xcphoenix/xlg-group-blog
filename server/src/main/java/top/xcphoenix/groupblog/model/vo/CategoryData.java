package top.xcphoenix.groupblog.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/25 下午1:25
 * @version     1.0
 */
@Data
@NoArgsConstructor
public class CategoryData {

    private int categoryId;
    private String categoryName;
    private String categoryLink;

    List<UserItem> userItemList;

}