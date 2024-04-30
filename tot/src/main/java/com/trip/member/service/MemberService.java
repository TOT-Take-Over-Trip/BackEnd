package com.trip.member.service;

import com.trip.member.model.MemberDto;

import java.util.List;

public interface MemberService {

    List<MemberDto> getMembers();

    MemberDto getMemberById(int memberId);

    void updateMember(int memberId, MemberDto memberDto);

    void deleteMember(int memberId);

}
