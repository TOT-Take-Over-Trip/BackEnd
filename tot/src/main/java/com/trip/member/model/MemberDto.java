package com.trip.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MemberDto {

    private int memberId;
    private String name;
    private String id;
    private String password;
    private String email;
    private String phoneNumber;
    private Long point;
    private String profileImage;
    private String createdDate;
    private String updatedDate;
    private String status;

}
