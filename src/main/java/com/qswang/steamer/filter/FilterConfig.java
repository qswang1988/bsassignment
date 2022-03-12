package com.qswang.steamer.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registerMyFilterForLogin(){
        FilterRegistrationBean<RequestValidationFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestValidationFilter());
        bean.addUrlPatterns("/gaming/*");
        return bean;
    }
}
