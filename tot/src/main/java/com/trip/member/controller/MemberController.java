package com.trip.member.controller;

import com.trip.member.model.MemberDto;
import com.trip.member.service.MemberService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@CrossOrigin("*")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    protected final PasswordEncoder passwordEncoder;

    // TODO: 이거 admin 인지 확인해야 될 거 같은데???
    // TODO: parameter 로 memberId 넘겨줘야할 거 같은데
    @GetMapping
    public ResponseEntity<?> getMembers() throws IOException {
        return ResponseEntity.ok(memberService.getMembers());
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable("memberId") int memberId) throws IOException {
        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }

    @PutMapping(value = "/{memberId}", consumes = {"multipart/form-data"})
    public void updateMember(@PathVariable("memberId") int memberId,
                             @RequestPart(value = "memberDto") MemberDto memberDto,
                             @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) throws IOException {
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encodedPassword);
        log.info("profileImage: {}", profileImage);
        memberService.updateMember(memberId, memberDto, profileImage);
    }

    @PatchMapping("/{memberId}")
    public void deleteMember(@PathVariable("memberId") int memberId) {
        memberService.deleteMember(memberId);
    }

    @PostMapping(value = "/signup", consumes = {"multipart/form-data"})
    public void signUpMember(@RequestPart("memberDto") MemberDto memberDto, @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        try {
            log.info("signupMember: {}", memberDto);
            memberService.createMember(memberDto, profileImage);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
