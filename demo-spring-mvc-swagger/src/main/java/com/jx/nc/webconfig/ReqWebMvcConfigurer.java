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
                .allowedOrigins("*")//允许跨域访问的域名
                .allowCredentials(true) //响应报头指示的请求的响应是否可以暴露于该页面。当true值返回时它可以被暴露。 凭证是 Cookie ，授权标头或 TLS 客户端证书。可以通过nginx反向代理配置请求，不需要暴露请求响应内容
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("token")
                .maxAge(3600);//第一次的预检请求获知服务器是否允许该跨域请求：如果允许，才发起第二次真实的请求；如果不允许，则拦截第二次请求。单位为秒,隔60分钟异步请求发起预检请求，也就是说，发送两次请求
        ;
        //浏览器允许所有的域访问 / 注意 * 不能满足带有cookie的访问,Origin 必须是全匹配
        // 允许带cookie访问
    }
}
