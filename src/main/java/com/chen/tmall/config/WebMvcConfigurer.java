package com.chen.tmall.config;

import com.chen.tmall.interceptor.LoginInterceptor;
import com.chen.tmall.interceptor.OtherInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Bean
    public LoginInterceptor getLoginIntercepter() {
        return new LoginInterceptor();
    }


    @Bean
    public OtherInterceptor getOtherIntercepter() {
        return new OtherInterceptor();
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLoginIntercepter())
                .addPathPatterns("/**");
        registry.addInterceptor(getOtherIntercepter())
                .addPathPatterns("/**").excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg");
    }
}
