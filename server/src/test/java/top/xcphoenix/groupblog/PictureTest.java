package top.xcphoenix.groupblog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.service.picture.CrawPictureService;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@SpringBootTest
public class PictureTest {
    
    @Autowired
    CrawPictureService crawPictureService;
    @Test
    void testPicture() throws IOException {
        String url = "https://blog.csdn.net/weixin_74474546/article/details/131094929";
        Document document = Jsoup.connect(url).get();
        Element article = document.selectFirst("article");
        String s = crawPictureService.downPicture(article,url);
        System.out.println(s);
    }
}
