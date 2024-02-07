package org.project.balabolkka.login.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginExceptionMessage {

    WRONG_PASSWORD("아이디나 비밀번호가 틀립니다");

    private final String message;
}
