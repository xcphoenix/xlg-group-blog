package top.xcphoenix.groupblog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.mapper.ArchiveMapper;
import top.xcphoenix.groupblog.service.view.ArchiveService;

/**
 * @author      xuanc
 * @date        2020/1/25 下午10:15
 * @version     1.0
 */
@Slf4j
@SpringBootTest
public class ArchiveTest {

    @Autowired
    private ArchiveService archiveService;
    @Autowired
    private ArchiveMapper archiveMapper;

    @Test
    void testArchive() {
        log.info(JSON.toJSONString(
                archiveService.getArchive(1, 10),
                SerializerFeature.PrettyFormat
        ));
    }

}
