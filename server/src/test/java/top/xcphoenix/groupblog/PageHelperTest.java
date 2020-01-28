package top.xcphoenix.groupblog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.manager.dao.BlogManager;

/**
 * @author      xuanc
 * @date        2020/1/22 上午11:04
 * @version     1.0
 */
@Slf4j
@SpringBootTest
public class PageHelperTest {

    @Autowired
    private BlogManager blogManager;

    @Test
    void onJoinSelect() {
        log.info(JSON.toJSONString(blogManager.getBlogSummaries(10, 0), SerializerFeature.PrettyFormat));
    }

}
