package org.project.balabolkka.member.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.project.balabolkka.exception.exceptions.AlreadyExistsException;
import org.project.balabolkka.exception.exceptions.NotFoundException;
import org.project.balabolkka.member.dto.MemberDataResponseDto;
import org.project.balabolkka.member.entity.Member;
import org.project.balabolkka.member.dto.MemberResponseDto;
import org.project.balabolkka.member.dto.MemberSaveRequestDto;
import org.project.balabolkka.member.dto.MemberUpdateRequestDto;
import org.project.balabolkka.member.exception.MemberExceptionMessage;
import org.project.balabolkka.member.mapper.MemberMapper;
import org.project.balabolkka.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDataResponseDto join(MemberSaveRequestDto memberSaveRequestDto){

        if(memberRepository.existsMemberByEmail(memberSaveRequestDto.getEmail())){
            throw new AlreadyExistsException(MemberExceptionMessage.ALREADY_EXISTS_MEMBER.getMessage());
        }

        Member joinMember = MemberMapper.toEntity(memberSaveRequestDto);
        Member saveMember = memberRepository.save(joinMember);

        return MemberMapper.dataResponseDtoOfMember(saveMember);
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> getAll(){
        List<Member> allMember = memberRepository.findAll();
        return allMember.stream()
            .map(MemberMapper::responseDtoOfMember)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberDataResponseDto getOne(Long id){
        Member getMember = memberRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(MemberExceptionMessage.NOT_FOUND_MEMBER.getMessage()));
        return MemberMapper.dataResponseDtoOfMember(getMember);
    }

    public void update(Long id, MemberUpdateRequestDto memberUpdateRequestDto){

        Member getMember = memberRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(MemberExceptionMessage.NOT_FOUND_MEMBER.getMessage()));

        if(!getMember.getEmail().equals(memberUpdateRequestDto.getEmail()) && memberRepository.existsMemberByEmail(memberUpdateRequestDto.getEmail())){
            throw new AlreadyExistsException(MemberExceptionMessage.ALREADY_EXISTS_MEMBER.getMessage());
        }

        getMember.update(memberUpdateRequestDto);
    }

    public void delete(Long id){
        memberRepository.deleteById(id);
    }

}
