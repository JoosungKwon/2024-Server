package com.example.demo.common.oauth.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface SocialOauth {
    /**
     * 각 소셜 로그인 페이지로 redirect할 URL build
     * 사용자로부터 로그인 요청을 받아 소셜 로그인 서버 인증용 코드 요청
     */
    String getOauthRedirectURL();

    /**
     * 소셜 로그인 후 OAuth 를 제공해주는 Provider 에게 AccessToken 요청
     */
    ResponseEntity<String> requestAccessToken(String code);

    OAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException;

    ResponseEntity<String> requestUserInfo(OAuthToken oAuthToken);
}
