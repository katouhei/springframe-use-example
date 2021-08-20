package com.jx.nc.webconfig;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jx.nc.controller"))  //显示所有类
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))  //只显示添加@Api注解的类
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("springmvc+swagger整合")  //粗标题
                .description("测试springmvc+swagger开放接口")   //描述
                .version("1.0.0")   //api version
                .termsOfServiceUrl("http://www.jx.com")
                .license("LICENSE")   //链接名称
                .licenseUrl("http://www.jx.com")   //链接地址
                .build();
    }

}
