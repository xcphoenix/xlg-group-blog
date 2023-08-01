package top.xcphoenix.groupblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author xuanc and ...
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("top.xcphoenix.groupblog.mybatis.mapper")
public class GroupblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupblogApplication.class, args);
        System.out.println("version: 1.0");
    }

}
