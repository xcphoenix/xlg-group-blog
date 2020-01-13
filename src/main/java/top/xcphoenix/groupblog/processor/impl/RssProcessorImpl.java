package top.xcphoenix.groupblog.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.xcphoenix.groupblog.processor.Processor;

/**
 * @author      xuanc
 * @date        2020/1/12 上午12:17
 * @version     1.0
 */
@Slf4j
@Component("rss")
public class RssProcessorImpl implements Processor {

    @Override
    public String processor(String url) {
        return null;
    }

}
