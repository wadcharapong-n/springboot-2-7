package com.north.poc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/hello"
                                , "/v2/api-docs"
                                , "/configuration/ui"
                                , "/swagger-resources/**"
                                , "/configuration/security"
                                , "/v3/api-docs/**"
                                , "/swagger-ui.html"
                                , "/swagger-ui/**"
                                , "/webjars/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();
        return http.build();
    }
}
