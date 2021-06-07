package com.blockchain.server.base.conf;

import com.blockchain.server.base.security.AccessPermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 接口拦截器
 * @author huangxl
 * @create 2019-02-21 21:03
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Bean
    public AccessPermissionInterceptor tokenInterceptor() {
        return new AccessPermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/error");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }
}
