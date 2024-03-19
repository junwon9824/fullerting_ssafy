package com.ssafy.fullerting.security.service;

import com.ssafy.fullerting.user.exception.UserErrorCode;
import com.ssafy.fullerting.user.exception.UserException;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import com.ssafy.fullerting.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;


@Service
@AllArgsConstructor
public class DataBaseUserDetailsService implements UserDetailsService {
    // DB에서 유저 정보 조회

    private final UserRepository userRepository;

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = userRepository.findByEmail(username).orElseThrow(() -> new UserException(UserErrorCode.NOT_EXISTS_USER));
        return customUser;
//        return new User(customUser.getEmail(), customUser.getPassword(), getAuthorities(customUser));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(CustomUser customUser) {
        // 권한 설정.
        return Collections.singletonList(new SimpleGrantedAuthority(customUser.getRole()));
    }
}


