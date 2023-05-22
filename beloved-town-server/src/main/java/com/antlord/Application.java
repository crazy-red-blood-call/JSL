package com.antlord;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.antlord")
@Slf4j
@MapperScan(basePackages = "com.antlord.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.warn("================美甲试戴启动成功===============");
    }


}