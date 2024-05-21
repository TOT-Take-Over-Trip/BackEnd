package com.trip.auth.service;

import com.trip.auth.model.dto.LoginUserDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<String> login(LoginUserDto loginUserDto);
}
