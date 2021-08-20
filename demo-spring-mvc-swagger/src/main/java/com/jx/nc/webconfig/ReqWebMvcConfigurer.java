package com.jx.nc.webconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ReqWebMvcConfigurer implements WebMvcConfigurer {

    /*** 跨域支持*/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("token").maxAge(3600); ;
        //浏览器允许所有的域访问 / 注意 * 不能满足带有cookie的访问,Origin 必须是全匹配
        // 允许带cookie访问
    }
}
