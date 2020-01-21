package top.xcphoenix.groupblog.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author      xuanc
 * @date        2020/1/21 上午10:58
 * @version     1.0
 */
@Data
@NoArgsConstructor
public class PageLink {
    int num;
    String link;

    public PageLink(int num, String link) {
        this.num = num;
        this.link = link;
    }

    public int getNum() {
        return num;
    }

    public String getLink() {
        return link;
    }

}