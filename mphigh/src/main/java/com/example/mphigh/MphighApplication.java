package com.example.mphigh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.example.mphigh.mapper")
public class MphighApplication {
    public static void main(String[] args) {
        SpringApplication.run(MphighApplication.class, args);
    }
}
