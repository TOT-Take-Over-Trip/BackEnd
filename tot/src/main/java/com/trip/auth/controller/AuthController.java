package com.trip.auth.controller;

import com.trip.auth.model.dto.LoginUserDto;
import com.trip.auth.model.dto.SignUpUserDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public void login(@RequestBody LoginUserDto loginUserDto){

    }

    @PostMapping("/logout")
    public void logout(){

    }

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpUserDto signUpUserDto){

    }


    @GetMapping("/checkId")
    public void checkId(@RequestParam String id){

    }
}
