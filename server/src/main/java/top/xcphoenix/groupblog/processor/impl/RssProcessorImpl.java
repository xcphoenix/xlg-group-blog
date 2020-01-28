package top.xcphoenix.groupblog.processor.impl;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.xcphoenix.groupblog.expection.processor.RssProcessorException;
import top.xcphoenix.groupblog.processor.Processor;

import java.io.IOException;
import java.net.URL;

/**
 * @author      xuanc
 * @date        2020/1/12 上午12:17
 * @version     1.0
 */
@Slf4j
@Component("rss")
public class RssProcessorImpl implements Processor {

    @Override
    public SyndFeed processor(String url) throws RssProcessorException {
        try {
            URL rssUrl = new URL(url);
            SyndFeedInput input = new SyndFeedInput();
            return input.build(new XmlReader(rssUrl));
        } catch (IOException | FeedException ex) {
            throw new RssProcessorException("rss error, url : "  + url);
        }
    }

}
