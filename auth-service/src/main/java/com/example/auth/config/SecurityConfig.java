package com.example.auth.config;


import com.example.auth.filter.AuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthFilter authFilter;


    //  "/webjars/**", -> add if needed

    public static final String[] PUBLIC_URLS = {
            "/api/users/**",
            "/api/auth/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/claims/test/**",
            "/favicon.ico"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                /**
                 * Need to disable csrf otherwise we will get 403 or 401
                 * on public POST types urls
                 */
                .authorizeHttpRequests()
                .requestMatchers(PUBLIC_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .build();
    }

}
