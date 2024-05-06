package com.trip.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
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

    @Builder
    public MemberDto(int memberId, String name, String id, String password, String email,
        String phoneNumber, Long point, String profileImage) {
        this.memberId = memberId;
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.point = point;
        this.profileImage = profileImage;
    }
}
