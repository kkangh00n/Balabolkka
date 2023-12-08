package org.project.balabolkka.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private Long id;

    private String name;

    private String email;

    @Builder
    public MemberResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
