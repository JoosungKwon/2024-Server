package com.example.demo.src.user.controller;


import com.example.demo.common.Constant.SocialLoginType;
import com.example.demo.common.Router.User.Args;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.model.response.BaseResponse;
import com.example.demo.common.oauth.service.OAuthService;
import com.example.demo.src.user.model.*;
import com.example.demo.src.user.repository.UserService;
import com.example.demo.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.example.demo.common.Router.Domain.USERS;
import static com.example.demo.common.Router.User.LOGIN;
import static com.example.demo.common.Router.V1;
import static com.example.demo.common.model.response.BaseResponseStatus.POST_USERS_INVALID_EMAIL;
import static com.example.demo.common.model.response.BaseResponseStatus.USERS_EMPTY_EMAIL;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;
import static org.springframework.http.HttpStatus.OK;

@Tag(
        name = "유저 API 목록",
        description = "회원가입 및 로그인, 등 유저 관련 API 목록"
)
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(V1 + USERS)
public class UserController {


    private final UserService userService;
    private final OAuthService oAuthService;
    private final JwtService jwtService;


    /**
     * 회원가입 API
     * [POST] /app/users
     *
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @Operation(summary = "회원 가입")
    @PostMapping
    @ResponseStatus(OK)
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        PostUserRes postUserRes = userService.createUser(postUserReq);
        return new BaseResponse<>(postUserRes);
    }

    /**
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /app/users? Email=
     *
     * @return BaseResponse<List < GetUserRes>>
     */
    //Query String
    @Operation(summary = "사용자 조회")
    @GetMapping // (GET) 127.0.0.1:9000/app/users
    @ResponseStatus(OK)
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
        if (Email == null) {
            List<GetUserRes> getUsersRes = userService.getUsers();
            return new BaseResponse<>(getUsersRes);
        }
        // Get Users
        List<GetUserRes> getUsersRes = userService.getUsersByEmail(Email);
        return new BaseResponse<>(getUsersRes);
    }

    /**
     * 회원 1명 조회 API
     * [GET] /app/users/:userId
     *
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @GetMapping(Args.USER_ID) // (GET) 127.0.0.1:9000/app/users/:userId
    @ResponseStatus(OK)
    public BaseResponse<GetUserRes> getUser(@PathVariable("userId") Long userId) {
        GetUserRes getUserRes = userService.getUser(userId);
        return new BaseResponse<>(getUserRes);
    }


    /**
     * 유저정보변경 API
     * [PATCH] /app/users/:userId
     *
     * @return BaseResponse<String>
     */
    @Operation(summary = "사용자 이름 변경")
    @PatchMapping(Args.USER_ID)
    @ResponseStatus(OK)
    public BaseResponse<String> modifyUserName(@PathVariable("userId") Long userId, @RequestBody PatchUserReq patchUserReq) {

        Long jwtUserId = jwtService.getUserId();

        userService.modifyUserName(userId, patchUserReq);

        String result = "수정 완료!!";
        return new BaseResponse<>(result);

    }

    /**
     * 유저정보삭제 API
     * [DELETE] /app/users/:userId
     *
     * @return BaseResponse<String>
     */
    @Operation(summary = "사용자 삭제")
    @DeleteMapping(Args.USER_ID)
    @ResponseStatus(OK)
    public BaseResponse<String> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        String result = "삭제 완료!!";
        return new BaseResponse<>(result);
    }

    /**
     * 로그인 API
     * [POST] /app/users/logIn
     *
     * @return BaseResponse<PostLoginRes>
     */
    @Operation(summary = "로그인")
    @PostMapping(LOGIN)
    @ResponseStatus(OK)
    public BaseResponse<PostLoginRes> logIn(@RequestBody @Valid PostLoginReq postLoginReq) {
        PostLoginRes postLoginRes = userService.logIn(postLoginReq);
        return new BaseResponse<>(postLoginRes);
    }


    /**
     * 유저 소셜 가입, 로그인 인증으로 리다이렉트 해주는 url
     * [GET] /app/users/auth/:socialLoginType/login
     *
     * @return void
     */
    @Operation(summary = "소셜 로그인 리다이렉트")
    @GetMapping("/auth/{socialLoginType}/login")
    @ResponseStatus(OK)
    public void socialLoginRedirect(@PathVariable(name = "socialLoginType") String SocialLoginPath) throws IOException {
        SocialLoginType socialLoginType = SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        oAuthService.accessRequest(socialLoginType);
    }


    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     *
     * @param socialLoginPath (GOOGLE, FACEBOOK, NAVER, KAKAO)
     * @param code            API Server 로부터 넘어오는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 java 객체 (access_token, jwt_token, user_num 등)
     */
    @Operation(summary = "소셜 로그인 콜백")
    @GetMapping(value = "/auth/{socialLoginType}/login/callback")
    @ResponseStatus(OK)
    public BaseResponse<GetSocialOAuthRes> socialLoginCallback(
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code) throws IOException, BaseException {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code : {}", code);
        SocialLoginType socialLoginType = SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        GetSocialOAuthRes getSocialOAuthRes = oAuthService.oAuthLoginOrJoin(socialLoginType, code);
        return new BaseResponse<>(getSocialOAuthRes);
    }


}
