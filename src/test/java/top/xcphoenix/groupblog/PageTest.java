package top.xcphoenix.groupblog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.xcphoenix.groupblog.model.vo.Pagination;

import java.awt.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author      xuanc
 * @date        2020/1/21 上午10:54
 * @version     1.0
 */
@SpringBootTest
public class PageTest {

    @Test
    void testPageShow() {
        Pagination pagination = new Pagination(4, 3, "/test");
        System.out.println(pagination.getPageLinks().stream().map(i -> i.getNum()).collect(Collectors.toList()));
        pagination = new Pagination(5, 2, "/test");
        System.out.println(pagination.getPageLinks().stream().map(i -> i.getNum()).collect(Collectors.toList()));
        pagination = new Pagination(7, 3, "/test");
        System.out.println(pagination.getPageLinks().stream().map(i -> i.getNum()).collect(Collectors.toList()));
        pagination = new Pagination(7, 4, "/test");
        System.out.println(pagination.getPageLinks().stream().map(i -> i.getNum()).collect(Collectors.toList()));
        pagination = new Pagination(7, 6, "/test");
        System.out.println(pagination.getPageLinks().stream().map(i -> i.getNum()).collect(Collectors.toList()));
    }

}
