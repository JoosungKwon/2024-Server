package com.example.demo.common.oauth.model;

import com.example.demo.src.user.entity.LoginType;
import com.example.demo.src.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialUser {
    private String email;
    private String name;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password("NONE")
                .name(this.name)
                .isOAuth(true)
                .loginType(LoginType.SOCIAL)
                .build();
    }
}
