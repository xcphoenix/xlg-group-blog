package top.xcphoenix.groupblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xuanc
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("top.xcphoenix.groupblog.mapper")
public class GroupblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupblogApplication.class, args);
    }

}
