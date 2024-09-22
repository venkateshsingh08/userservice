package com.example.userservice.controllers;


import com.example.userservice.dtos.LoginRequestDto;
import com.example.userservice.dtos.LoginResponseDto;
import com.example.userservice.dtos.SignupRequestDto;
import com.example.userservice.dtos.SignupResponseDto;
import com.example.userservice.dtos.UserDto;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    public SignupResponseDto signup(SignupRequestDto signupRequestDto){

        return null;
    }

    public LoginResponseDto login(LoginRequestDto requestDto){
        return null;
    }

    public UserDto validate(@RequestHeader("Authorization") String token){
        return null;
    }

    public void logout(){

    }
}
