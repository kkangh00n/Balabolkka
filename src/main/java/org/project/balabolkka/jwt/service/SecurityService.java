package org.project.balabolkka.jwt.service;

import lombok.RequiredArgsConstructor;
import org.project.balabolkka.member.exception.MemberExceptionMessage;
import org.project.balabolkka.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findMemberByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(
                MemberExceptionMessage.NOT_FOUND_MEMBER.getMessage()));
    }
}
