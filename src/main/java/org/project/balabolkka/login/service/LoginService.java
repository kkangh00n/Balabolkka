package org.project.balabolkka.login.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.project.balabolkka.jwt.provider.JwtTokenProvider;
import org.project.balabolkka.login.dto.LoginRequest;
import org.project.balabolkka.login.exception.LoginExceptionMessage;
import org.project.balabolkka.member.entity.Member;
import org.project.balabolkka.member.repository.MemberRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(LoginRequest loginRequest, HttpServletResponse response){

        Member loginMember = memberRepository.findMemberByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new BadCredentialsException(LoginExceptionMessage.WRONG_PASSWORD.getMessage()));

        if(!encoder.matches(loginRequest.getPassword(), loginMember.getPassword())){
            throw new BadCredentialsException(LoginExceptionMessage.WRONG_PASSWORD.getMessage());
        }

        String token = jwtTokenProvider.createToken(loginMember.getEmail());

        response.setHeader("Authorization", token);

        return "로그인 완료";
    }

}
