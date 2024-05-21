package com.trip.auth.controller;

import com.trip.auth.model.dto.LoginUserDto;
import com.trip.auth.model.dto.SignUpUserDto;
import com.trip.auth.service.AuthService;
import com.trip.member.model.MemberDto;
import com.trip.member.service.MemberService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class AuthController {

    private final MemberService memberService;
    private final AuthService authService;
    protected final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, Object>> login(@RequestBody LoginUserDto loginUserDto){
        log.info("Member login request: {}", loginUserDto.getId());
        MemberDto member = memberService.getMemberByLoginId(loginUserDto.getId()); //TODO: 예외처리
        return authService.login(loginUserDto);
    }

    @PostMapping("/logout")
    public void logout(){

    }

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpUserDto signUpUserDto){
        String encodedPassword = passwordEncoder.encode(signUpUserDto.getPassword());
        MemberDto member = MemberDto.builder()
            .id(signUpUserDto.getId())
            .password(encodedPassword)
            .name(signUpUserDto.getName())
            .email(signUpUserDto.getEmail())
            .phoneNumber(signUpUserDto.getPhoneNumber())
            .build();
        memberService.createMember(member);
    }


    @GetMapping("/checkId")
    public void checkId(@RequestParam String id){

    }
}
