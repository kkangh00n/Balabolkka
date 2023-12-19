package org.project.balabolkka.login.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Email
    String email;

    String password;
}
