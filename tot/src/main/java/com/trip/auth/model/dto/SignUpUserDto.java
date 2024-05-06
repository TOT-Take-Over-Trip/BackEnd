package com.trip.auth.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpUserDto {
    private String id;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
}
