package com.trip.member.service;

import com.trip.member.model.MemberDto;
import com.trip.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public List<MemberDto> getMembers() {
        return memberMapper.selectAllMembers();
    }

    @Override
    public MemberDto getMemberById(int memberId) {
        return memberMapper.selectMemberById(memberId);
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
