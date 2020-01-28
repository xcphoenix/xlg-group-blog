package top.xcphoenix.groupblog.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import top.xcphoenix.groupblog.config.MixPropertySourceFactory;
import top.xcphoenix.groupblog.model.dto.NumStatics;

import java.util.List;

/**
 * TODO 用户友链表
 *
 * @author xuanc
 * @version 1.0
 * @date 2020/1/19 下午7:44
 */
@Data
@NoArgsConstructor
@Component
@PropertySource(value = {"file:${config-dir}/view/site.yml"}, factory = MixPropertySourceFactory.class)
@ConfigurationProperties(prefix = "site")
public class SiteSchema implements Cloneable {

    private String siteTitle;
    private String siteSubTitle;

    private String avatarUrl;
    private boolean isInAuthor;
    private NumStatics numStatics;

    private AuthorSchema authorSchema;
    private SidebarLinks sidebarLinks;
    private SidebarLinks userLinks;
    private List<Links> friendLinks;

    /**
     * 浅拷贝
     */
    @Override
    public SiteSchema clone() throws CloneNotSupportedException {
        return (SiteSchema) super.clone();
    }

}
