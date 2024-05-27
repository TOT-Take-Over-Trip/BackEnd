package com.trip.auth.service;

import com.trip.auth.model.dto.LoginUserDto;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<HashMap<String, Object>> login(LoginUserDto loginUserDto);
}
