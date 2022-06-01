package com.north.poc.service;

import com.north.poc.dto.AuthenticationRequest;
import com.north.poc.exception.NotFoundException;
import com.north.poc.model.User;
import com.north.poc.repository.UserRepository;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    AuthenticationService authenticationService;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtService jwtService;


    @Test
    void authenticate_success() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setPassword("password");
        request.setUsername("abc");

        User user = new User();
        user.setId(1L);
        user.setUsername("abc");
        user.setPassword("password");

        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(jwtService.signingByJWT(Mockito.any(),Mockito.anyLong(),Mockito.any(),Mockito.any(),Mockito.anyLong())).thenReturn("token");

        String jwt = authenticationService.authenticate(request);

        Assertions.assertThat(jwt).isNotEmpty();
    }

    @Test
    void authenticate_not_found_user() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setPassword("password");
        request.setUsername("abc");

        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

//        NotFoundException notFoundException = org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> authenticationService.authenticate(request));
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> authenticationService.authenticate(request))
                .withMessage("not found user");
    }
}