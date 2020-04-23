package top.xcphoenix.groupblog.controller.api;

import org.springframework.web.bind.annotation.*;
import top.xcphoenix.groupblog.annotation.AdminAuth;
import top.xcphoenix.groupblog.model.Result;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.service.api.UserService;
import top.xcphoenix.groupblog.service.dispatch.ScheduleCrawlService;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/2/4 上午11:11
 */
@AdminAuth
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
    public Result<Void> execSchedule() {
        scheduleCrawlService.crawlIncr();
        return Result.success("定时任务开启...", null);
    }

}
