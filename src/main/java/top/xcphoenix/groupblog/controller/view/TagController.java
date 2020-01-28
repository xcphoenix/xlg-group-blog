package top.xcphoenix.groupblog.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/23 下午3:37
 * @version     1.0
 */
@Controller
@RequestMapping("/tag")
public class TagController {

    @GetMapping("/{tagId}")
    public String tag(Map<String, Object> map,
                      @PathVariable("tagId") int tagId,
                      @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                              int pageSize,
                      @RequestParam(value = "pageNum", required = false, defaultValue = "1")
                              int pageNum) {
        return null;
    }

}
