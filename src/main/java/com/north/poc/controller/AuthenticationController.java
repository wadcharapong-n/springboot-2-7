package com.north.poc.controller;

import com.north.poc.dto.AuthenticationRequest;
import com.north.poc.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthenticationController {

    final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(HttpServletRequest httpServletRequest, @RequestBody @Valid AuthenticationRequest request, BindingResult result) {
        log.info("Requesting authenticate, IP Address : {} {} {} {}", httpServletRequest.getRemoteUser(), httpServletRequest.getRemoteAddr(), httpServletRequest.getRemoteHost(), httpServletRequest.getRemotePort());
        if (result.hasErrors()) {
            log.warn("Error authenticate");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String jwt = authenticationService.authenticate(request);

        return ResponseEntity.ok(jwt);
    }
}
