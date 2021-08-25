package com.jx.nc.webconfig;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    @Bean
    public Docket api() {
        //添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("令牌")//Token 以及Authorization 为自定义的参数，session保存的名字是哪个就可以写成那个
        .modelRef(new ModelRef("string")).parameterType("header").required(false).build();//header中的ticket参数非必填，传空也可以
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jx.nc.controller"))  //显示所有类
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))  //只显示添加@Api注解的类
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(pars)
                .pathMapping("/");
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
