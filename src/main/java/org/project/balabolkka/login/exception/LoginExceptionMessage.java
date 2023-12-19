package org.project.balabolkka.login.exception;

import lombok.Getter;

@Getter
public enum LoginExceptionMessage {

    WRONG_PASSWORD("아이디나 비밀번호가 틀립니다");

    private final String message;

    LoginExceptionMessage(String message) {
        this.message = message;
    }
}
