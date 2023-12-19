package org.project.balabolkka.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.balabolkka.common.BaseEntity;
import org.project.balabolkka.member.dto.MemberUpdateRequestDto;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String address;

    private LocalDate birth;

    @Builder
    public Member(String name, String email, String password, String address, LocalDate birth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.birth = birth;
    }

    public void update(MemberUpdateRequestDto updateRequestDto){
        this.email = updateRequestDto.getEmail();
        this.address = updateRequestDto.getAddress();
    }

    public void setPassword(String password){
        this.password = password;
    }

}
