package com.example.demo.common.oauth.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Getter
public enum Social {
    GOOGLE("google") {
        @Override
        public SocialUser toUserInfo(ResponseEntity<String> responseUserInfo) throws JsonProcessingException {
            Map<String, Object> response = objectMapper.readValue(responseUserInfo.getBody(), new TypeReference<Map<String, Object>>() {
            });
            String email = (String) response.get("email");
            String name = (String) response.get("name");

            return SocialUser.builder()
                    .email(email)
                    .name(name)
                    .build();
        }
    }, KAKAO("kakao") {
        @Override
        public SocialUser toUserInfo(ResponseEntity<String> responseUserInfo) throws JsonProcessingException {
            Map<String, Object> response = objectMapper.readValue(responseUserInfo.getBody(), new TypeReference<Map<String, Object>>() {
            });

            // kakao_account 정보를 가져옵니다.
            Map<String, Object> kakaoAccount = (Map<String, Object>) response.get("kakao_account");

            // profile 정보를 가져옵니다.
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            String email = (String) kakaoAccount.get("email");
            String name = (String) profile.get("nickname");
            return SocialUser.builder()
                    .email(email)
                    .name(name)
                    .build();
        }
    }, NAVER("naver") {
        @Override
        public SocialUser toUserInfo(ResponseEntity<String> responseUserInfo) throws JsonProcessingException {
            Map<String, Object> response = objectMapper.readValue(responseUserInfo.getBody(), new TypeReference<Map<String, Object>>() {
            });

            String email = (String) response.get("email");
            String name = (String) response.get("name");
            return SocialUser.builder()
                    .email(email)
                    .name(name)
                    .build();
        }
    };

    private final String provider;
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    Social(String provider) {
        this.provider = provider;
    }

    public abstract SocialUser toUserInfo(ResponseEntity<String> responseUserInfo) throws JsonProcessingException;
}
