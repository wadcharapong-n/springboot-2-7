package com.north.poc.controller;

import com.north.poc.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseMvcTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    JwtService jwtService;
    public String getToken(){
        return jwtService.signingByJWT("test",1L, "userTest", List.of("ROLE_USER"), 5L);
    }
}
