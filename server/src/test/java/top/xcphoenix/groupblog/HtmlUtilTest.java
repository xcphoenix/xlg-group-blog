package top.xcphoenix.groupblog;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;
import top.xcphoenix.groupblog.processor.Processor;
import top.xcphoenix.groupblog.utils.HtmlUtil;


/**
 * @author      xuanc
 * @date        2020/1/30 上午10:39
 * @version     1.0
 */
@Slf4j
@SpringBootTest
public class HtmlUtilTest {

    @Autowired
    @Qualifier("direct")
    private Processor processor;

    @Test
    void testHtmlToText() throws ProcessorException {
        Document document = (Document)processor.processor("http://47.94.5.149:6789/blog/104098607");
        String html = document.html();
        String text = HtmlUtil.htmlToCompressedText(html);
        log.info(text);
    }

}
