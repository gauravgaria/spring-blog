package com.blog.blogrestapi.service.impl;

import com.blog.blogrestapi.dto.LoginDto;
import com.blog.blogrestapi.dto.UserDto;
import com.blog.blogrestapi.entity.User;
import com.blog.blogrestapi.repository.UserRepository;
import com.blog.blogrestapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto registerUser(UserDto userDto) {
        // check if user already registered
        Optional<User> userExists = userRepository.findByUsernameOrEmail(userDto.getUsername(),userDto.getEmail());
        if(!userExists.isEmpty()){
            return null;
        }
        User user = userRepository.save(mapToEntity(userDto));

        return maptoDto(user);
    }

    private User mapToEntity(UserDto userDto){
        return modelMapper.map(userDto,User.class);
    }

    private UserDto maptoDto(User user){
        return modelMapper.map(user,UserDto.class);
    }
}
