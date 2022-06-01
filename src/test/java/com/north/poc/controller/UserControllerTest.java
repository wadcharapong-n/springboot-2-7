package com.north.poc.controller;

import com.north.poc.dto.UserDto;
import com.north.poc.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseMvcTest{
    @MockBean
    UserService userService;

    @Test
    void getUserByToken() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("userTest");
        Mockito.when(userService.getUserById(Mockito.anyLong())).thenReturn(userDto);
        String token = getToken();
        this.mockMvc.perform(get("/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("userTest"));
    }
}