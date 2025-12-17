package com.schedule.teacher.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI teacherOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("教师排课子系统 API")
                        .version("1.0.0")
                        .description("Teacher Scheduling Subsystem"));
    }
}
