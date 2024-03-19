package com.ssafy.fullerting.user.model.entity;

import com.ssafy.fullerting.user.model.dto.response.UserResponse;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user")
public class CustomUser implements UserDetails{

    // DB 필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_email", nullable = false, length = 50)
    private String email;

    @Column(name = "user_pwd", nullable = false, length = 255)
    private String password;

    @Column(name = "user_role", nullable = false, length = 20)
    private String role;

    @Column(name = "user_nick", nullable = false, length = 10)
    private String nickname;

    @Column(name = "user_thumb", length = 255)
    private String thumbnail;

    @Column(name = "user_rank", length = 20)
    private String rank;

    @Column(name = "user_location", length = 20)
    private String location;


    
    // 메서드 설정


    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    // DTO 생성자
    public UserResponse toResponse(){
        return UserResponse.builder()
                .id(this.id)
                .email(this.email)
                .role(this.role)
                .nickname(this.nickname)
                .thumbnail(this.thumbnail)
                .rank(this.rank)
                .location(this.location)
                .build();
    }

}
