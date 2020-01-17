package top.xcphoenix.groupblog;

import com.alibaba.fastjson.JSON;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.processor.impl.SeleniumProcessor;
import top.xcphoenix.groupblog.service.blog.content.BlogContentService;

import javax.annotation.Resource;
import java.text.ParseException;

@Slf4j
@SpringBootTest
class GroupblogApplicationTests {

    @Autowired
    private SeleniumProcessor processor;

    @Resource(name = "content-csdn")
    private BlogContentService blogContentService;

    @Test
    @Ignore
    void contextLoads() throws ParseException {
        String webContent =
                processor.processor("https://blog.csdn.net/bitcarmanlee/article/details/71057226");
        log.info(JSON.toJSONString(blogContentService.getBlogFromHtml(webContent)));
    }

}
