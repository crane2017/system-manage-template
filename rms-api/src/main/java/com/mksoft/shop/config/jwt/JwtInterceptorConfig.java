package com.mksoft.shop.config.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by oneway on 2017/4/29.
 */
//@Configuration
public class JwtInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void addInterceptors(InterceptorRegistry registry) {
        logger.debug("add JwtInterceptor");
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/swagger*/**", "/v2/api-docs", "api/error")
//                .excludePathPatterns("/app/picture/**")
//                .excludePathPatterns("/app/hp/news/**")
//                .excludePathPatterns("/app/hp/video/**")
//                .excludePathPatterns("/app/event/match/**")
//                .excludePathPatterns("/app/team/*")
//                .excludePathPatterns("/login/**")
//                .excludePathPatterns("/error/**");
    }
}