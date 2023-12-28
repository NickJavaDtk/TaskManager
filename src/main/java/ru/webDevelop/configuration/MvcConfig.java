package ru.webDevelop.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig  implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("hello");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/tasklist").setViewName("tasklist");
       // registry.addViewController("/appeal").setViewName("appeal");
        registry.addViewController("/login").setViewName("login");
    }
}
