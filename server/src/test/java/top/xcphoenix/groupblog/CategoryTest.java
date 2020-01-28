package top.xcphoenix.groupblog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.mapper.CategoryMapper;
import top.xcphoenix.groupblog.mapper.UserMapper;
import top.xcphoenix.groupblog.service.view.CategoryService;

/**
 * @author      xuanc
 * @date        2020/1/25 下午3:28
 * @version     1.0
 */
@Slf4j
@SpringBootTest
public class CategoryTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void testAllCategory() {
        log.info(JSON.toJSONString(
               categoryService.getCategoryData(), SerializerFeature.PrettyFormat
        ));
    }

}
