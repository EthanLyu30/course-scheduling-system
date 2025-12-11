package com.schedule.student.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j (Swagger) 接口文档配置
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("学生排课子系统 API")
                        .version("1.0.0")
                        .description("双向选择排课系统 - 学生端接口文档")
                        .contact(new Contact()
                                .name("吕霄阳")
                                .email("lvxy@stu.suda.edu.cn")));
    }
}
