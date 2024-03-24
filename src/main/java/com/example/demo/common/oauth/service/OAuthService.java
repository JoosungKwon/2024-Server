package com.example.demo.common.oauth.service;

import com.example.demo.common.Constant;
import com.example.demo.common.oauth.model.OAuthToken;
import com.example.demo.common.oauth.model.Social;
import com.example.demo.common.oauth.model.SocialOauth;
import com.example.demo.common.oauth.model.SocialUser;
import com.example.demo.common.oauth.provider.OAuthStrategy;
import com.example.demo.src.user.model.GetSocialOAuthRes;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.src.user.repository.UserService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {
    private final OAuthStrategy oAuthStrategy;
    private final HttpServletResponse response;
    private final UserService userService;
    private final JwtService jwtService;
    private final String OAUTH_LOGIN = "OAuth Login email : {}, Name : {}, Provider : {}";
    private final String OAUTH_SIGN_UP = "OAuth Sign Up!! INSERT USER Table id : {},  Profile = {email : {}, Name : {}, Provide : {}}";

    public void accessRequest(Constant.SocialLoginType socialLoginType) throws IOException {
        // 소셜 로그인 타입에 맞는 OAuth 객체 획득
        SocialOauth socialOauth = oAuthStrategy.getOauthStrategy(socialLoginType);
        String oauthRedirectURL = socialOauth.getOauthRedirectURL();
        // 소셜 로그인에 맞는 redirect URL 로 redirect
        response.sendRedirect(oauthRedirectURL);
    }

    public GetSocialOAuthRes oAuthLoginOrJoin(Constant.SocialLoginType socialLoginType, String code) throws IOException {
        // 소셜 로그인 타입에 맞는 OAuth 객체 획득
        SocialOauth socialOauth = oAuthStrategy.getOauthStrategy(socialLoginType);
        ResponseEntity<String> accessTokenResponse = socialOauth.requestAccessToken(code);
        OAuthToken oAuthToken = socialOauth.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = socialOauth.requestUserInfo(oAuthToken);

        // 소셜 provider 에 맞는 객체 획득 후 provider 에 맞는 유저 정보를 객체화
        Social provider = Social.valueOf(socialLoginType.name());
        SocialUser userInfo = provider.toUserInfo(userInfoResponse);

        String jwtToken;
        Long userId;
        // 회원가입이 되어있다면 기존 db 에 저장되어 있는 id 를 jwt 생성 후 발급
        if (userService.checkUserByEmail(userInfo.getEmail())) {
            GetUserRes getUserRes = userService.getUserByEmail(userInfo.getEmail());
            jwtToken = jwtService.createJwt(getUserRes.getId());
            userId = getUserRes.getId();
            log.info(OAUTH_LOGIN, getUserRes.getEmail(), getUserRes.getName(), socialLoginType.name());
        } else {
            // 회원 가입이 되어 있지 않을경우 자동 회원가입 진행 후 생성된 id 로 jwt 생성 후 발급
            PostUserRes postUserRes = userService.createOAuthUser(userInfo.toEntity());
            jwtToken = postUserRes.getJwt();
            userId = postUserRes.getId();
            log.info(OAUTH_SIGN_UP, postUserRes.getId(), userInfo.getEmail(), userInfo.getName(), socialLoginType.name());
        }
        userService.oauthLogin(userInfo.getEmail());
        return new GetSocialOAuthRes(jwtToken, userId, oAuthToken.getAccessToken(), oAuthToken.getTokenType());
    }
}
