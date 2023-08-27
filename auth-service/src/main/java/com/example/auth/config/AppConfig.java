package com.example.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(value = {JwtProperties.class})
@Configuration
public class AppConfig {
}
