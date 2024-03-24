package com.example.demo.src.user;

import com.example.demo.src.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserRes {

    private Long userId;
    private String userName;
    private String profileImageUrl;


    public static UserRes from(User user, String preSignedProfileImageUrl) {
        return UserRes.builder()
                .userId(user.getId())
                .userName(user.getName())
                .profileImageUrl(preSignedProfileImageUrl)
                .build();
    }
}
