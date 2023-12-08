package org.project.balabolkka.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class MemberSaveRequestDto {

    private String name;

    @Email
    private String email;

    private String password;

    private String address;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;
}
