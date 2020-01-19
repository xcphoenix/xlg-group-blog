package top.xcphoenix.groupblog;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.processor.impl.SeleniumProcessor;
import top.xcphoenix.groupblog.manager.blog.content.BlogContentManager;

import javax.annotation.Resource;
import java.text.ParseException;

@Slf4j
@SpringBootTest
class GroupblogApplicationTests {

    @Autowired
    private SeleniumProcessor processor;

    @Resource(name = "content-csdn")
    private BlogContentManager blogContentManager;

    @Test
    @Ignore
    void contextLoads() throws ParseException {

    }

}
