package top.xcphoenix.groupblog.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import top.xcphoenix.groupblog.expection.processor.ProcessorException;
import top.xcphoenix.groupblog.processor.Processor;

import java.io.IOException;

/**
 * 直接获取web页面
 *
 * @author      xuanc
 * @date        2020/1/29 下午5:01
 * @version     1.0
 */
@Slf4j
@Component("direct")
public class DirectGetWebPageProcessor implements Processor {

    @Override
    public Object processor(String url) throws ProcessorException {
        Connection connection = Jsoup.connect(url);
        Document document;
        try {
            document = connection.get();
        } catch (IOException ex) {
            throw new ProcessorException("IO error", ex);
        }
        return document;
    }

}