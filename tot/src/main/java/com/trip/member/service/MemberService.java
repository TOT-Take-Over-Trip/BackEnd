package com.trip.member.service;

import com.trip.member.model.MemberDto;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    List<MemberDto> getMembers() throws IOException;

    MemberDto getMemberById(int memberId) throws IOException;

    MemberDto getMemberByLoginId(String id);

    void createMember(MemberDto memberDto, MultipartFile profileImage) throws IOException;

    void updateMember(int memberId, MemberDto memberDto, MultipartFile profileImage) throws IOException;

    void deleteMember(int memberId);

    void updatePoint(HashMap<String, Integer> map);

}
