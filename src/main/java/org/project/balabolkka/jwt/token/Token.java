package org.project.balabolkka.jwt.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {

    private String accessToken;
    private String refreshToken;
    private String email;

}
