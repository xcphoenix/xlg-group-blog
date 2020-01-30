package top.xcphoenix.groupblog.processor.impl;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.xcphoenix.groupblog.expection.processor.FeedProcessorException;
import top.xcphoenix.groupblog.processor.Processor;

import java.io.IOException;
import java.net.URL;

/**
 * @author      xuanc
 * @date        2020/1/12 上午12:17
 * @version     1.0
 */
@Slf4j
@Component("feed")
public class FeedProcessorImpl implements Processor {

    @Override
    public WireFeed processor(String url) throws FeedProcessorException {
        try {
            URL rssUrl = new URL(url);
            WireFeedInput input = new WireFeedInput();
            return input.build(new XmlReader(rssUrl));
        } catch (IOException | FeedException ex) {
            throw new FeedProcessorException("rss error, url : "  + url);
        }
    }

}
