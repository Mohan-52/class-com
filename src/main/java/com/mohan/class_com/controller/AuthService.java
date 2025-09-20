package com.mohan.class_com.controller;

import com.mohan.class_com.dto.AuthRequestDto;
import com.mohan.class_com.dto.AuthResponseDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.dto.UserRequestDto;
import com.mohan.class_com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody UserRequestDto requestDto){
        return new ResponseEntity<>(userService.registerUser(requestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto requestDto){
        return ResponseEntity.ok(userService.login(requestDto));

    }

}
