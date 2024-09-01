package com.hdu.orange_recruitment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class OrangeRecruitment {

    public static void main(String[] args) {
        SpringApplication.run(OrangeRecruitment.class, args);
    }

}
