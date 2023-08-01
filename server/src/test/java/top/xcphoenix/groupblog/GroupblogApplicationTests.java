package top.xcphoenix.groupblog;

import com.alibaba.fastjson.JSON;
//import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.model.vo.SiteSchema;

import java.text.ParseException;

@Slf4j
@SpringBootTest
class GroupblogApplicationTests {

    @Autowired
    private SiteSchema siteSchema;

    @Test
    @Ignore
    void contextLoads() throws ParseException {
        log.info(JSON.toJSONString(siteSchema));
    }

}
