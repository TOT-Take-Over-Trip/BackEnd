package com.trip.auth.service;

import com.trip.auth.model.JwtProvider;
import com.trip.auth.model.dto.LoginUserDto;
import com.trip.member.model.MemberDto;
import com.trip.member.service.MemberService;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    protected final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Override
    public ResponseEntity<HashMap<String, Object>> login(LoginUserDto loginUserDto) {
        MemberDto member = memberService.getMemberByLoginId(loginUserDto.getId());
        try {
            validatePassword(loginUserDto.getPassword(),member.getPassword());
        } catch (Exception e) {
            e.printStackTrace();    //TODO: 예외처리
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("memberId", member.getMemberId());
        map.put("jwtToken", jwtProvider.createAccessToken(loginUserDto.getId()));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    private void validatePassword(String password, String encodedPassword) throws Exception {
        log.info("password: " + password);
        log.info("encodedPassword: " + encodedPassword);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new Exception(); //TODO: 예외처리
        }
    }
}
