package top.xcphoenix.groupblog.service.dispatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.model.dto.UserSummary;
import top.xcphoenix.groupblog.service.crawl.CrawlBlogService;
import top.xcphoenix.groupblog.utils.BeanUtil;

import java.util.List;

/**
 * @author      xuanc
 * @date        2020/1/19 下午1:27
 * @version     1.0
 */
@Service
@Slf4j
public class ScheduleCrawlService {

    private UserManager userManager;

    public ScheduleCrawlService(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * 定时增量抓取
     */
    @Scheduled(cron = "0 8 9,23 * * *")
    public void crawlIncr(){
        log.info("exec cron task...");

        List<UserSummary> summaryList = userManager.getUsersSummary();
        CrawlBlogService crawlBlogService;

        for (UserSummary summary : summaryList) {
            log.info("uid: " + summary.getUid() + ", exec Bean [" + summary.getBeanName() + "]");
            if(!summary.getBeanName().startsWith("crawl")){
                continue;
            }

            try {
                crawlBlogService = BeanUtil.getBean(summary.getBeanName(), CrawlBlogService.class);
            } catch (BeansException be) {
                log.warn("found bean error, bean: " + summary.getBeanName());
                continue;
            }

            log.info("exec crawl task...");
            crawlBlogService.crawlIncrement(summary.getUid());
        }
        log.info("crawl finished...");
    }

    public String crawlIncr(long uid){
        log.info("craw uid's blog...");

        UserSummary summary = userManager.getUserSummaryByUid(uid);
        CrawlBlogService crawlBlogService;
        if(!summary.getBeanName().startsWith("crawl")){
            return "该类型不支持爬取";
        }

        log.info("uid: " + summary.getUid() + ", exec Bean [" + summary.getBeanName() + "]");

        try {
            crawlBlogService = BeanUtil.getBean(summary.getBeanName(), CrawlBlogService.class);
            log.info("exec crawl task...");
            crawlBlogService.crawlIncrement(summary.getUid());
        } catch (BeansException be) {
            log.warn("found bean error, bean: " + summary.getBeanName());
        }
        log.info("crawl finished...");
        return null;
    }
}
