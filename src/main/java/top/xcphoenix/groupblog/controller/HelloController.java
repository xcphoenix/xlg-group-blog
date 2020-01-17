package top.xcphoenix.groupblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author      xuanc
 * @date        2020/1/15 下午4:46
 * @version     1.0
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

}
