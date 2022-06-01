package com.north.poc.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    @InjectMocks
    JwtService jwtService;



    @Test
    void signingByJWT() {
        List<String> roles = Arrays.asList("A", "B");
        String jwt = jwtService.signingByJWT("test", 1, "username", roles, 10);
        Assertions.assertThat(jwt).isNotEmpty();
    }
}