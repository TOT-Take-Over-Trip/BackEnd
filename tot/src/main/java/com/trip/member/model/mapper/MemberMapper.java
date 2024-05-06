package com.trip.member.model.mapper;

import com.trip.member.model.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    List<MemberDto> selectAllMembers();

    MemberDto selectMemberById(int memberId);

    MemberDto selectMemberByLoginId(String id);

    void insertMember(MemberDto memberDto);

    void updateMember(MemberDto memberDto);

    void deleteMemberById(int memberId);

}
