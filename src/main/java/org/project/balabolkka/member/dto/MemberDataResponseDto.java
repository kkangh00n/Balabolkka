package org.project.balabolkka.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDataResponseDto {

    private final Long id;

    private final String name;

    private final String email;

    private final String address;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate birth;

    private final LocalDateTime createdAt;

    @Builder
    public MemberDataResponseDto(Long id, String name, String email, String address,
        LocalDate birth, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birth = birth;
        this.createdAt = createdAt;
    }
}
