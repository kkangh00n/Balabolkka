package org.project.balabolkka.jwt.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtErrorMessage {

    JWT_TOKEN_EXPIRED("만료된 토큰입니다"),
    JWT_TOKEN_INVALID("잘못된 토큰입니다");

    private final String message;

}
