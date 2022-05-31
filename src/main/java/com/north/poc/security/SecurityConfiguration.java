package com.north.poc.security;

import com.north.poc.filter.JWTAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter();
        http
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/hello"
                                , "/authenticate"
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
                        .and()
                        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                        .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                )
//                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();
        return http.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://example.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTION"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
