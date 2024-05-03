package com.trip.member.controller;

import com.trip.member.model.MemberDto;
import com.trip.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // TODO: 이거 admin 인지 확인해야 될 거 같은데???
    // TODO: parameter 로 memberId 넘겨줘야할 거 같은데
    @GetMapping
    public ResponseEntity<?> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable("memberId") int memberId) {
        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }

    @PutMapping("/{memberId}")
    public void updateMember(@PathVariable("memberId") int memberId, @RequestBody MemberDto memberDto) {
        memberService.updateMember(memberId, memberDto);
    }

    @PatchMapping("/{memberId}")
    public void deleteMember(@PathVariable("memberId") int memberId) {
        memberService.deleteMember(memberId);
    }
}
