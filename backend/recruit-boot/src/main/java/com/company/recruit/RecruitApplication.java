package com.company.recruit;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "com.company.recruit")
@ConfigurationPropertiesScan(basePackages = "com.company.recruit")
@MapperScan(basePackages = "com.company.recruit", annotationClass = Mapper.class)
public class RecruitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitApplication.class, args);
    }
}
