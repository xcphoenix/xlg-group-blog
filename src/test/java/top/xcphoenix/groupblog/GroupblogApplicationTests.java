package top.xcphoenix.groupblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.processor.impl.SeleniumProcessor;

@SpringBootTest
class GroupblogApplicationTests {

    @Autowired
    private SeleniumProcessor processor;

    @Test
    void contextLoads() {
        System.out.println(processor.processor("https://blog.csdn.net/bitcarmanlee/article/details/71057226"));
    }

}
