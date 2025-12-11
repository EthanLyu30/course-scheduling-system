package com.schedule.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 学生排课子系统启动类
 */
@SpringBootApplication
public class StudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
        System.out.println("=========================================");
        System.out.println("  学生排课子系统启动成功！");
        System.out.println("  接口文档: http://localhost:8081/doc.html");
        System.out.println("=========================================");
    }

}
