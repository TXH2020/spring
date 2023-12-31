package com.example.demo1.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class DemoInterceptorConfig implements WebMvcConfigurer {
	   @Autowired
	   DemoInterceptor demoInterceptor;

	   public void addInterceptors(InterceptorRegistry registry) {
	      registry.addInterceptor(demoInterceptor);
	   }
	}
