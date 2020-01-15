package top.xcphoenix.groupblog;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.manager.impl.CsdnManager;
import top.xcphoenix.groupblog.service.blog.userzone.UserZoneService;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author      xuanc
 * @date        2020/1/13 下午3:57
 * @version     1.0
 */
@Slf4j
@SpringBootTest
public class UserZoneTest {

    @Resource(name = "zone-csdn")
    private UserZoneService userZoneService;

    @Resource(name = "manager-csdn")
    private CsdnManager csdnManager;

    @Test
    void tmp() throws ParseException {
        userZoneService.getPageBlogUrls("https://blog.csdn.net/qq_17034925?t=1");
    }

    @Test
    void getUserBlogs() throws ParseException {
        csdnManager.setUrl("https://blog.csdn.net/lalala323/article/list");
        csdnManager.exec();
    }

}
