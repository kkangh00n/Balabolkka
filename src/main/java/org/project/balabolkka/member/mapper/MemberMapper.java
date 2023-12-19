package org.project.balabolkka.member.mapper;

import org.project.balabolkka.member.dto.MemberDataResponseDto;
import org.project.balabolkka.member.entity.Member;
import org.project.balabolkka.member.dto.MemberResponseDto;
import org.project.balabolkka.member.dto.MemberSaveRequestDto;

public class MemberMapper {

    public static Member toEntity(MemberSaveRequestDto memberSaveRequestDto){
        return Member.builder()
            .name(memberSaveRequestDto.getName())
            .email(memberSaveRequestDto.getEmail())
            .address(memberSaveRequestDto.getAddress())
            .birth(memberSaveRequestDto.getBirth())
            .build();
    }

    public static MemberResponseDto responseDtoOfMember(Member member){
        return MemberResponseDto.builder()
            .id(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .build();
    }

    public static MemberDataResponseDto dataResponseDtoOfMember(Member member){
        return MemberDataResponseDto.builder()
            .id(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .address((member.getAddress()))
            .birth(member.getBirth())
            .createdAt(member.getCreateDate())
            .build();
    }

}
