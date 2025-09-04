package com.mohan.class_com.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String email;
    private String password;
    private String role;
}
