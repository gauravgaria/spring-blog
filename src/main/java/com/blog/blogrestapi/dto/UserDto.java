package com.blog.blogrestapi.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {
    private String name;
    @NotEmpty
    @Email
    private String email;
    private String password;
    private String username;
}
