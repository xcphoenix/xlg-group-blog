package top.xcphoenix.groupblog.service.view.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.config.MixPropertySourceFactory;
import top.xcphoenix.groupblog.model.vo.PostData;
import top.xcphoenix.groupblog.service.view.AboutService;

import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/23 下午2:29
 * @version     1.0
 */
@Service
public class AboutServiceImpl implements AboutService {

    private PostData postData;

    public AboutServiceImpl(PostData postData) {
        this.postData = postData;
    }

    @Override
    public PostData getAbout() {
        return postData;
    }

}

@Component("aboutData")
@PropertySource(value = {"${config-dir}/view/about.yml"},
        factory = MixPropertySourceFactory.class)
@ConfigurationProperties(prefix = "about")
class AboutData extends PostData {
}