package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * 资源映射
 *
 * @author llin
 * @date 2022/1/12
 **/
@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

    private static List<String> EXCLUDE_PATH = Arrays.asList(
            "/toLogin", "/login", "/logout", "/static/**", "/webjars/**"
            , "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.ico"
            , "/static/**", "/webjars/**", "swagger-ui.html", "/swagger-resources/**"
    );


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-resources/**").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// 设置允许跨域的路由
                .allowedHeaders("*")
                .allowedOriginPatterns("*") // 设置允许跨域请求的域名
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")  // 设置允许的方法
                .maxAge(3600)   // 跨域允许时间
                .allowCredentials(true) // 是否允许证书（cookies）
        ;
    }
}
