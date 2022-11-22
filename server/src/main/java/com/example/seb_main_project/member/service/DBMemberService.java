package com.example.seb_main_project.member.service;

import com.example.seb_main_project.exception.BusinessLogicException;
import com.example.seb_main_project.exception.ExceptionCode;
import com.example.seb_main_project.member.entity.Member;
import com.example.seb_main_project.security.auth.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DBMemberService implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils customAuthorityUtils;


    /**
     * Member 회원가입 DB 저장 메서드
     */
    @Override
    public Member createMember(Member member) {
        verifyExistEmail(member.getEmail());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        member.setRoles(customAuthorityUtils.createRoles(member.getEmail()));
        log.info("# Create Member in DB");

        return memberRepository.save(member);
    }

    /**
     * 이메일 정보를 인수로 받아서 리포지토리에 존재하는지 확인하는 메서드
     *
     * @param email String 타입, 이메일 정보
     * @author dev32user
     */
    private void verifyExistEmail(String email) {
        this.memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
                });
    }
}
