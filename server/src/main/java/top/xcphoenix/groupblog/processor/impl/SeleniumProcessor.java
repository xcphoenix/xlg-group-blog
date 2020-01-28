package top.xcphoenix.groupblog.processor.impl;

import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.ErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import top.xcphoenix.groupblog.expection.processor.SeleniumProcessorException;
import top.xcphoenix.groupblog.processor.Processor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;

/**
 * @author xuanc
 * @version 1.0
 * @date 2019/12/8 下午7:09
 */
@Slf4j
@Component("selenium")
@PropertySource("file:${config-dir}/processor.properties")
public class SeleniumProcessor implements Processor {

    private WebDriver driver;
    @Value("${processor.selenium.chrome-driver.location:./chromedriver}")
    private String driverLocation;

    @Override
    public String processor(String url) {
        long startTime = System.currentTimeMillis();
        log.info("processor[" + this.getClass() + "] get url page content: " + url);

        driver.get(url);
        String webContent =  driver.getPageSource();

        log.info("processor[" + this.getClass() + "] get web content finished, time used " + (System.currentTimeMillis() - startTime));
        return webContent;
    }

    @PostConstruct
    private void init() throws SeleniumProcessorException {
        log.info("processor[" + this.getClass() + "] init");

        File driverFile;
        /*
         * Jar包无法获取文件
         */
        driverFile = new File(driverLocation);

        if (!driverFile.exists()) {
            throw new SeleniumProcessorException("driver not found, file: " + driverLocation);
        }
        System.setProperty("webdriver.chrome.driver", driverFile.getAbsolutePath());

        // https://github.com/lightbody/browsermob-proxy#using-with-selenium
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.blacklistRequests("https://.*/.*.css.*", 200);
        proxy.blacklistRequests("https://.*/.*.js.*", 200);
        proxy.blacklistRequests("https://.*/.*.ico.*", 200);
        proxy.blacklistRequests("https://adaccount.csdn.net/", 200);
        proxy.blacklistRequests("http://www/.google/.*.css.*", 200);
        proxy.start();
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("blink-settings=imagesEnabled=false");
        chromeOptions.setCapability(CapabilityType.PROXY, seleniumProxy);

        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }

    @PreDestroy
    private void destroy() {
        log.info("processor[" + this.getClass() + "] destroy");
        driver.quit();
    }

}