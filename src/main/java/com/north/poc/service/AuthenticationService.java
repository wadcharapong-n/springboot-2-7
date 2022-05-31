package com.north.poc.service;

import com.north.poc.dto.AuthenticationRequest;
import com.north.poc.exception.InvalidCredentialException;
import com.north.poc.exception.NotFoundException;
import com.north.poc.model.User;
import com.north.poc.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    final JwtService jwtService;
    final UserRepository userRepository;

    public String authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new NotFoundException("not found user"));
        if(!user.getPassword().equals(request.getPassword())){
            throw new InvalidCredentialException("user : " + user.getUsername() + " wrong password");
        }
        return jwtService.signingByJWT("test", user.getId(), user.getUsername(), null, 60);
    }
}
