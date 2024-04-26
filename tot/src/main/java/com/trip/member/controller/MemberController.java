package com.trip.member.controller;

import com.trip.member.model.MemberDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    // TODO: 구현해야 함
    @GetMapping
    public List<MemberDto> getMembers() {
        return new ArrayList<>();
    }

    // TODO: 구현해야 함
    @GetMapping("/{memberId}")
    public MemberDto getMember(@PathVariable("memberId") Long memberId) {
        return new MemberDto();
    }

    // TODO: 구현해야 함
    @PutMapping("/{memberId}")
    public MemberDto updateMember(@PathVariable("memberId") Long memberId, @RequestBody MemberDto memberDto) {
        return new MemberDto();
    }

    // TODO: 구현해야 함
    @PatchMapping("/{memberId}")
    public void deleteMember(@PathVariable("memberId") Long memberId) {

    }
}
