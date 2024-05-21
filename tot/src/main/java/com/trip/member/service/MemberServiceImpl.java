package com.trip.member.service;

import com.trip.member.model.MemberDto;
import com.trip.member.model.mapper.MemberMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public List<MemberDto> getMembers() throws IOException {
        List<MemberDto> members = memberMapper.selectAllMembers();
        for(MemberDto member : members){
            String filePath = member.getProfileImage();
            byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //실제 파일 불러오기
            String base64EncodedString = Base64.getEncoder().encodeToString(bytes); //인코딩
            member.setProfileImage(base64EncodedString); //인코딩 정보 넣어주기
        }
        return members;
    }

    @Override
    public MemberDto getMemberById(int memberId) throws IOException {
        MemberDto member = memberMapper.selectMemberById(memberId);
        String filePath = member.getProfileImage();
        if (filePath != null) {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //실제 파일 불러오기
            String base64EncodedString = Base64.getEncoder().encodeToString(bytes); //인코딩
            member.setProfileImage(base64EncodedString); //인코딩 정보 넣어주기
        }
        return member;
    }

    @Override
    public MemberDto getMemberByLoginId(String id) {
        return memberMapper.selectMemberByLoginId(id);
    }

    @Override
    public void createMember(MemberDto memberDto) {
        memberMapper.insertMember(memberDto);
    }

    @Override
    public void updateMember(int memberId, MemberDto memberDto) {
        memberMapper.updateMember(memberDto);
    }

    @Override
    public void deleteMember(int memberId) {
        memberMapper.deleteMemberById(memberId);
    }
}
