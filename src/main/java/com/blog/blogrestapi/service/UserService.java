package com.blog.blogrestapi.service;

import com.blog.blogrestapi.dto.LoginDto;
import com.blog.blogrestapi.dto.UserDto;
import com.blog.blogrestapi.entity.User;

public interface UserService {
    UserDto registerUser(UserDto userDto);
}
