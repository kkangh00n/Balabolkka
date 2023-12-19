package org.project.balabolkka.member.repository;

import java.util.Optional;
import org.project.balabolkka.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsMemberByEmail(String email);

    Optional<Member> findMemberByEmail(String email);
}
