package com.north.poc.service;

import com.north.poc.dto.UserDto;
import com.north.poc.exception.NotFoundException;
import com.north.poc.model.User;
import com.north.poc.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    final UserRepository userRepository;

    public UserDto getUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("not found user"));
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }
}