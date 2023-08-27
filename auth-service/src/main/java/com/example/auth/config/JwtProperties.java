package com.example.auth.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "jwt")
@Slf4j
public record JwtProperties(
        @DefaultValue(value = "5")
        int jwtExpireTimeInMinute,
        String jwtSecretKey) {

    @PostConstruct
    public void init() {
        if (log.isDebugEnabled()) {
            log.debug("{} bean created", this.getClass().getName());
        }
    }
}
