package com.mohan.class_com.service;

import com.mohan.class_com.dto.AuthRequestDto;
import com.mohan.class_com.dto.AuthResponseDto;
import com.mohan.class_com.dto.ResponseDto;
import com.mohan.class_com.dto.UserRequestDto;
import com.mohan.class_com.entity.User;
import com.mohan.class_com.exception.InvalidCredentials;
import com.mohan.class_com.exception.ResourceAlreadyExistsEx;
import com.mohan.class_com.exception.ResourceNotFoundEx;
import com.mohan.class_com.repository.UserRepository;
import com.mohan.class_com.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ResponseDto registerUser(UserRequestDto requestDto){
        Optional<User> existingUser=userRepo.findByEmail(requestDto.getEmail());

        if(existingUser.isPresent()){
            throw new ResourceAlreadyExistsEx("User with email "+requestDto.getEmail()+" already exists");
        }

        User user=new User();
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole(requestDto.getRole().toUpperCase());

        User savedUser=userRepo.save(user);

        return new ResponseDto("User successfully create with id "+savedUser.getId());
    }

    public AuthResponseDto login(AuthRequestDto requestDto){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    requestDto.getEmail(),requestDto.getPassword()
            ));

        }catch (Exception ex){
            throw new InvalidCredentials("Give email or password is invalid");
        }

        User user=userRepo.findByEmail(requestDto.getEmail())
                .orElseThrow(()-> new ResourceNotFoundEx("User not found"));


        return new AuthResponseDto(jwtUtil.generateToken(user.getEmail()), user.getRole());
    }

}
