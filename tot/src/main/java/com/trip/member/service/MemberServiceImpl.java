package com.trip.member.service;

import com.trip.member.model.MemberDto;
import com.trip.member.model.mapper.MemberMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

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
            if (filePath != null) {
                byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //실제 파일 불러오기
                String base64EncodedString = Base64.getEncoder().encodeToString(bytes); //인코딩
                member.setProfileImage(base64EncodedString); //인코딩 정보 넣어주기
            }
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
    public void createMember(MemberDto memberDto, MultipartFile profileImage) throws IOException {
        String fullPath = "";
        if(profileImage!=null){
            //확장자 추출
            int pos = profileImage.getOriginalFilename().lastIndexOf(".");
            String ext = profileImage.getOriginalFilename().substring(pos+1);

            String uuid = UUID.randomUUID().toString();
            fullPath = fileDir + uuid + "." + ext;
            profileImage.transferTo(new File(fullPath));
            memberDto.setProfileImage(fullPath);
        }
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

    @Override
    public void updatePoint(HashMap<String, Integer> map) {
        memberMapper.updatePoint(map);
    }
}
