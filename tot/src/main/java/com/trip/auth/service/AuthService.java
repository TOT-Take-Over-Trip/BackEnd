package com.trip.auth.service;

import com.trip.auth.model.dto.LoginUserDto;

public interface AuthService {

    String login(LoginUserDto loginUserDto);
}
