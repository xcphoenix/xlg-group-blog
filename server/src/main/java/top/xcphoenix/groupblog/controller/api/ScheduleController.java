package top.xcphoenix.groupblog.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xcphoenix.groupblog.annotation.AdminAuth;
import top.xcphoenix.groupblog.annotation.UserAuth;
import top.xcphoenix.groupblog.model.Result;
import top.xcphoenix.groupblog.service.api.UserService;
import top.xcphoenix.groupblog.service.dispatch.ScheduleCrawlService;


@RestController
@RequestMapping("/api")
public class ScheduleController {

    private ScheduleCrawlService scheduleCrawlService;
    private UserService userService;

    public ScheduleController(ScheduleCrawlService scheduleCrawlService, UserService userService) {
        this.scheduleCrawlService = scheduleCrawlService;
        this.userService = userService;
    }

    @AdminAuth
    @GetMapping("/schedule")
    public Result<Void> execSchedule() {
        scheduleCrawlService.crawlIncr();
        return Result.success("定时任务开启...", null);
    }

    @UserAuth
    @GetMapping("/execByUid/{uid}")
    public Result<Void> execByUid(@PathVariable("uid") Long uid) {
        String success = scheduleCrawlService.crawlIncr(uid);
        if(success != null){
            return Result.error(-1,success);
        }
        return Result.success("定时任务开启...",null);
    }


}
