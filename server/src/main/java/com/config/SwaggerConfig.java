package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).
                apiInfo(apiInfo()).
                select().
                apis(RequestHandlerSelectors.basePackage("com.controller"))
                .build();
    }
    private ApiInfo apiInfo(){
        return new ApiInfo("模拟投诉表",
                "乐游后台投诉","1.0",
                null,null,"apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",new ArrayList<>());
    }
}
