package top.xcphoenix.groupblog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/15 下午2:09
 * @version     1.0
 */
@SpringBootTest
public class UrlUtilTest {

    @Test
    void testEffectiveness() {
        String json =
                "{\n" +
                "  \"id\": \"file\",\n" +
                "  \"value\": \"File\",\n" +
                "  \"popup\": {\n" +
                "    \"menuitem\": [\n" +
                "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\n" +
                "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n" +
                "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n" +
                "    ]\n" +
                "   }\n" +
                "}";
        Map<String, Object> map = JSONObject.parseObject(json).getInnerMap();
        Assertions.assertEquals(map.get("id"), "file");
    }

}
