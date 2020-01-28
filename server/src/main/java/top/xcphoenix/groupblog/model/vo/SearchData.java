package top.xcphoenix.groupblog.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/26 下午7:54
 * @version     1.0
 */
@Data
@NoArgsConstructor
public class SearchData {

    String keyword;
    List<BlogData> blogDataList;

}
