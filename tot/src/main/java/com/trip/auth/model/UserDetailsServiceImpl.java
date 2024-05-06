package com.trip.auth.model;

import com.trip.member.model.MemberDto;
import com.trip.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberService memberService;


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
//        MemberDto memberDto = memberService.getMemberById(Integer.parseInt(id));
        MemberDto memberDto = memberService.getMemberByLoginId(id);
        return new UserDetailsImpl(memberDto);  //Todo: 고치기
    }

}