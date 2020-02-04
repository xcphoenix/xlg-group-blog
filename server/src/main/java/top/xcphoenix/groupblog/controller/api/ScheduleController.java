package top.xcphoenix.groupblog.controller.api;

import org.springframework.web.bind.annotation.*;
import top.xcphoenix.groupblog.model.Result;
import top.xcphoenix.groupblog.service.api.UserService;
import top.xcphoenix.groupblog.service.dispatch.ScheduleCrawlService;

/**
 * @author      xuanc
 * @date        2020/2/4 上午11:11
 * @version     1.0
 */
@RestController
@RequestMapping("/api")
public class ScheduleController {

    private ScheduleCrawlService scheduleCrawlService;
    private UserService userService;

    public ScheduleController(ScheduleCrawlService scheduleCrawlService, UserService userService) {
        this.scheduleCrawlService = scheduleCrawlService;
        this.userService = userService;
    }

    @GetMapping("/schedule")
    public Result execSchedule(@SessionAttribute("user") long uid) {
        if (uid == 10074L) {
            scheduleCrawlService.crawlIncr();
            return Result.success("定时任务开启...", null);
        }
        else {
            return Result.error(-1, "权限拒绝");
        }
    }

}
