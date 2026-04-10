package com.company.recruit.common.web.config;

import com.company.recruit.common.web.interceptor.DuplicateSubmitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Registers common MVC interceptors shared by backend modules.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final DuplicateSubmitInterceptor duplicateSubmitInterceptor;

    public WebMvcConfig(DuplicateSubmitInterceptor duplicateSubmitInterceptor) {
        this.duplicateSubmitInterceptor = duplicateSubmitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(duplicateSubmitInterceptor).addPathPatterns("/api/**");
    }
}