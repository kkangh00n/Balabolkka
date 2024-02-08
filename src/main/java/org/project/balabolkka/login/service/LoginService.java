package org.project.balabolkka.login.service;

import lombok.RequiredArgsConstructor;
import org.project.balabolkka.exception.BadRequestException;
import org.project.balabolkka.jwt.provider.JwtTokenProvider;
import org.project.balabolkka.jwt.token.Token;
import org.project.balabolkka.login.dto.LoginRequest;
import org.project.balabolkka.login.exception.LoginExceptionMessage;
import org.project.balabolkka.member.entity.Member;
import org.project.balabolkka.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Token login(LoginRequest loginRequest) {

        //이메일을 통해 Member 가져옴
        Member loginMember = memberRepository.findMemberByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new BadRequestException(
                LoginExceptionMessage.WRONG_PASSWORD.getMessage()));

        //password 확인
        if (!encoder.matches(loginRequest.getPassword(), loginMember.getPassword())) {
            throw new BadRequestException(LoginExceptionMessage.WRONG_PASSWORD.getMessage());
        }

        //token 생성
        return createJwtToken(loginMember.getEmail());
    }

    @Transactional
    public void logout(Member member) {
        jwtTokenProvider.deleteRefreshToken(member.getEmail());
    }

    private Token createJwtToken(String email) {
        return jwtTokenProvider.createJwtToken(email);
    }
}
