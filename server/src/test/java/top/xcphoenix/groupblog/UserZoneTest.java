package top.xcphoenix.groupblog;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.service.blog.impl.CsdnServiceImpl;
import top.xcphoenix.groupblog.manager.blog.userzone.UserZoneManager;

import javax.annotation.Resource;

/**
 * @author      xuanc
 * @date        2020/1/13 下午3:57
 * @version     1.0
 */
@Slf4j
@SpringBootTest
public class UserZoneTest {

    @Resource(name = "zone-csdn")
    private UserZoneManager userZoneService;

    @Resource(name = "service-csdn")
    private CsdnServiceImpl csdnManager;

    @Test
    @Disabled
    void tmp() throws Exception {
        userZoneService.getPageBlogUrls("https://blog.csdn.net/qq_17034925?t=1");
    }

}
