package top.xcphoenix.groupblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.mapper.UserMapper;
import top.xcphoenix.groupblog.model.dao.User;

/**
 * @author      xuanc
 * @date        2020/1/15 下午3:53
 * @version     1.0
 */
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testTmp() {
        long uid = 10000;
        User user = userMapper.getUserBlogArgs(uid);
    }

}
