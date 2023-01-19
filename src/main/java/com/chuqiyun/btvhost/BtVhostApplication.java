package com.chuqiyun.btvhost;

import com.chuqiyun.btvhost.utils.EncryptUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author lvyunqi
 */
@SpringBootApplication
@MapperScan("com.chuqiyun.btvhost.dao")
@ComponentScan("com.chuqiyun.*")
@EnableAsync
public class BtVhostApplication {
    private static ConfigurableApplicationContext context;

    /*public static void main(String[] args) {
        SpringApplication.run(ChuqiCloudBtVhostApplication.class, args);
    }*/

    public static void main(String[] args) {
        context = SpringApplication.run(BtVhostApplication.class, args);
    }

    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(BtVhostApplication.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }


}
