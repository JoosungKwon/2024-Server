package com.example.demo.common.oauth.provider;

import com.example.demo.common.oauth.model.OAuthToken;
import com.example.demo.common.oauth.model.SocialOauth;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component("KAKAO")
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth {

    @Value("${spring.OAuth2.kakao.url}")
    private String KAKAO_SNS_URL;

    @Value("${spring.OAuth2.kakao.client-id}")
    private String KAKAO_SNS_CLIENT_ID;

    @Value("${spring.OAuth2.kakao.callback-login-url}")
    private String KAKAO_SNS_CALLBACK_LOGIN_URL;

    @Value("${spring.OAuth2.kakao.client-secret}")
    private String KAKAO_SNS_CLIENT_SECRET;

    @Value("${spring.OAuth2.kakao.scope}")
    private String KAKAO_DATA_ACCESS_SCOPE;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", KAKAO_DATA_ACCESS_SCOPE);
        params.put("response_type", "code");
        params.put("client_id", KAKAO_SNS_CLIENT_ID);
        params.put("redirect_uri", KAKAO_SNS_CALLBACK_LOGIN_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));
        String redirectURL = KAKAO_SNS_URL + "?" + parameterString;
        log.info("redirectURL = ", redirectURL);
        return redirectURL;
    }

    public ResponseEntity<String> requestAccessToken(String code) {
        String KAKAO_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";

        // 카카오 로그인 시 헤더에 x-www-form-urlencoded 를 설정해야함.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", KAKAO_SNS_CLIENT_ID);
        params.add("client_secret", KAKAO_SNS_CLIENT_SECRET);
        params.add("redirect_uri", KAKAO_SNS_CALLBACK_LOGIN_URL);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(KAKAO_TOKEN_REQUEST_URL, request, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }
        return null;
    }

    public OAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        log.info("response.getBody() = {}", response.getBody());
        return objectMapper.readValue(response.getBody(), OAuthToken.class);
    }

    public ResponseEntity<String> requestUserInfo(OAuthToken oAuthToken) {
        String KAKAO_USERINFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";

        //header에 accessToken을 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccessToken());

        //HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(KAKAO_USERINFO_REQUEST_URL, HttpMethod.GET, request, String.class);
        log.info("response.getBody() = {}", response.getBody());
        return response;
    }
}
