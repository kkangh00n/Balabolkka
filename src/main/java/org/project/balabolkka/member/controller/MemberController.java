package org.project.balabolkka.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.balabolkka.member.dto.MemberDataResponseDto;
import org.project.balabolkka.member.dto.MemberResponseDto;
import org.project.balabolkka.member.dto.MemberSaveRequestDto;
import org.project.balabolkka.member.dto.MemberUpdateRequestDto;
import org.project.balabolkka.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 REST API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 가입")
    @PostMapping
    public ResponseEntity<MemberDataResponseDto> join(@Validated @RequestBody MemberSaveRequestDto memberSaveRequestDto){
        MemberDataResponseDto joinMember = memberService.join(memberSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(joinMember);
    }

    @Operation(summary = "회원 전체 조회")
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAll(){
        List<MemberResponseDto> allMember = memberService.getAll();
        return ResponseEntity.ok(allMember);
    }

    @Operation(summary = "회원 단일 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDataResponseDto> getOne(@PathVariable("memberId") Long memberId){
        MemberDataResponseDto member = memberService.getOne(memberId);
        return ResponseEntity.ok(member);
    }

    @Operation(summary = "회원 정보 수정")
    @PatchMapping("/{memberId}")
    public ResponseEntity<Boolean> update(@PathVariable("memberId") Long memberId, @Validated @RequestBody MemberUpdateRequestDto memberUpdateRequestDto){
        memberService.update(memberId, memberUpdateRequestDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(true);
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Boolean> delete(@PathVariable("memberId") Long memberId){
        memberService.delete(memberId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(true);
    }

}
