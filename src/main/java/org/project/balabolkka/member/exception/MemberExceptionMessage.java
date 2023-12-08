package org.project.balabolkka.member.exception;

import lombok.Getter;

@Getter
public enum MemberExceptionMessage {

    NOT_FOUND_MEMBER("회원을 찾을 수 없습니다."),
    ALREADY_EXISTS_MEMBER("해당 이메일로 이미 가입하였습니다");

    private final String message;

    MemberExceptionMessage(String message) {
        this.message = message;
    }
}
