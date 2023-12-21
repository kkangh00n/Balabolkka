package org.project.balabolkka.login.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.project.balabolkka.jwt.provider.JwtTokenProvider;
import org.project.balabolkka.jwt.service.RefreshTokenService;
import org.project.balabolkka.jwt.token.Token;
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
public class LoginService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public String login(LoginRequest loginRequest, HttpServletResponse response){

        //이메일을 통해 Member 가져옴
        Member loginMember = memberRepository.findMemberByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new BadCredentialsException(LoginExceptionMessage.WRONG_PASSWORD.getMessage()));

        //password 확인
        if(!encoder.matches(loginRequest.getPassword(), loginMember.getPassword())){
            throw new BadCredentialsException(LoginExceptionMessage.WRONG_PASSWORD.getMessage());
        }

        //token 생성
        Token token = createJwtToken(loginMember.getEmail());

        //accesstoken 헤더 세팅
        response.setHeader("AccessToken", token.getAccessToken());
        response.setHeader("RefreshToken", token.getRefreshToken());

        return "로그인 완료";
    }

    public Token createJwtToken(String email){
        Token token = jwtTokenProvider.createJwtToken(email);
        //refreshtoken DB 저장
        refreshTokenService.createRefreshToken(token);
        return token;
    }
}
