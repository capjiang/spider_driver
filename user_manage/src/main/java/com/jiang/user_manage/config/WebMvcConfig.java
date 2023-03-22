package com.jiang.user_manage.config;

import com.jiang.user_manage.config.resolver.CurrentUserMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserMethodArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 项目中的所有接口都支持跨域
                .allowedOrigins("*")    // //所有地址都可以访问，也可以配置具体地址
                .allowCredentials(true)
                .allowedMethods("*")    // "GET","POST","PUT","DELETE"
                .allowedHeaders("*");
    }
}
