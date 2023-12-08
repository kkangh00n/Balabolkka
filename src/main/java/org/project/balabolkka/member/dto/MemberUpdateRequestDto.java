package org.project.balabolkka.member.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    @Email
    private String email;

    private String address;
}
