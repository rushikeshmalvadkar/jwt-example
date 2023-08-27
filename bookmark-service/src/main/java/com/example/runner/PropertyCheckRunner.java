package com.example.runner;

import com.example.auth.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PropertyCheckRunner implements CommandLineRunner {

    private final JwtProperties jwtProperties;

    @Override
    public void run(String... args) throws Exception {
      log.info("JWT EXPIRY TIME IN MINUTES :: {} " , jwtProperties.jwtExpireTimeInMinute());
    }
}
