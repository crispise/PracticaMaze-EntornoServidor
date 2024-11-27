package com.esliceu.maze;

import com.esliceu.maze.filters.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MazeApplication implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(MazeApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                loginInterceptor
        ).addPathPatterns("/start", "/nav", "/getcoin", "/getkey", "/reset");
    }
}
