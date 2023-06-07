package com.trianaSalesianos.tofuApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200", "http://localhost:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "DELETE", "HEAD")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "content-type")
                        .maxAge(3600);
            }
        };
    }
}
