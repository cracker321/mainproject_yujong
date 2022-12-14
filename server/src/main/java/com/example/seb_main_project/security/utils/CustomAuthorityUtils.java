package com.example.seb_main_project.security.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:application-local.yml")
public class CustomAuthorityUtils {
    @Value("${mail.address.admin}")
    private String adminMailAddress;
    private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "USER");
    private final List<String> USER_ROLES_STRING = List.of("USER");


    /**
     * 사용자 이메일을 통해 데이터베이스에 저장된 유저 권한 리스트를 반환하는 메서드
     *
     * @param email 사용자 이메일
     * @return 유저 권한 String 리스트
     * @author dev32user
     */
    public List<String> createAuthorities(String email) {
        if (email.equals(adminMailAddress)) {
            return ADMIN_ROLES_STRING;
        }
        return USER_ROLES_STRING;
    }

    /**
     * 사용자 권한 리스트를 통해 GrantedAuthority 컬렉션을 리턴하는 메서드
     *
     * @param roles 사용자 권한 String 리스트
     * @return GrantedAuthority 리스트 컬렉션
     * @author dev32user
     */
    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
